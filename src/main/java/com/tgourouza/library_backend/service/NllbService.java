package com.tgourouza.library_backend.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
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

    public TranslateResponse translateDescription(String text, String sourceLanguage, String targetLanguage) {
        return nllbClient.post()
                .uri("/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("text", text, "source", sourceLanguage, "target", targetLanguage))
                .retrieve()
                .body(TranslateResponse.class);
    }

    // public Language detectLanguage(String text) {
    // LanguageDetector detector =
    // LanguageDetectorBuilder.fromAllLanguages().build();
    // return detector.detectLanguageOf(text);
    // }

    /** Translate with explicit source/target Lingua languages. */
    public String translate(String text, Language source, Language target) {
        String src = NllbLangMapper.toNllb(source)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported source language: " + source));
        String tgt = NllbLangMapper.toNllb(target)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported target language: " + target));

        TranslateResponse resp = translateDescription(text, src, tgt);

        if (resp == null || resp.translation() == null)
            throw new IllegalStateException("Empty translation from NLLB server");
        return resp.translation();
    }

    /** Translate by auto-detecting the source with Lingua. */
    public String translateAutoDetect(String text, Language target) {
        Language detected = detector.detectLanguageOf(text);
        if (detected == Language.UNKNOWN) {
            throw new IllegalArgumentException("Could not detect source language");
        }
        return translate(text, detected, target);
    }
}
