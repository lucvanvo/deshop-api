package com.example.demo.controller.order;

import java.util.List;

public record OrderRequest(String address, String phoneNumber, String orderPersonName, String email, String notes,
        List<OrderDetailsRequest> orderDetails) {

}
