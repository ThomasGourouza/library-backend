package com.tgourouza.library_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    public HealthController() {
    }

    @GetMapping
    public ResponseEntity<Void> checkHealth() {
        return ResponseEntity.ok().build();
    }
}
