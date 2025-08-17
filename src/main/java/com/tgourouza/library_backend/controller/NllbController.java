package com.tgourouza.library_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pemistahl.lingua.api.Language;
import com.tgourouza.library_backend.dto.Multilingual;
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

    @GetMapping("/translate-text")
    public ResponseEntity<Multilingual> translateText(@RequestParam String text) {
        try {
            Multilingual translation = nllbService.translateText(text);
            return ResponseEntity.ok(translation);
        } catch (Exception e) {
            log.error("NLLB translate-text failed (text='{}')",
                    text, e);
            return ResponseEntity.status(502).build();
        }
    }

    // TODO: remove
    @GetMapping("/detect-language")
    public ResponseEntity<String> detectLanguage(@RequestParam String text) {
        return ResponseEntity.ok(nllbService.detectLanguage(text).toString());
    }
}
