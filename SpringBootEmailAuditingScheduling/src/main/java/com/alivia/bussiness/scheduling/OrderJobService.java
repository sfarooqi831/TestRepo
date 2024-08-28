package com.alivia.bussiness.scheduling;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alivia.bussiness.service.EmailService;

import jakarta.mail.MessagingException;

@Service
@EnableScheduling
public class OrderJobService {

	@Autowired
	EmailService emailService;

	@Scheduled(cron = "${cron_interval}", zone = "PST")
	public void process() {
		System.out.println(" Report Sent At  " + new Date());

		try {
			emailService.sendOrderReport();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
