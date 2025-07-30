package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.constant.LiteraryMovementCreateRequest;
import com.tgourouza.library_backend.dto.constant.LiteraryMovementDTO;
import com.tgourouza.library_backend.service.LiteraryMovementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/literary_movements")
public class LiteraryMovementController {
    private final LiteraryMovementService literaryMovementService;

    public LiteraryMovementController(LiteraryMovementService literaryMovementService) {
        this.literaryMovementService = literaryMovementService;
    }

    @GetMapping
    public ResponseEntity<List<LiteraryMovementDTO>> getAll() {
        return ResponseEntity.ok(literaryMovementService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiteraryMovementDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(literaryMovementService.getById(id));
    }

    @PostMapping
    public ResponseEntity<LiteraryMovementDTO> create(@Valid @RequestBody LiteraryMovementCreateRequest request) {
        LiteraryMovementDTO created = literaryMovementService.save(request);
        URI location = URI.create("/literary_movements/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<LiteraryMovementDTO>> createBatch(@Valid @RequestBody List<LiteraryMovementCreateRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(literaryMovementService.saveAll(requests));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        literaryMovementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
