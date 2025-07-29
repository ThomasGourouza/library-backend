package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.BookDTO;
import com.tgourouza.library_backend.dto.BookCreateRequest;
import com.tgourouza.library_backend.dto.PopularityUpdateRequest;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Map;
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
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable UUID id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
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
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build(); // HTTP 204
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();  // HTTP 404
        }
    }

    @PatchMapping("/{id}/popularity")
    public ResponseEntity<BookDTO> updatePopularity(
            @PathVariable UUID id,
            @Valid @RequestBody PopularityUpdateRequest updateRequest
    ) {
        BookDTO updated = bookService.updatePopularity(id, updateRequest.getPopularityEurope());
        return ResponseEntity.ok(updated);
    }

}
