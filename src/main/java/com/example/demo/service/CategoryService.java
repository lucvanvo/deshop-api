package com.example.demo.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.demo.controller.categories.CategoryRequest;
import com.example.demo.controller.categories.CategoryResponse;
import com.example.demo.controller.exception.ResourceExistedException;
import com.example.demo.controller.exception.auth.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        // Check if user is authenticated and has the right role
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new UnauthorizedException("You are not authorized to create a category.");
        }

        // Check if the category already exists
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new ResourceExistedException("Category already exists with name: " + categoryRequest.name());
        }

        var newCategory = categoryRepository.save(categoryRequest.toCategory());
        return CategoryResponse.fromCategory(newCategory);
    }

}
