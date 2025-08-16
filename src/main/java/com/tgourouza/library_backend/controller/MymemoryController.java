package com.tgourouza.library_backend.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mymemory")
public class MymemoryController {

    // TODO: use Service
    private final RestClient rest;
    private final ObjectMapper mapper = new ObjectMapper();

    public MymemoryController(@Qualifier("mymemoryRestClient") RestClient rest) {
        this.rest = rest;
    }

    @GetMapping(value = "/translate-title", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TranslateResponse> translate(
            @RequestParam String title,
            @RequestParam(defaultValue = "en") String sourceLanguage,
            @RequestParam(defaultValue = "fr") String targetLanguage) {
        try {
            // Appel MyMemory:
            // https://api.mymemory.translated.net/get?q={text}&langpair=en|fr
            String body = rest.get()
                    .uri(uri -> uri.path("/get")
                            .queryParam("q", title)
                            .queryParam("langpair", sourceLanguage + "|" + targetLanguage)
                            .build())
                    .retrieve()
                    .body(String.class);

            if (body == null || body.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(TranslateResponse.error(title, sourceLanguage, targetLanguage,
                                "Empty body from MyMemory"));
            }

            JsonNode root = mapper.readTree(body);
            int status = root.path("responseStatus").asInt(0);
            String details = root.path("responseDetails").asText("");

            // MyMemory renvoie 200 en cas de succès
            if (status != 200) {
                log.warn("MyMemory non-200: {} - {}", status, details);
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(TranslateResponse.error(title, sourceLanguage, targetLanguage,
                                "MyMemory error " + status + (details.isBlank() ? "" : (": " + details))));
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
            return ResponseEntity.ok(resp);

        } catch (HttpClientErrorException e) {
            log.error("MyMemory client error: {} {}", e.getStatusCode(), e.getResponseBodyAsString());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(TranslateResponse.error(title, sourceLanguage, targetLanguage,
                            "Upstream client error: " + e.getStatusCode()));
        } catch (Exception e) {
            log.error("MyMemory call failed", e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(TranslateResponse.error(title, sourceLanguage, targetLanguage,
                            "Call failed: " + e.getMessage()));
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
