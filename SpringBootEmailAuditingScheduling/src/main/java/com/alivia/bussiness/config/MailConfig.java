package com.alivia.bussiness.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class MailConfig {

//	private final JavaMailSender sender;
	
	@Bean(name = "simpleMailMessage")
	public SimpleMailMessage  simpleMailMessage() {
		return new SimpleMailMessage();
	}
}
