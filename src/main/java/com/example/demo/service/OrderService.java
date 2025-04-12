package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.controller.order.OrderDetailsResponse;
import com.example.demo.controller.order.OrderRequest;
import com.example.demo.controller.order.OrderResponse;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderDetailsRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        var order = Order.builder()
                .address(orderRequest.address())
                .phoneNumber(orderRequest.phoneNumber())
                .orderPersonName(orderRequest.orderPersonName())
                .email(orderRequest.email())
                .orderDate(LocalDate.now())
                .notes(orderRequest.notes())
                .status(OrderStatus.NEW)
                .build();

        order = orderRepository.save(order);

        var orderDetailsRequests = orderRequest.orderDetails();
        List<OrderDetailsResponse> orderDetailsList = new ArrayList<>();

        for (var orderDetailsRequest : orderDetailsRequests) {
            var product = productRepository.findById(orderDetailsRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            var orderDetails = orderDetailsRequest.toOrderDetails(order, product);

            orderDetails = orderDetailsRepository.save(orderDetails);
            orderDetailsList
                    .add(OrderDetailsResponse.form(orderDetails, product.getPrice(),
                            product.getPrice() * orderDetails.getQuantity()));
        }

        var totalPrice = orderDetailsList.stream()
                .mapToLong(OrderDetailsResponse::price)
                .sum();

        return OrderResponse.from(order, orderDetailsList, totalPrice);
    }

}
