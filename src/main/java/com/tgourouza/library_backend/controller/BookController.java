package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.*;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.dto.book.PersonalNotesUpdateRequest;
import com.tgourouza.library_backend.dto.book.StatusUpdateRequest;
import com.tgourouza.library_backend.service.BookService;
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<BookDTO> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody StatusUpdateRequest updateRequest
    ) {
        return ResponseEntity.ok(bookService.updateStatus(id, updateRequest.getStatusId()));
    }

    @PatchMapping("/{id}/favorite")
    public ResponseEntity<BookDTO> updateFavorite(
            @PathVariable UUID id,
            @Valid @RequestBody FavoriteUpdateRequest request
    ) {
        BookDTO dto = bookService.updateFavorite(id, request.getFavorite());
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}/personal-notes")
    public ResponseEntity<BookDTO> updatePersonalNotes(
            @PathVariable UUID id,
            @Valid @RequestBody PersonalNotesUpdateRequest request
    ) {
        BookDTO dto = bookService.updatePersonalNotes(id, request.getPersonalNotes());
        return ResponseEntity.ok(dto);
    }

}
