package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.constant.CategoryCreateRequest;
import com.tgourouza.library_backend.dto.constant.CategoryDTO;
import com.tgourouza.library_backend.service.constant.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryCreateRequest request) {
        CategoryDTO created = categoryService.save(request);
        URI location = URI.create("/categories/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CategoryDTO>> createBatch(@Valid @RequestBody List<CategoryCreateRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveAll(requests));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
