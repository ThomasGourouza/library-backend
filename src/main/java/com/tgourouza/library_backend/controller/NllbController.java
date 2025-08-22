package com.tgourouza.library_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/translate")
    public ResponseEntity<Multilingual> translateText(@RequestParam String text) {
        try {
            if (text == null || text.trim().isEmpty()) {
                return ResponseEntity.ok(new Multilingual(null, null, null, null, null, null, null, null));
            }
            Multilingual translation = nllbService.translateText(text.trim());
            return ResponseEntity.ok(translation);

        } catch (Exception e) {
            log.error("NLLB translate failed (text='{}')",
                    text, e.getMessage());
            return ResponseEntity.status(502).build();
        }
    }
}
