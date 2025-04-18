package com.example.demo.controller.order;

import com.example.demo.model.OrderDetails;

public record OrderDetailsResponse(Long id,
        Long orderId,
        Long productId,
        String productName,
        Integer quantity,
        Long priceUnit,
        Long price) {

    public static OrderDetailsResponse from(OrderDetails orderDetails) {

        return new OrderDetailsResponse(
                orderDetails.getId(),
                orderDetails.getOrder().getId(),
                orderDetails.getProduct().getId(),
                orderDetails.getProduct().getName(),
                orderDetails.getQuantity(),
                orderDetails.getProduct().getPrice(),
                orderDetails.getProduct().getPrice() * orderDetails.getQuantity());
    }
}
