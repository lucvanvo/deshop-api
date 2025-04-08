package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.ResourceExistedException;
import com.example.demo.exception.ResourceNotFoundException;

@ControllerAdvice

public class GlobalControllerAdvice {

    @ExceptionHandler(ResourceExistedException.class)
    public ResponseEntity<String> handleResourceExistedException(ResourceExistedException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}