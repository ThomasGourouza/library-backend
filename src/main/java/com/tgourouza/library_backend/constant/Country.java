package com.tgourouza.library_backend.constant;

public enum Country {
    UNKNOWN("Inconnu"),
    FR("France"),
    GB("Grande-Bretagne"),
    ES("Espagne"),
    DE("Allemagne");

    private String value;

    Country(String value) {
        this.value = value;
    }
    
}
