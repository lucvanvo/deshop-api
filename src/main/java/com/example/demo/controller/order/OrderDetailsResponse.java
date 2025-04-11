package com.example.demo.controller.order;

import com.example.demo.model.OrderDetails;

public record OrderDetailsResponse(Long id,
        Long orderId,
        Long productId,
        Integer quantity) {

    public static OrderDetailsResponse formOrderDetails(OrderDetails orderDetails) {
        return new OrderDetailsResponse(orderDetails.getId(),
                orderDetails.getOrder().getId(), orderDetails.getProduct().getId(), orderDetails.getQuantity());
    }
}
