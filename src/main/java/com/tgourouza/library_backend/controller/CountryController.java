package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.constant.CountryCreateRequest;
import com.tgourouza.library_backend.dto.constant.CountryDTO;
import com.tgourouza.library_backend.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAll() {
        return ResponseEntity.ok(countryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CountryDTO> create(@Valid @RequestBody CountryCreateRequest request) {
        CountryDTO created = countryService.save(request);
        URI location = URI.create("/countries/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CountryDTO>> createBatch(@Valid @RequestBody List<CountryCreateRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(countryService.saveAll(requests));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        countryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
