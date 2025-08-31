package com.tgourouza.library_backend.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgourouza.library_backend.constant.Language;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.mymemory.TranslateTitleResponse;
import com.tgourouza.library_backend.mapper.IsoLangMapper;
import static com.tgourouza.library_backend.util.utils.cleanAndTitleCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MymemoryService {

    private final RestClient mymemoryClient;
    private final IsoLangMapper isoLangMapper;
    private final LibreTranslateService libreTranslateService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${mymemory.email}")
    private String email;

    public MymemoryService(
            @Qualifier("mymemoryRestClient") RestClient mymemoryClient,
            IsoLangMapper isoLangMapper,
            LibreTranslateService libreTranslateService) {
        this.mymemoryClient = mymemoryClient;
        this.isoLangMapper = isoLangMapper;
        this.libreTranslateService = libreTranslateService;
    }

    public Multilingual translateTitle(String title, Language sourceLanguage) {
        Language source = sourceLanguage != null ? sourceLanguage : libreTranslateService.detectLanguage(title);
        if (source == Language.UNKNOWN) {
            throw new IllegalArgumentException("Could not detect source language");
        }
        return new Multilingual(
                cleanAndTitleCase(translate(title, source, Language.FRENCH)),
                cleanAndTitleCase(translate(title, source, Language.SPANISH)),
                cleanAndTitleCase(translate(title, source, Language.ITALIAN)),
                cleanAndTitleCase(translate(title, source, Language.PORTUGUESE)),
                cleanAndTitleCase(translate(title, source, Language.ENGLISH)),
                cleanAndTitleCase(translate(title, source, Language.GERMAN)),
                cleanAndTitleCase(translate(title, source, Language.RUSSIAN)),
                cleanAndTitleCase(translate(title, source, Language.JAPANESE)));
    }

    private String translate(String title, Language source, Language target) {
        try {
            String src = isoLangMapper.toIso(source);
            String tgt = isoLangMapper.toIso(target);
            if (src.equals(tgt)) {
                return title;
            }
            String resp = mymemoryTranslation(title, src, tgt);
            if (resp == null || resp.isBlank()) {
                throw new IllegalStateException("Empty translation from MyMemory API");
            }

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
                .queryParam("de", email)
                .build())
                .retrieve()
                .body(String.class);
    }
}
