package com.tgourouza.library_backend.service;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.tgourouza.library_backend.constant.Language;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.libretranslate.DetectResult;
import com.tgourouza.library_backend.dto.libretranslate.TranslateResponse;
import com.tgourouza.library_backend.mapper.IsoLangMapper;

@Service
public class LibreTranslateService {

    private final RestClient libretranslateClient;
    private final IsoLangMapper isoLangMapper;

    public LibreTranslateService(
            @Qualifier("libreTranslateClient") RestClient libretranslateClient,
            IsoLangMapper isoLangMapper) {
        this.libretranslateClient = libretranslateClient;
        this.isoLangMapper = isoLangMapper;
    }

    public DetectResult detect(String text) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("q", Objects.requireNonNull(text, "q (text) is required"));

        DetectResult[] arr = libretranslateClient.post()
                .uri("/detect")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(DetectResult[].class);

        return arr == null ? null : Arrays.stream(arr).findFirst().orElse(null);
    }

    public Language detectLanguage(String text) {
        return isoLangMapper.toLanguage(detect(text).language());
    }

    public TranslateResponse translate(String text, String source, String target) {
        if (text == null || text.isBlank()) {
            return new TranslateResponse(text);
        }
        if (source == null || source.isBlank() || source.equals("auto")) {
            source = detect(text).language();
        }
        if (source.equals(target)) {
            return new TranslateResponse(text);
        }
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("q", Objects.requireNonNull(text, "q (text) is required"));
        form.add("source", source);
        form.add("target", Objects.requireNonNull(target, "target is required"));

        return libretranslateClient.post()
                .uri("/translate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(TranslateResponse.class);
    }

    public String translateText(String text, Language targetLanguage) {
        String target = isoLangMapper.toIso(targetLanguage);
        return translate(text, null, target).translatedText();
    }

    private String translateTextFromSource(String text, Language sourceLanguage, Language targetLanguage) {
        String target = isoLangMapper.toIso(targetLanguage);
        String source = isoLangMapper.toIso(sourceLanguage);
        return translate(text, source, target).translatedText();
    }

    public Multilingual translateTextMultilingual(String text, Language source) {
        return new Multilingual(
                translateTextFromSource(text, source, Language.FRENCH),
                translateTextFromSource(text, source, Language.SPANISH),
                translateTextFromSource(text, source, Language.ITALIAN),
                translateTextFromSource(text, source, Language.PORTUGUESE),
                translateTextFromSource(text, source, Language.ENGLISH),
                translateTextFromSource(text, source, Language.GERMAN),
                translateTextFromSource(text, source, Language.RUSSIAN),
                translateTextFromSource(text, source, Language.JAPANESE));
    }
}
