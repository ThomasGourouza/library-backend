package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.constant.LiteraryGenreCreateRequest;
import com.tgourouza.library_backend.dto.constant.LiteraryGenreDTO;
import com.tgourouza.library_backend.service.LiteraryGenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/literary_genres")
public class LiteraryGenreController {
    private final LiteraryGenreService literaryGenreService;

    public LiteraryGenreController(LiteraryGenreService literaryGenreService) {
        this.literaryGenreService = literaryGenreService;
    }

    @GetMapping
    public ResponseEntity<List<LiteraryGenreDTO>> getAll() {
        return ResponseEntity.ok(literaryGenreService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiteraryGenreDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(literaryGenreService.getById(id));
    }

    @PostMapping
    public ResponseEntity<LiteraryGenreDTO> create(@Valid @RequestBody LiteraryGenreCreateRequest request) {
        LiteraryGenreDTO created = literaryGenreService.save(request);
        URI location = URI.create("/literary_genres/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<LiteraryGenreDTO>> createBatch(@Valid @RequestBody List<LiteraryGenreCreateRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(literaryGenreService.saveAll(requests));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        literaryGenreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
