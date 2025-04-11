package com.example.demo.controller.order;

public record OrderDetailsRequest(
        Long orderId,
        Long productId,
        Integer quantity) {
}