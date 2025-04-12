package com.example.demo.controller.products;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @PostMapping(produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<ProductResponse> createProduct(@ModelAttribute ProductRequest request)
            throws IllegalStateException, IOException {
        var response = productService.createProduct(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<Iterable<ProductResponse>> getAll() {
        var products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
            @ModelAttribute ProductRequest request) throws IOException {
        var response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        var product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }
}
