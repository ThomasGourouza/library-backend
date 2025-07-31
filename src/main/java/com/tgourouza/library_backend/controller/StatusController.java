package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.constant.StatusDTO;
import com.tgourouza.library_backend.service.constant.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity<List<StatusDTO>> getAll() {
        return ResponseEntity.ok(statusService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(statusService.getById(id));
    }

//    @PostMapping
//    public ResponseEntity<StatusDTO> create(@Valid @RequestBody StatusCreateRequest request) {
//        StatusDTO created = statusService.save(request);
//        URI location = URI.create("/statuses/" + created.getId());
//        return ResponseEntity.created(location).body(created);
//    }
//
//    @PostMapping("/batch")
//    public ResponseEntity<List<StatusDTO>> createBatch(@Valid @RequestBody List<StatusCreateRequest> requests) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(statusService.saveAll(requests));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
//        statusService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
}
