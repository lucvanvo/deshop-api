package com.example.demo.controller.order;

import com.example.demo.model.Order;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Product;

import lombok.Builder;

@Builder
public record OrderDetailsRequest(
        Long orderId,
        Long productId,
        Integer quantity) {

    public OrderDetails toOrderDetails(Order order, Product product) {
        return new OrderDetails(
                null,
                order,
                product,
                quantity);
    }
}