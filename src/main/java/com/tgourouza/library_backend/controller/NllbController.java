package com.tgourouza.library_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgourouza.library_backend.dto.nllb.TranslateResponse;
import com.tgourouza.library_backend.service.NllbService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/nllb")
public class NllbController {

    private final NllbService nllbService;

    public NllbController(NllbService nllbService) {
        this.nllbService = nllbService;
    }

    @GetMapping("/translate-description")
    public ResponseEntity<TranslateResponse> translateDescription(@RequestParam String text,
            @RequestParam String sourceLanguage,
            @RequestParam String targetLanguage) {
        try {
            TranslateResponse resp = nllbService.translateDescription(text, sourceLanguage, targetLanguage);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            log.error("NLLB translate-description failed (text='{}', sourceLanguage='{}', targetLanguage='{}')",
                    text, sourceLanguage, targetLanguage, e);
            return ResponseEntity.status(502).build();
        }
    }

    // TODO: remove
    @GetMapping("/detect-language")
    public ResponseEntity<String> detectLanguage(@RequestParam String text) {
        return ResponseEntity.ok(nllbService.detectLanguage(text).toString());
    }
}
