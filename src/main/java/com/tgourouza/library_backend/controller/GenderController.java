package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.constant.GenderCreateRequest;
import com.tgourouza.library_backend.dto.constant.GenderDTO;
import com.tgourouza.library_backend.service.GenderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/genders")
public class GenderController {
    private final GenderService genderService;

    public GenderController(GenderService genderService) {
        this.genderService = genderService;
    }

    @GetMapping
    public ResponseEntity<List<GenderDTO>> getAll() {
        return ResponseEntity.ok(genderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenderDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(genderService.getById(id));
    }

    @PostMapping
    public ResponseEntity<GenderDTO> create(@Valid @RequestBody GenderCreateRequest request) {
        GenderDTO created = genderService.save(request);
        URI location = URI.create("/genders/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GenderDTO>> createBatch(@Valid @RequestBody List<GenderCreateRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genderService.saveAll(requests));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        genderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
