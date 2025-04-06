package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "`order`")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column (length = 100, nullable = false)
	private Long user_id;
	@Column (nullable = false)
	private Double total_price;
	@Column(name = "order_date")
	private LocalDate orderDate;
	
	public Order(Long id, Long user_id, Double total_price, LocalDate orderDate, OrderDetail details) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.total_price = total_price;
		this.orderDate = orderDate;
		this.orderDetail = details;
	}
	public OrderDetail getDetails() {
		return orderDetail;
	}
	public void setDetails(OrderDetail details) {
		this.orderDetail = details;
	}
	@OneToOne
	@JoinColumn(name = "order_detail_id")
	private OrderDetail orderDetail;
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(Long id, Long user_id, Double total_price, LocalDate orderDate) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.total_price = total_price;
		this.orderDate = orderDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Double getTotal_price() {
		return total_price;
	}
	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	
	
}
