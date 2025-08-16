package com.tgourouza.library_backend.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import com.tgourouza.library_backend.dto.nllb.TranslateResponse;

@Service
public class NllbService {
    private final RestClient nllbClient;

    public NllbService(@Qualifier("nllbClient") RestClient nllbClient) {
        this.nllbClient = nllbClient;
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

    public Language detectLanguage(String text) {
        LanguageDetector detector = LanguageDetectorBuilder.fromAllLanguages().build();
        return detector.detectLanguageOf(text);
    }
}
