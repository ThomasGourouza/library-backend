package com.tgourouza.library_backend.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgourouza.library_backend.dto.mymemory.TranslateTitleResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MymemoryService {
    private final RestClient mymemoryClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public MymemoryService(@Qualifier("mymemoryRestClient") RestClient mymemoryClient) {
        this.mymemoryClient = mymemoryClient;
    }

    public String getBody(String title, String sourceLanguage, String targetLanguage)
            throws JsonMappingException, JsonProcessingException {
        // https://api.mymemory.translated.net/get?q={text}&langpair=en|fr
        return mymemoryClient.get()
                .uri(uri -> uri.path("/get")
                        .queryParam("q", title)
                        .queryParam("langpair", sourceLanguage + "|" + targetLanguage)
                        .build())
                .retrieve()
                .body(String.class);
    }

    public TranslateTitleResponse getTranslation(String body, String title, String sourceLanguage,
            String targetLanguage)
            throws JsonMappingException, JsonProcessingException {
        JsonNode root = mapper.readTree(body);
        int status = root.path("responseStatus").asInt(0);
        String details = root.path("responseDetails").asText("");
        if (status != 200) {
            log.warn("MyMemory non-200: {} - {}", status, details);
            return new TranslateTitleResponse().error(status, title, sourceLanguage, targetLanguage,
                    "MyMemory error " + status + (details.isBlank() ? "" : (": " + details)));
        }
        JsonNode rd = root.path("responseData");
        String translated = rd.path("translatedText").asText("");
        double match = rd.path("match").asDouble(0.0);
        return new TranslateTitleResponse(
                status,
                title,
                sourceLanguage,
                targetLanguage,
                translated,
                match,
                details);
    }
}
