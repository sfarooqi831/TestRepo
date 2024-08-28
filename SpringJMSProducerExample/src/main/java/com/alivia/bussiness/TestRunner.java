package com.alivia.bussiness;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

public class TestRunner {

	@Autowired
	MessageProducer msg;
	int number =10;

	// public void run(String... args) throws Exception {
	@Scheduled(cron = "*/10 * * * * *")
	public void testRun() throws Exception {
		Student student = new Student();
		student.setId(UUID.randomUUID().toString().substring(0,5));
		student.setName("sajid");
		msg.sendMessage(student);
		System.out.println("MSG Sent");
	}
	

}
