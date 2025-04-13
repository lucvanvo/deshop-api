package com.example.demo.controller.order;

import java.util.List;

import com.example.demo.model.OrderStatus;

public record OrderRequest(String address, String phoneNumber, String orderPersonName, String email, String notes,
                OrderStatus status,
                List<OrderDetailsRequest> orderDetails) {

}
