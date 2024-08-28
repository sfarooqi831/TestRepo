package com.alivia.bussiness;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = "com.alivia.bussiness.config")
//@EnableBatchProcessing
public class SpringBootBatchProcessingUsingJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBatchProcessingUsingJpaApplication.class, args);
	}

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	private Job job;
	
	private final String FILE_PATH ="C:\\Users\\Sajid Farooqi\\Desktop\\Batch-file\\";

	@GetMapping("/import")
	public String importFile(@RequestParam("file") MultipartFile multipartFile)
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException, IllegalStateException, IOException {

		/**
		 * It tries to look into the project directory as it doesn't exist 
		 * so we need to copy the file path to Some location
		 */
		
		
		
		String originalFilename = multipartFile.getOriginalFilename();
		File file = new File(FILE_PATH+originalFilename); // to get the file form this location
		multipartFile.transferTo(file);
		
		System.err.println("FILE PATH " + file.getAbsolutePath());
		System.err.println("FILE " +  (FILE_PATH+originalFilename));
		
		System.err.println("JOB Implementation: " + job.getClass().getCanonicalName());
		JobParameters jobParameters = new JobParametersBuilder()
		.addLong("startAt", System.currentTimeMillis())
//		.addString("fileFullPath", file.getAbsolutePath())
		.addString("fileFullPath", (FILE_PATH+originalFilename))		
		.toJobParameters();
		JobExecution jobExecution = jobLauncher.run(job,
				jobParameters);
		if(jobExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
			Files.deleteIfExists(Paths.get(FILE_PATH+originalFilename));
		}
		
		
		return "done";
	}
}
