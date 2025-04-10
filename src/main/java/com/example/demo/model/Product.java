package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false, unique = true)
	private String name;

	@Column(length = 250)
	private String description;

	@Column(nullable = false)
	private Long price;

	@Column(nullable = false, unique = true)
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "CATEGORY_ID_FK"))
	private Category category;

}
