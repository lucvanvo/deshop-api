package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.controller.order.OrderDetailsResponse;
import com.example.demo.controller.order.OrderRequest;
import com.example.demo.controller.order.OrderResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.auth.UnauthorizedException;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.User;
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
                .orderDate(LocalDateTime.now())
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
                    .add(OrderDetailsResponse.from(orderDetails));
        }

        var totalPrice = orderDetailsList.stream()
                .mapToLong(OrderDetailsResponse::price)
                .sum();

        return OrderResponse.from(order, orderDetailsList, totalPrice);
    }

    public List<OrderResponse> getOrdersWithDetails(List<Long> ids) {

        var orders = ids == null || ids.isEmpty() ? orderRepository.findAll() : orderRepository.findAllById(ids);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (var order : orders) {
            var orderDetailsList = orderDetailsRepository.findByOrderId(order.getId());
            var orderDetailsResponse = orderDetailsList.stream().map(OrderDetailsResponse::from).toList();
            var totalPrice = orderDetailsResponse.stream()
                    .mapToLong(OrderDetailsResponse::price)
                    .sum();
            OrderResponse orderResponse = OrderResponse.from(order, orderDetailsResponse, totalPrice);
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    public List<OrderResponse> getOrdersWithoutDetails(List<Long> ids) {
        var orders = ids == null || ids.isEmpty() ? orderRepository.findAll() : orderRepository.findAllById(ids);

        return orders.stream()
                .map(order -> {
                    var totalPrice = orderDetailsRepository.sumPriceByOrderId(order.getId());
                    return OrderResponse.from(order, null, totalPrice);
                })
                .toList();
    }

    public void deleteOrder(Long id) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new UnauthorizedException("User is not authorized to create an order.");
        }
        orderDetailsRepository.findByOrderId(id).forEach(orderDetailsRepository::delete);
        orderRepository.deleteById(id);
    }

    public void updateOrderStatus(Long id, OrderStatus status) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    public void cancelOrder(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (order.getStatus() == OrderStatus.CANCELLED) {
            return;
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
