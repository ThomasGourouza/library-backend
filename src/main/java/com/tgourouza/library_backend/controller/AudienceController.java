package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.constant.AudienceCreateRequest;
import com.tgourouza.library_backend.dto.constant.AudienceDTO;
import com.tgourouza.library_backend.service.constant.AudienceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/audiences")
public class AudienceController {
    private final AudienceService audienceService;

    public AudienceController(AudienceService audienceService) {
        this.audienceService = audienceService;
    }

    @GetMapping
    public ResponseEntity<List<AudienceDTO>> getAll() {
        return ResponseEntity.ok(audienceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AudienceDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(audienceService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AudienceDTO> create(@Valid @RequestBody AudienceCreateRequest request) {
        AudienceDTO created = audienceService.save(request);
        URI location = URI.create("/audiences/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AudienceDTO>> createBatch(@Valid @RequestBody List<AudienceCreateRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(audienceService.saveAll(requests));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        audienceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
