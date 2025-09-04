package com.tgourouza.library_backend.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.dto.book.FavoriteUpdateRequest;
import com.tgourouza.library_backend.dto.book.PersonalNotesUpdateRequest;
import com.tgourouza.library_backend.dto.book.StatusUpdateRequest;
import com.tgourouza.library_backend.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooks(@RequestParam(name = "language", required = false, defaultValue = "ENGLISH") DataLanguage dataLanguage) {
        return ResponseEntity.ok(bookService.getAllBooks(dataLanguage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable UUID id,
            @RequestParam(name = "language", required = false, defaultValue = "ENGLISH") DataLanguage dataLanguage) {
        return ResponseEntity.ok(bookService.getBookById(id, dataLanguage));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookCreateRequest request,
            @RequestParam(name = "language", required = false, defaultValue = "ENGLISH") DataLanguage dataLanguage) {
        BookDTO dto = bookService.createBook(request, dataLanguage);
        URI location = URI.create("/books/" + dto.id());
        return ResponseEntity.created(location).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // TODO
    @PatchMapping("/{id}/status")
    public ResponseEntity<BookDTO> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody StatusUpdateRequest updateRequest,
            @RequestParam(name = "language", required = false, defaultValue = "ENGLISH") DataLanguage dataLanguage) {
        return ResponseEntity.ok(bookService.updateStatus(id, updateRequest.getStatus(), dataLanguage));
    }

    @PatchMapping("/{id}/favorite")
    public ResponseEntity<BookDTO> updateFavorite(
            @PathVariable UUID id,
            @Valid @RequestBody FavoriteUpdateRequest request,
            @RequestParam(name = "language", required = false, defaultValue = "ENGLISH") DataLanguage dataLanguage) {
        BookDTO dto = bookService.updateFavorite(id, request.getFavorite(), dataLanguage);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}/personal_notes")
    public ResponseEntity<BookDTO> updatePersonalNotes(
            @PathVariable UUID id,
            @Valid @RequestBody PersonalNotesUpdateRequest request,
            @RequestParam(name = "language", required = false, defaultValue = "ENGLISH") DataLanguage dataLanguage) {
        BookDTO dto = bookService.updatePersonalNotes(id, request.getPersonalNotes(), dataLanguage);
        return ResponseEntity.ok(dto);
    }
}
