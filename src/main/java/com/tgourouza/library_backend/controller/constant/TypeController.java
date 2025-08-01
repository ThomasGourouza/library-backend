package com.tgourouza.library_backend.controller.constant;

import com.tgourouza.library_backend.dto.constant.TypeCreateRequest;
import com.tgourouza.library_backend.dto.constant.TypeDTO;
import com.tgourouza.library_backend.service.constant.TypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/literary_genres")
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<List<TypeDTO>> getAll() {
        return ResponseEntity.ok(typeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(typeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TypeDTO> create(@Valid @RequestBody TypeCreateRequest request) {
        TypeDTO created = typeService.save(request);
        URI location = URI.create("/literary_genres/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TypeDTO>> createBatch(@Valid @RequestBody List<TypeCreateRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(typeService.saveAll(requests));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        typeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
