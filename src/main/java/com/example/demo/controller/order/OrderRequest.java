package com.example.demo.controller.order;

import java.util.List;

import com.example.demo.model.Order;

public record OrderRequest(String address, String phoneNumber, String orderPersonName, String email,
        List<OrderDetailsRequest> orderDetails) {

    public Order toOrder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'extractOrder'");
    }

}
