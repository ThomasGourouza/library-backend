package com.tgourouza.library_backend.constant;

public enum Status {
    UNREAD("Non lu"),
    TO_READ("À lire"),
    READING("En cours de lecture"),
    READ("Lu");

    private String value;

    Status(String value) {
        this.value = value;
    }
}
