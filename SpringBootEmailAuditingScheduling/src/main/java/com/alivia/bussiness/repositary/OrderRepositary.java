package com.alivia.bussiness.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import com.alivia.bussiness.dto.Order;

public interface OrderRepositary extends RevisionRepository<Order, Integer, Integer>, JpaRepository<Order, Integer> {

}
