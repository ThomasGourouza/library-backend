package com.tgourouza.library_backend.constant;

public enum Gender {
    UNKNOWN("Inconnu"),
    MAN("Homme"),
    WOMAN("Femme");

    private String value;

    Gender(String value) {
        this.value = value;
    }
    
}
