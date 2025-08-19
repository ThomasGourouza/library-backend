package com.tgourouza.library_backend.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String genre, String value) {
        super(genre + " with value '" + value + "' is not valid.");
    }
}
