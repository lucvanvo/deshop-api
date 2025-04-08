package com.example.demo.exception;

public class ResourceExistedException extends RuntimeException {
    public ResourceExistedException(String message) {
        super(message);
    }

}
