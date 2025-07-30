package com.tgourouza.library_backend.constant;

public enum Language {
    UNKNOWN("Inconnu"),
    FR("Français"),
    EN("Anglais"),
    ES("Espagnol"),
    DE("Allemand");

    private String value;

    Language(String value) {
        this.value = value;
    }
    
}
