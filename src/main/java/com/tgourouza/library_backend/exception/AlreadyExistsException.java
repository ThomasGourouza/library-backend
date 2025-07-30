package com.tgourouza.library_backend.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String type, String key) {
        super(type + " with value '" + key + "' already exists.");
    }
}
