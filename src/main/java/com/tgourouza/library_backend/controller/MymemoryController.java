package com.tgourouza.library_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.tgourouza.library_backend.dto.Multilingual;
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

    @GetMapping(value = "/translate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Multilingual> translate(
            @RequestParam String title) {
        try {
            if (title == null || title.trim().isEmpty()) {
                return ResponseEntity.ok(new Multilingual(null, null, null, null, null, null, null, null));
            }
            Multilingual translations = mymemoryService.translateTitle(title.trim());
            return ResponseEntity.ok(translations);

        } catch (HttpClientErrorException e) {
            log.error("MyMemory client error: {} {}", e.getStatusCode(), e.getResponseBodyAsString());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new Multilingual(title, title, title, title, title, title, title, title));
        } catch (Exception e) {
            log.error("MyMemory call failed", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new Multilingual(title, title, title, title, title, title, title, title));
        }
    }
}
