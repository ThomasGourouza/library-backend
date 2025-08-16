package com.tgourouza.library_backend.dto.mymemory;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateTitleResponse {
    private String sourceText; // texte source
    private String sourceLang; // en
    private String targetLang; // fr
    private String translatedText;
    private double match; // score aproximatif
    private String details; // éventuels détails/erreurs MyMemory

    public TranslateTitleResponse error(String sourceText, String src, String tgt, String details) {
        return new TranslateTitleResponse(Objects.toString(sourceText, ""),
                Objects.toString(src, ""), Objects.toString(tgt, ""),
                "", 0.0, details);
    }
}
