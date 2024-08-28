package com.alivia.bussiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.alivia.bussiness.service.AuditAwareImpl;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)  // This will maintain the history
public class SpringBootEmailAuditingSchedulingApplication {

	 @Bean(name = "auditAwareImpl")
	  public AuditorAware<String> auditAwareImpl() {
	    return new AuditAwareImpl();
	  }
	  
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmailAuditingSchedulingApplication.class, args);
	}

}
