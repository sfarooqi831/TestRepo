package com.alivia.bussiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringJmsProducerExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJmsProducerExampleApplication.class, args);
	}

}
