package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.constant.LanguageCreateRequest;
import com.tgourouza.library_backend.dto.constant.LanguageDTO;
import com.tgourouza.library_backend.service.LanguageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/languages")
public class LanguageController {
    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<List<LanguageDTO>> getAll() {
        return ResponseEntity.ok(languageService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(languageService.getById(id));
    }

    @PostMapping
    public ResponseEntity<LanguageDTO> create(@Valid @RequestBody LanguageCreateRequest request) {
        LanguageDTO created = languageService.save(request);
        URI location = URI.create("/languages/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<LanguageDTO>> createBatch(@Valid @RequestBody List<LanguageCreateRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(languageService.saveAll(requests));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        languageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
