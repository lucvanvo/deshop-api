package com.example.demo.controller.products;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;

import lombok.Builder;

@Builder
public record ProductRequest(Long id, Long categoryId, String name, String description, Long price,
        MultipartFile image) {
    public ProductRequest {
        valdateFields(name, description, price, image);
    }

    private static void valdateFields(String name, String description, Long price, MultipartFile image) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Image cannot be null or empty");
        }
    }

    public Product toProduct() {
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .imageUrl(image.getOriginalFilename())
                .build();
    }

}
