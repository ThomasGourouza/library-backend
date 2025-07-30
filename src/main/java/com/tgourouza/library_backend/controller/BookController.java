package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.BookDTO;
import com.tgourouza.library_backend.dto.BookCreateRequest;
import com.tgourouza.library_backend.dto.PopularityUpdateRequest;
import com.tgourouza.library_backend.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookCreateRequest request) {
        BookDTO dto = bookService.createBook(request);
        URI location = URI.create("/books/" + dto.getId());
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody BookCreateRequest request
    ) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    @PatchMapping("/{id}/popularity")
    public ResponseEntity<BookDTO> updatePopularity(
            @PathVariable UUID id,
            @Valid @RequestBody PopularityUpdateRequest updateRequest
    ) {
        return ResponseEntity.ok(bookService.updatePopularity(id, updateRequest.getPopularityEurope()));
    }
}
