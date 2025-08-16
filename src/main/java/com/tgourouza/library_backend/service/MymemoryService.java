package com.tgourouza.library_backend.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MymemoryService {
    private final RestClient mymemoryClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public MymemoryService(@Qualifier("mymemoryRestClient") RestClient mymemoryClient) {
        this.mymemoryClient = mymemoryClient;
    }

    public TranslateResponse translate(String title, String sourceLanguage, String targetLanguage) {
        try {
            // Appel MyMemory:
            // https://api.mymemory.translated.net/get?q={text}&langpair=en|fr
            String body = mymemoryClient.get()
                    .uri(uri -> uri.path("/get")
                            .queryParam("q", title)
                            .queryParam("langpair", sourceLanguage + "|" + targetLanguage)
                            .build())
                    .retrieve()
                    .body(String.class);

            if (body == null || body.isBlank()) {
                // return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                // .body(TranslateResponse.error(title, sourceLanguage, targetLanguage,
                // "Empty body from MyMemory"));
                return TranslateResponse.error(title, sourceLanguage, targetLanguage, "Empty body from MyMemory");
            }

            JsonNode root = mapper.readTree(body);
            int status = root.path("responseStatus").asInt(0);
            String details = root.path("responseDetails").asText("");

            // MyMemory renvoie 200 en cas de succès
            if (status != 200) {
                log.warn("MyMemory non-200: {} - {}", status, details);
                // return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                // .body(TranslateResponse.error(title, sourceLanguage, targetLanguage,
                // "MyMemory error " + status + (details.isBlank() ? "" : (": " + details))));
                return TranslateResponse.error(title, sourceLanguage, targetLanguage,
                        "MyMemory error " + status + (details.isBlank() ? "" : (": " + details)));
            }

            JsonNode rd = root.path("responseData");
            String translated = rd.path("translatedText").asText("");
            double match = rd.path("match").asDouble(0.0);

            TranslateResponse resp = new TranslateResponse(
                    title,
                    sourceLanguage,
                    targetLanguage,
                    translated,
                    match,
                    details);
            // return ResponseEntity.ok(resp);
            return resp;

        } catch (HttpClientErrorException e) {
            log.error("MyMemory client error: {} {}", e.getStatusCode(), e.getResponseBodyAsString());
            // return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
            // .body(TranslateResponse.error(title, sourceLanguage, targetLanguage,
            // "Upstream client error: " + e.getStatusCode()));
            return TranslateResponse.error(title, sourceLanguage, targetLanguage,
                    "Upstream client error: " + e.getStatusCode());
        } catch (Exception e) {
            log.error("MyMemory call failed", e);
            // return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
            // .body(TranslateResponse.error(title, sourceLanguage, targetLanguage,
            // "Call failed: " + e.getMessage()));
            return TranslateResponse.error(title, sourceLanguage, targetLanguage,
                    "Call failed: " + e.getMessage());
        }
    }

    /* ======================== DTO ======================== */

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TranslateResponse {
        private String sourceText; // texte source
        private String sourceLang; // en
        private String targetLang; // fr
        private String translatedText;
        private double match; // score aproximatif
        private String details; // éventuels détails/erreurs MyMemory

        public static TranslateResponse error(String sourceText, String src, String tgt, String details) {
            return new TranslateResponse(Objects.toString(sourceText, ""),
                    Objects.toString(src, ""), Objects.toString(tgt, ""),
                    "", 0.0, details);
        }
    }
}
