package com.example.demo.controller.categories;

import com.example.demo.model.Category;

public record CategoryResponse(Long id, String name) {

    public static CategoryResponse fromCategory(Category newCategory) {
        return new CategoryResponse(newCategory.getId(), newCategory.getName());
    }

}