package com.tgourouza.library_backend.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.service.CityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Multilingual> create(@Valid @RequestBody Multilingual request) {
        Multilingual created = service.create(request);
        return ResponseEntity
                .created(URI.create("/cities/"))
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<Multilingual>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
