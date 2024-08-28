package com.alivia.bussiness.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alivia.bussiness.service.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	public List<Employee> findAll() throws JsonProcessingException {
		 List<Employee> empList = IntStream.rangeClosed(1, 10).mapToObj(i -> new Employee(i, "Employee " + i))
				.collect(Collectors.toList());
		 logger.info("Returning Employees from DB {}", new ObjectMapper().writeValueAsString(empList));
		 return empList;
	}
}
