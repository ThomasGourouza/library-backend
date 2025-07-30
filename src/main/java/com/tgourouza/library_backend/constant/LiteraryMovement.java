package com.tgourouza.library_backend.constant;

public enum LiteraryMovement {
    UNKNOWN("Inconnu"),
    ENLIGHTENMENT("Lumières"),
    ROMANTICISM("Romantisme"),
    REALISM("Réalisme"),
    NATURALISM("Naturalisme"),
    SYMBOLISM("Symbolisme"),
    IMPRESSIONISM("Impressionnisme"),
    MODERNISM("Modernisme"),
    POSTMODERNISM("Postmodernisme"),
    SURREALISM("Surréalisme"),
    EXISTENTIALISM("Existentialisme");

    private String value;

    LiteraryMovement(String value) {
        this.value = value;
    }
    
}
