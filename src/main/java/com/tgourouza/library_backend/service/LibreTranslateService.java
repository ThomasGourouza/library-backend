package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.libretranslate.DetectResult;
import com.tgourouza.library_backend.dto.libretranslate.TranslateResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class LibreTranslateService {

    private final RestClient libretranslateClient;

    public LibreTranslateService(@Qualifier("libreTranslateClient") RestClient libretranslateClient) {
        this.libretranslateClient = libretranslateClient;
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

    public TranslateResponse translate(String text, String source, String target) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("q", Objects.requireNonNull(text, "q (text) is required"));
        form.add("source", (source == null || source.isBlank()) ? "auto" : source);
        form.add("target", Objects.requireNonNull(target, "target is required"));

        return libretranslateClient.post()
                .uri("/translate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(TranslateResponse.class);
    }
}
