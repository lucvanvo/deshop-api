package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "`order`")
@NoArgsConstructor
@Getter
@Setter
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDate orderDate;

	@Column(nullable = false)
	private String orderPersonName;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String phoneNumber;

	private String email;

	private String notes;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;
}
