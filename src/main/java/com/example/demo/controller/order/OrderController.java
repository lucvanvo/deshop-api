package com.example.demo.controller.order;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OrderService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/api/orders")
@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        var order = orderService.createOrder(orderRequest);
        return ResponseEntity.created(URI.create("/api/orders/" + order.id())).body(order);
    }
}