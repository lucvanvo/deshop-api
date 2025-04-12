package com.example.demo.controller.order;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OrderService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(
            @RequestParam(defaultValue = "false", required = false) boolean withDetails,
            @RequestParam(required = false) List<Long> ids) {
        List<OrderResponse> orderResponses;
        if (withDetails) {
            orderResponses = orderService.getOrdersWithDetails(ids);
        } else {
            orderResponses = orderService.getOrdersWithoutDetails(ids);
        }

        if (orderResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(orderResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}