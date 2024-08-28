package com.alivia.bussiness.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alivia.bussiness.dto.EmailRequest;
import com.alivia.bussiness.dto.Order;
import com.alivia.bussiness.service.EmailService;
import com.alivia.bussiness.service.OrderService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	EmailService service;

	@Autowired
	OrderService orderService;

	@PostMapping("/sendEmail")
	public String sendEmail(@RequestBody EmailRequest emailRequest) {
		return service.sendSimpleEmail(emailRequest);
	}

	@GetMapping("/sendOrderReport")
	public String sendEmailAndAttachment()
			throws MessagingException, IOException {
		return service.sendOrderReport();
	}

	@PostMapping("/email")
	public String triggerEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
		return service.sendEmail(emailRequest);
	}

	@PostMapping("/saveOrder")
	public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
		return ResponseEntity.ok(orderService.saveOrder(order));
	}
	
	@PutMapping("/updateOrder")
	public ResponseEntity<Order> updateOrder(@RequestBody Order order){
		return ResponseEntity.ok(orderService.updateOrder(order));
	}

}
