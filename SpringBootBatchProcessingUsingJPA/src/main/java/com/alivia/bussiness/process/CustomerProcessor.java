package com.alivia.bussiness.process;

import org.springframework.batch.item.ItemProcessor;

import com.alivia.bussiness.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer cust) throws Exception {
//		cust.setId(null);

		int age = Integer.parseInt(cust.getAge());
		if (age > 18) {
			cust.setFullName(cust.getFirstName() + cust.getLastName());
			return cust;
		}
		return null;
	}

}
