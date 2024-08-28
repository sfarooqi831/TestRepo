package com.alivia.bussiness.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.alivia.bussiness.entity.Customer;
import com.alivia.bussiness.process.CustomerProcessor;
import com.alivia.bussiness.repo.CustomerRepositary;
import com.alivia.bussiness.skip.listener.CustomerSkipListener;

@Configuration
@EnableBatchProcessing // To Enable Batch Processing
public class AppBatchConfig {
	
	
	
	
	@Autowired
	private CustomerRepositary custRepo;
		
	@Autowired
	private  JobRepository jobRepository;
	

	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	
//	@Bean
//	@StepScope // As default scope is singleton
//	public ItemReader<Customer> itemReader(@Value("#{jobParameters[fileFullPath]}") String fileName){
//		FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
////		reader.setResource(new ClassPathResource("customers.csv"));
//		reader.setResource(new FileSystemResource(new File(fileName)));
//		reader.setLinesToSkip(1);
//		reader.setLineMapper(new DefaultLineMapper<>() {{
//			setLineTokenizer(new DelimitedLineTokenizer()
//					{{
//						setDelimiter(DELIMITER_COMMA);
//						setNames(new String[]{"id", "firstName", "lastName", "email", "gender", "country", "dob", "contact","age"});
//					}}
//					
//					);
//			setFieldSetMapper(new  BeanWrapperFieldSetMapper<Customer>() {{
//			setTargetType(Customer.class);
//			}}
//		);
//		}}
//		
//	);
//		return reader;
//	}
//	
//	
	@Bean
	@StepScope
	public FlatFileItemReader<Customer> itemReader(@Value("#{jobParameters[fileFullPath]}") String fileName) {
	    FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
	    reader.setResource(new FileSystemResource(fileName));
	    reader.setLinesToSkip(1);
	    reader.setLineMapper(lineMapper());
	    return reader;
	}

	private DefaultLineMapper<Customer> lineMapper() {
	    DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
	    lineMapper.setLineTokenizer(delimitedLineTokenizer());
	    lineMapper.setFieldSetMapper(fieldSetMapper());
	    return lineMapper;
	}

	private DelimitedLineTokenizer delimitedLineTokenizer() {
	    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
	    tokenizer.setDelimiter(",");
	    tokenizer.setNames("id", "firstName", "lastName", "email", "gender", "country", "dob", "contact", "age");
	    return tokenizer;
	}

	private BeanWrapperFieldSetMapper<Customer> fieldSetMapper() {
	    BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
	    fieldSetMapper.setTargetType(Customer.class);
	    return fieldSetMapper;
	}

	
	@Bean
	public ItemProcessor<Customer, Customer> itemProcessor(){
		return new CustomerProcessor();
	}
	
	@Bean
	public RepositoryItemWriter<Customer> itemWriter() {
		
		RepositoryItemWriter<Customer> itemWriter = new RepositoryItemWriter<>();
		itemWriter.setMethodName("save");
		itemWriter.setRepository(custRepo);
//	Similarly --	custRepo.save(null);
		return itemWriter;
	}
	
	@Bean
	public JobExecutionListener jobListener() {
		
		return new JobExecutionListener() {
			
			@Override
			public void afterJob(JobExecution jobExecution) {
				System.err.println("JOb Ended At " + jobExecution.getEndTime());
				System.err.println("JOB Status " + jobExecution.getStatus());
			}

			@Override
			public void beforeJob(JobExecution jobExecution) {
				System.err.println("JOb Started At " + jobExecution.getStartTime());
				System.err.println("JOB Status " + jobExecution.getStatus());
			}
		};
	}
	

	
	@Bean
	public Step importCustomerToDbStep(ItemReader<Customer> itemReader) {
		return new StepBuilder("importCustomerToDbStep",jobRepository)
				.<Customer , Customer>chunk(10,platformTransactionManager)
				.reader(itemReader)
				.writer(itemWriter())
				.processor(itemProcessor())
				.taskExecutor(new SimpleAsyncTaskExecutor() {/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
				{
				setConcurrencyLimit(10);
				}
				})
				.faultTolerant()
//				.skipLimit(100)
//				.skip(Exception.class)
				.listener(customerSkipListener())
				.skipPolicy(skipPolicy())
				/**
				 * Spring boot is smart enough that he will insert the only rows which have the issues
				 */
				.build();
	}
	
	@Bean
	public  SkipPolicy skipPolicy() {
		return new CustomSkipPolicy();
	}
	
	
	@Bean
	public SkipListener<Customer, Number> customerSkipListener() {
		return new CustomerSkipListener();
	}
	
	


	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
		asyncTaskExecutor.setConcurrencyLimit(10);
		return asyncTaskExecutor;
	}


	@Bean
	public Job importCustomerToDbJob(ItemReader<Customer> itemReader) {
		return new JobBuilder("importCustomerJob",jobRepository)
				.listener(jobListener())
//				.incrementer(new RunIdIncrementer())
				.start(importCustomerToDbStep(itemReader))
				.build();
	}
	
}
