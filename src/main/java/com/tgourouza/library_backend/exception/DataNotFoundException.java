package com.tgourouza.library_backend.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String dataName, String id) {
        super(dataName + " with id " + id + " not found");
    }
}
