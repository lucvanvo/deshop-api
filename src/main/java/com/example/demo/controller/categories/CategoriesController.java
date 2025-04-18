package com.example.demo.controller.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CategoryService;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoryService categoriesService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        var response = categoriesService.createCategory(request);
        return ResponseEntity.created(URI.create("/api/categories/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<Iterable<CategoryResponse>> getAll() {
        var categories = categoriesService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoriesService.getAllCategories());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoriesService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,
            @RequestBody CategoryRequest request) {
        var response = categoriesService.updateCategory(id, request);
        return ResponseEntity.ok(response);
    }
}
