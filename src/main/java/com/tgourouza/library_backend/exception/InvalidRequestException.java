package com.tgourouza.library_backend.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String fieldName, String value) {
        super(fieldName + " with value '" + value + "' is not valid.");
    }
}
