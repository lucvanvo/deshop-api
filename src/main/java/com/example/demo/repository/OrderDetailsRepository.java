package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    List<OrderDetails> findByOrderId(Long id);

    @Query("SELECT SUM(od.quantity * p.price) FROM OrderDetails od join Product p on p.id = od.product.id join Order o on o.id = od.order.id where od.order.id = :id")
    Long sumPriceByOrderId(Long id);

}
