package com.tgourouza.library_backend.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message + " not found");
    }
}
