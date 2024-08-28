package com.alivia.bussiness.dto;

import java.util.Date;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "orders_tab")
@EntityListeners(AuditingEntityListener.class)
@Audited // for which i need to maintain the Enverse process
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int quantity;
	private String name;
	private double price;
	
	@CreatedBy
	private String createdBy;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@LastModifiedBy
	private String lastModifiedBy;
	
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=");
		builder.append(id);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", name=");
		builder.append(name);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}

	public Order() {
		
	}
	
	public Order(String name, int quantity, double price) {
		super();
		this.quantity = quantity;
		this.name = name;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
