package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.controller.exception.ResourceExistedException;

@ControllerAdvice

public class GlobalControllerAdvice {
    // handle ResourceExistedException and other exceptions globally
    // and return a 409 Conflict response with the exception message
    @ExceptionHandler(ResourceExistedException.class)
    public ResponseEntity<String> handleResourceExistedException(ResourceExistedException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());

    }
}