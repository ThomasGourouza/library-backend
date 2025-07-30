package com.tgourouza.library_backend.constant;

public enum Category {
    UNKNOWN("Inconnu"),
    CHILDREN("Enfants"),
    CLASSICS("Classiques");

    private String value;

    Category(String value) {
        this.value = value;
    }
}
