package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    @Query("SELECT p.id as id, p.name as name, p.description as description, p.imageUrl as imageUrl, p.price as price, c.name AS categoryName, c.id AS categoryId "
            + "FROM Product p JOIN Category c on p.category.id = c.id")
    List<ProductDetails> getAllProductsDetails();

}
