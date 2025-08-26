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
    private final NllbLangMapper nllbLangMapper;
    private final LanguageDetector detector;

    public NllbService(@Qualifier("nllbClient") RestClient nllbClient, LanguageDetector detector,
            NllbLangMapper nllbLangMapper) {
        this.nllbClient = nllbClient;
        this.detector = detector;
        this.nllbLangMapper = nllbLangMapper;
    }

    public Multilingual translateText(String text, Language sourceLanguage) {
        Language source = sourceLanguage != null ? sourceLanguage : detector.detectLanguageOf(text);
        return new Multilingual(
                translate(text, source, Language.FRENCH),
                translate(text, source, Language.SPANISH),
                translate(text, source, Language.ITALIAN),
                translate(text, source, Language.PORTUGUESE),
                translate(text, source, Language.ENGLISH),
                translate(text, source, Language.GERMAN),
                translate(text, source, Language.RUSSIAN),
                translate(text, source, Language.JAPANESE));
    }

    private String translate(String text, Language source, Language target) {
        if (text == null || text.isBlank() || source == Language.UNKNOWN) {
            return text;
        }
        String src = nllbLangMapper.toNllb(source)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported source language: " + source));
        String tgt = nllbLangMapper.toNllb(target)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported target language: " + target));
        if (src.equals(tgt)) {
            return text;
        }
        TranslateResponse resp = nllbTranslation(text, src, tgt);
        if (resp == null || resp.translation() == null)
            throw new IllegalStateException("Empty translation from NLLB server");
        return resp.translation();
    }

    public String translateToEnglish(String text) {
        Language source = detector.detectLanguageOf(text);
        Language target = Language.ENGLISH;
        return translate(text, source, target);
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
