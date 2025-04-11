package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.controller.order.OrderDetailsResponse;
import com.example.demo.controller.order.OrderRequest;
import com.example.demo.controller.order.OrderResponse;
import com.example.demo.model.OrderDetails;
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

        var order = orderRepository.save(orderRequest.toOrder());

        var orderDetailsRequests = orderRequest.orderDetails();

        var orderDetailsList = orderDetailsRequests.stream()
                .map(orderDetailsRequest -> {
                    var orderDetails = new OrderDetails();
                    orderDetails.setOrder(order);
                    var product = productRepository.findById(orderDetailsRequest.productId())
                            .orElse(null);
                    orderDetails.setProduct(product);
                    orderDetails.setQuantity(orderDetailsRequest.quantity());
                    return orderDetailsRepository.save(orderDetails);
                }).map(OrderDetailsResponse::formOrderDetails).toList();

        return OrderResponse.from(order, orderDetailsList);
    }

}
