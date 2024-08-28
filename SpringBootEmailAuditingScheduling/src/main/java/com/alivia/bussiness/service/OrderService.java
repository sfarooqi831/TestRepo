package com.alivia.bussiness.service;

import com.alivia.bussiness.dto.Order;
import com.alivia.bussiness.repositary.OrderRepositary;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	@Autowired
	OrderRepositary repo;

//	@PostConstruct
	public void initData() {
		List<Order> orderList = Arrays.asList(new Order[] { new Order("Laptop", 2, 1500.0D),
				new Order("Phone", 5, 800.0D), new Order("Headphones", 10, 150.0D), new Order("Monitor", 4, 300.0D),
				new Order("Keyboard", 8, 70.0D), new Order("Mouse", 15, 50.0D), new Order("Printer", 1, 200.0D),
				new Order("Desk", 3, 250.0D), new Order("Chair", 7, 120.0D),
				new Order("External Hard Drive", 6, 100.0D), new Order("USB Cable", 20, 10.0D),
				new Order("Webcam", 4, 85.0D), new Order("Speaker", 9, 60.0D), new Order("Router", 2, 130.0D),
				new Order("SSD", 8, 110.0D), new Order("RAM", 10, 75.0D), new Order("Graphics Card", 3, 500.0D),
				new Order("Motherboard", 5, 220.0D), new Order("Processor", 2, 350.0D),
				new Order("Power Supply", 6, 90.0D), new Order("Cooling Fan", 12, 25.0D),
				new Order("HDMI Cable", 14, 15.0D), new Order("Smart Watch", 7, 200.0D), new Order("Tablet", 3, 300.0D),
				new Order("Camera", 2, 450.0D), new Order("Tripod", 5, 40.0D), new Order("Lens", 4, 500.0D),
				new Order("Memory Card", 20, 25.0D), new Order("Flash Drive", 30, 15.0D),
				new Order("Projector", 2, 600.0D) });
		this.repo.saveAll(orderList);
	}

	public Order saveOrder(Order order) {
		return (Order) this.repo.save(order);
	}

	public Order updateOrder(Order order) {
		Order original = this.repo.findById(order.getId()).get();
		original.setName(order.getName());
		original.setPrice(order.getPrice());
		original.setQuantity(order.getQuantity());
		return (Order) this.repo.save(order);
	}
}