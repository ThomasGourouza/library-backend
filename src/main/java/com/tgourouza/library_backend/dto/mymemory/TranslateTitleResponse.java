package com.tgourouza.library_backend.dto.mymemory;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateTitleResponse {
    private int status;
    private String sourceText; // texte source
    private String sourceLang; // en
    private String targetLang; // fr
    private String translatedText;
    private double match; // score aproximatif
    private String details; // éventuels détails/erreurs MyMemory

    public TranslateTitleResponse error(int status, String sourceText, String sourceLang, String targetLang,
            String details) {
        return new TranslateTitleResponse(status, Objects.toString(sourceText, ""),
                Objects.toString(sourceLang, ""), Objects.toString(targetLang, ""),
                "", 0.0, details);
    }
}
