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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controller.products.ProductRequest;
import com.example.demo.controller.products.ProductResponse;
import com.example.demo.exception.ResourceExistedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.auth.UnauthorizedException;
import com.example.demo.model.Product;
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

        Path imagePath = saveImage(request.image());// Save the image and get the path

        // Save the product to the database
        var product = request.toProduct(); // Convert request to product entity
        product.setImageUrl(imagePath.toString()); // Set the image path in the product object
        product.setCategory(category); // Set the category in the product object
        var savedProduct = productRepository.save(product);
        return ProductResponse.fromProduct(savedProduct);
    }

    private Path saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalStateException("Image file is missing or empty");
        }

        // Save image to resoures folder
        var originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalStateException("Image file name is missing");
        }
        Path imagePath = Paths.get("D:/images");
        Files.createDirectories(imagePath); // Create the directory if it doesn't exist

        imagePath = imagePath.resolve(
                +TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "."
                        + originalFilename.substring(originalFilename.lastIndexOf(".") + 1));

        imageFile.transferTo(imagePath.toFile()); // Save the file to the specified path
        return imagePath;
    }

    public List<ProductResponse> getAllProducts() {
        var productDetailsList = productRepository.getAllProductsDetails();
        if (productDetailsList.isEmpty()) {
            throw new ResourceNotFoundException("No products found.");
        }
        return productDetailsList.stream()
                .map(ProductResponse::fromProductDetails)
                .toList();
    }

    public void deleteProduct(Long id) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new UnauthorizedException("You are not authorized to delete a product.");
        }
        productRepository.deleteById(id);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) throws IOException {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new UnauthorizedException("You are not authorized to update a product.");
        }

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Update the product details
        updateProduct(product, request);

        // Save the updated product to the database
        var updatedProduct = productRepository.save(product);
        return ProductResponse.fromProduct(updatedProduct);
    }

    private void updateProduct(Product product, ProductRequest request) throws IOException {
        if (request.name() != null) {
            product.setName(request.name());
        }
        if (request.description() != null) {
            product.setDescription(request.description());
        }
        if (request.price() != null) {
            product.setPrice(request.price());
        }
        if (request.categoryId() != null) {
            var category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Category not found with id: " + request.categoryId()));
            product.setCategory(category);
        }
        if (request.image() != null && !request.image().isEmpty()) {
            var path = saveImage(request.image());
            deleteImage(product.getImageUrl()); // Delete the old image file
            product.setImageUrl(path.toString());
        }
    }

    private void deleteImage(String imageUrl) {
        if (imageUrl != null) {
            try {
                Files.deleteIfExists(Paths.get(imageUrl)); // Delete the old image file
            } catch (IOException e) {
                throw new IllegalStateException("Failed to delete old image file: " + e.getMessage(), e);
            }
        }
    }
}
