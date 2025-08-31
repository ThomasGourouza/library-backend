package com.tgourouza.library_backend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgourouza.library_backend.constant.Language;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.service.OpenLibraryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/openlibrary")
public class OpenLibraryController {

    private final OpenLibraryService openLibraryService;

    public OpenLibraryController(OpenLibraryService openLibraryService) {
        this.openLibraryService = openLibraryService;
    }

    @GetMapping(value = "/book-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookCreateRequest> getBookCreateRequest(
            @RequestParam String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false, defaultValue = "1") int resultNumber,
            @RequestParam(required = false, defaultValue = "ENGLISH") Language language
    ) {
        BookCreateRequest bookCreateRequest = openLibraryService.getBookCreateRequest(
                title, author, resultNumber, language);
        if (bookCreateRequest == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookCreateRequest);
    }

    @GetMapping(value = "/author/{authorKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorOpenLibrary> getAuthorOpenLibrary(@PathVariable String authorKey) {
        if (authorKey == null || authorKey.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        AuthorOpenLibrary authorOpenLibrary = openLibraryService.getAuthorOpenLibrary(authorKey); // may throw 502-mapped exceptions
        if (authorOpenLibrary == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authorOpenLibrary);
    }
}
