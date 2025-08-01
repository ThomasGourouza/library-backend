package com.tgourouza.library_backend.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String type, String value) {
        super(type + " with value '" + value + "' is not valid.");
    }
}
