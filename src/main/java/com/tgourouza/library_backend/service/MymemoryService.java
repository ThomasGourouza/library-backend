package com.tgourouza.library_backend.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.mymemory.TranslateTitleResponse;
import com.tgourouza.library_backend.mapper.MyMemoryLangMapper;
import static com.tgourouza.library_backend.util.utils.cleanAndTitleCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MymemoryService {
    private final RestClient mymemoryClient;
    private final MyMemoryLangMapper myMemoryLangMapper;
    private final LanguageDetector detector;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${mymemory.email}")
    private String email;

    public MymemoryService(@Qualifier("mymemoryRestClient") RestClient mymemoryClient,
            MyMemoryLangMapper myMemoryLangMapper, LanguageDetector detector) {
        this.mymemoryClient = mymemoryClient;
        this.myMemoryLangMapper = myMemoryLangMapper;
        this.detector = detector;
    }

    public Multilingual translateTitle(String title) throws JsonMappingException, JsonProcessingException {
        Language detected = detector.detectLanguageOf(title);
        if (detected == Language.UNKNOWN) {
            throw new IllegalArgumentException("Could not detect source language");
        }
        return new Multilingual(
                cleanAndTitleCase(translate(title, detected, Language.FRENCH)),
                cleanAndTitleCase(translate(title, detected, Language.SPANISH)),
                cleanAndTitleCase(translate(title, detected, Language.ITALIAN)),
                cleanAndTitleCase(translate(title, detected, Language.PORTUGUESE)),
                cleanAndTitleCase(translate(title, detected, Language.ENGLISH)),
                cleanAndTitleCase(translate(title, detected, Language.GERMAN)),
                cleanAndTitleCase(translate(title, detected, Language.RUSSIAN)),
                cleanAndTitleCase(translate(title, detected, Language.JAPANESE)));
    }

    private String translate(String title, Language source, Language target) {
        try {
            String src = myMemoryLangMapper.toIso(source)
                    .orElseThrow(() -> new IllegalArgumentException("Unsupported source language: " + source));
            String tgt = myMemoryLangMapper.toIso(target)
                    .orElseThrow(() -> new IllegalArgumentException("Unsupported target language: " + target));
            if (src.equals(tgt)) {
                return title;
            }
            String resp = mymemoryTranslation(title, src, tgt);
            if (resp == null || resp.isBlank())
                throw new IllegalStateException("Empty translation from MyMemory API");

            return getTranslation(resp, title, source, target).translatedText();
        } catch (Exception ex) {
            log.error("Translation error: {}", ex.getMessage());
            return title;
        }
    }

    public TranslateTitleResponse getTranslation(String body, String title, Language sourceLanguage,
            Language targetLanguage)
            throws JsonMappingException, JsonProcessingException {
        JsonNode root = mapper.readTree(body);
        int status = root.path("responseStatus").asInt(0);
        String details = root.path("responseDetails").asText("");
        if (status != 200) {
            log.warn("MyMemory non-200: {} - {}. source: {}, target: {}", status, details, sourceLanguage,
                    targetLanguage);
            throw new IllegalStateException("Empty translation from MyMemory API");
        }
        JsonNode rd = root.path("responseData");
        String translated = rd.path("translatedText").asText("");
        double match = rd.path("match").asDouble(0.0);
        return new TranslateTitleResponse(
                status,
                title,
                sourceLanguage.toString(),
                targetLanguage.toString(),
                translated,
                match,
                details);
    }

    public String mymemoryTranslation(String title, String sourceLanguage, String targetLanguage)
            throws JsonMappingException, JsonProcessingException {
        // https://api.mymemory.translated.net/get?q={text}&langpair=en|fr
        return mymemoryClient.get()
                .uri(uri -> uri.path("/get")
                        .queryParam("q", title)
                        .queryParam("langpair", sourceLanguage + "|" + targetLanguage)
                        // .queryParam("de", email)
                        .build())
                .retrieve()
                .body(String.class);
    }
}
