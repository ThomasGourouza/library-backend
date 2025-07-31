package com.tgourouza.library_backend.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String dataName, String idOrName) {
        super(dataName + idOrName + " not found");
    }
}
