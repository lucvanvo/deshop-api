package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.OrderDetail;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
