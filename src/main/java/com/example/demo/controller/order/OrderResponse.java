package com.example.demo.controller.order;

import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import lombok.Builder;

@Builder
public record OrderResponse(Long id, String address, String orderPersonName,
        String phoneNumber, String email, OrderStatus status,
        Iterable<OrderDetailsResponse> orderDetails) {
    public static OrderResponse from(Order order, Iterable<OrderDetailsResponse> orderDetails) {
        return OrderResponse.builder()
                .id(order.getId())
                .address(order.getAddress())
                .orderPersonName(order.getOrderPersonName())
                .phoneNumber(order.getPhoneNumber())
                .email(order.getEmail())
                .status(order.getStatus())
                .orderDetails(orderDetails)
                .build();
    }

}
