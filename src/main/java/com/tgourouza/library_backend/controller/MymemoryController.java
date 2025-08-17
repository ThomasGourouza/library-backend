package com.tgourouza.library_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.tgourouza.library_backend.dto.mymemory.TranslateTitleResponse;
import com.tgourouza.library_backend.service.MymemoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mymemory")
public class MymemoryController {

    private final MymemoryService mymemoryService;

    public MymemoryController(MymemoryService mymemoryService) {
        this.mymemoryService = mymemoryService;
    }

    @GetMapping(value = "/translate-title", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TranslateTitleResponse> translate(
            @RequestParam String title,
            @RequestParam(defaultValue = "en") String sourceLanguage,
            @RequestParam(defaultValue = "fr") String targetLanguage) {
        try {
            String body = mymemoryService.getBody(title, sourceLanguage, targetLanguage);
            if (body == null || body.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new TranslateTitleResponse().error(502, title, sourceLanguage, targetLanguage,
                                "Empty body from MyMemory"));
            }
            TranslateTitleResponse response = mymemoryService.getTranslation(body, title, sourceLanguage,
                    targetLanguage);
            if (response.getStatus() != 200) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(response);
            }
            return ResponseEntity.ok(response);

        } catch (HttpClientErrorException e) {
            log.error("MyMemory client error: {} {}", e.getStatusCode(), e.getResponseBodyAsString());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new TranslateTitleResponse().error(502, title, sourceLanguage, targetLanguage,
                            "Upstream client error: " + e.getStatusCode()));
        } catch (Exception e) {
            log.error("MyMemory call failed", e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new TranslateTitleResponse().error(502, title, sourceLanguage, targetLanguage,
                            "Call failed: " + e.getMessage()));
        }
    }
}
