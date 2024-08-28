package com.alivia.bussiness.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alivia.bussiness.entity.Customer;


@Repository
public interface CustomerRepositary extends JpaRepository<Customer, Integer> {

}
