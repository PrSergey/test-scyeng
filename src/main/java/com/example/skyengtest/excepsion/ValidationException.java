package com.example.skyengtest.excepsion;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
