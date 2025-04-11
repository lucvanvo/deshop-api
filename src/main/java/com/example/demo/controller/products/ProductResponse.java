package com.example.demo.controller.products;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductDetails;

import lombok.Builder;

@Builder
public record ProductResponse(
        Long id,
        String name,
        Long categoryId,
        String description,
        String imageUrl,
        Long price,
        String categoryName) {

    public static ProductResponse fromProduct(Product savedProduct) {
        return ProductResponse.builder()
                .id(savedProduct.getId())
                .categoryId(savedProduct.getCategory().getId())
                .categoryName(savedProduct.getCategory().getName())
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .price(savedProduct.getPrice())
                .imageUrl(savedProduct.getImageUrl())
                .build();
    }

    public static ProductResponse fromProductDetails(ProductDetails productDetails) {
        return ProductResponse.builder()
                .id(productDetails.getId())
                .categoryId(productDetails.getCategoryId())
                .categoryName(productDetails.getCategoryName())
                .name(productDetails.getName())
                .description(productDetails.getDescription())
                .price(productDetails.getPrice())
                .imageUrl(productDetails.getImageUrl())
                .build();
    }

}
