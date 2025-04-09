package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.controller.products.ProductRequest;
import com.example.demo.controller.products.ProductResponse;
import com.example.demo.exception.ResourceExistedException;
import com.example.demo.exception.auth.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse createProduct(ProductRequest request) throws IllegalStateException, IOException {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new UnauthorizedException("You are not authorized to create a product.");
        }

        // Check if the product already exists
        if (productRepository.existsByName(request.name())) {
            throw new ResourceExistedException("Product already exists with name: " + request.name());
        }

        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + request.categoryId()));

        // Save image to resoures folder
        var imageFile = request.image();
        var originalFilename = imageFile.getOriginalFilename();
        if (imageFile.isEmpty() || originalFilename == null) {
            throw new IllegalStateException("Image file is missing or empty");
        }
        Path imagePath = Paths.get("D:/images");
        Files.createDirectories(imagePath); // Create the directory if it doesn't exist

        imagePath = imagePath.resolve(
                +TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "."
                        + originalFilename.substring(originalFilename.lastIndexOf(".") + 1));

        imageFile.transferTo(imagePath.toFile()); // Save the file to the specified path

        // Save the product to the database
        var product = request.toProduct(); // Convert request to product entity
        product.setImageUrl(imagePath.toString()); // Set the image path in the product object
        category.addProduct(product); // Add the product to the category
        var savedProduct = productRepository.save(product);
        return ProductResponse.fromProduct(savedProduct, category.getId());
    }

    public List<ProductResponse> getAllProducts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
    }

    public void deleteProduct(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

}
