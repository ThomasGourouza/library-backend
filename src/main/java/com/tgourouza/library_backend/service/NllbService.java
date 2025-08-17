package com.tgourouza.library_backend.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.nllb.TranslateResponse;
import com.tgourouza.library_backend.mapper.NllbLangMapper;

@Service
public class NllbService {
    private final RestClient nllbClient;
    private final LanguageDetector detector;

    public NllbService(@Qualifier("nllbClient") RestClient nllbClient, LanguageDetector detector) {
        this.nllbClient = nllbClient;
        this.detector = detector;
    }

    public Multilingual translateText(String text) {
        Language detected = detector.detectLanguageOf(text);
        if (detected == Language.UNKNOWN) {
            throw new IllegalArgumentException("Could not detect source language");
        }
        return new Multilingual(
            translate(text, detected, Language.FRENCH),
            translate(text, detected, Language.SPANISH),
            translate(text, detected, Language.ITALIAN),
            translate(text, detected, Language.PORTUGUESE),
            translate(text, detected, Language.ENGLISH),
            translate(text, detected, Language.GERMAN),
            translate(text, detected, Language.RUSSIAN),
            translate(text, detected, Language.JAPANESE)
        );
    }

    private String translate(String text, Language source, Language target) {
        String src = NllbLangMapper.toNllb(source)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported source language: " + source));
        String tgt = NllbLangMapper.toNllb(target)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported target language: " + target));
        if (src.equals(tgt)) {
            return text;
        }
        TranslateResponse resp = nllbTranslation(text, src, tgt);

        if (resp == null || resp.translation() == null)
            throw new IllegalStateException("Empty translation from NLLB server");
        return resp.translation();
    }

    private TranslateResponse nllbTranslation(String text, String sourceLanguage, String targetLanguage) {
        return nllbClient.post()
                .uri("/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("text", text, "source", sourceLanguage, "target", targetLanguage))
                .retrieve()
                .body(TranslateResponse.class);
    }
}
