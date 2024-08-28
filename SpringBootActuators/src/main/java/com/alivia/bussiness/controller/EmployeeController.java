package com.alivia.bussiness.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alivia.bussiness.service.EmployeeService;
import com.alivia.bussiness.service.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService service;

	@GetMapping("/health")
	public HttpStatus getMethodName() {
		return HttpStatus.OK;
	}

	@GetMapping("/all")
	public List<Employee> findAll() throws JsonProcessingException {
		List<Employee> all = service.findAll();
		logger.info("fetch all employees {}", new ObjectMapper().writeValueAsString(all));
		return all;
	}

}
