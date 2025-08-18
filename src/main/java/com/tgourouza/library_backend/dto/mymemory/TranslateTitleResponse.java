package com.tgourouza.library_backend.dto.mymemory;

public record TranslateTitleResponse(
        int status,
        String sourceText,
        String sourceLang,
        String targetLang,
        String translatedText,
        double match,
        String details) {
}
