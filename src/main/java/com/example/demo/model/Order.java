package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime orderDate;

	@Column(nullable = false)
	private String orderPersonName;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String phoneNumber;

	private String email;

	private String notes;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private OrderStatus status = OrderStatus.NEW; // Default status is NEW
}
