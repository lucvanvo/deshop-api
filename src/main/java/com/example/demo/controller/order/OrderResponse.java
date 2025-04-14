package com.example.demo.controller.order;

import java.time.LocalDateTime;

import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import lombok.Builder;

@Builder
public record OrderResponse(Long id, String address, String orderPersonName,
        String phoneNumber, String email, OrderStatus status,
        LocalDateTime orderDate,
        String notes,
        Iterable<OrderDetailsResponse> orderDetails, Long totalPrice) {
    public static OrderResponse from(Order order, Iterable<OrderDetailsResponse> orderDetails, Long totalPrice) {
        return OrderResponse.builder()
                .id(order.getId())
                .address(order.getAddress())
                .orderPersonName(order.getOrderPersonName())
                .phoneNumber(order.getPhoneNumber())
                .email(order.getEmail())
                .status(order.getStatus())
                .orderDetails(orderDetails)
                .notes(order.getNotes())
                .orderDate(order.getOrderDate())
                .totalPrice(totalPrice).build();
    }

}
