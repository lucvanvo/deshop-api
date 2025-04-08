package com.example.demo.controller.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CategoryService;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

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
        return ResponseEntity.ok(categoriesService.getAllCategories());
    }

}
