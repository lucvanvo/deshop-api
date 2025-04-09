package com.example.demo.controller.products;

import com.example.demo.model.Product;

import lombok.Builder;

@Builder
public record ProductResponse(
        Long id,
        String name,
        Long categoryId,
        String description,
        String imageUrl,
        Long price) {

    public static ProductResponse fromProduct(Product savedProduct) {
        return fromProduct(savedProduct, null);
    }

    public static ProductResponse fromProduct(Product savedProduct, Long categoryId) {
        return ProductResponse.builder()
                .id(savedProduct.getId())
                .categoryId(categoryId)
                .name(savedProduct.getName())
                .description(savedProduct.getDiscription())
                .price(savedProduct.getPrice())
                .imageUrl(savedProduct.getImageUrl())
                .build();
    }

}
