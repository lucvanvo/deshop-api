package com.example.demo.controller.categories;

import com.example.demo.model.Category;

public record CategoryRequest(String name) {

    public Category toCategory() {
        return new Category(null, name);
    }
}
