package com.tgourouza.library_backend.constant;

public enum LiteraryGenre {
    THEATER("Théâtre"),
    NOVEL("Roman"),
    POETRY("Poésie"),
    ESSAY("Essai"),
    COMICS("BD"),
    SCIENCE_FICTION("Science-fiction"),
    FANTASY("Fantasy"),
    BIOGRAPHY("Biographie"),
    HISTORY("Histoire"),
    TALE("Conte");

    private String value;

    LiteraryGenre(String value) {
        this.value = value;
    }
    
}
