package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgourouza.library_backend.dto.openLibrary.BookInfo;
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
    public ResponseEntity<BookInfo> getBookInfo(
            @RequestParam String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false, defaultValue = "1") int resultNumber) {
        BookInfo info = openLibraryService.getBookInfo(title, author, resultNumber);
        if (info == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(info);
    }

    @GetMapping(value = "/author", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorOpenLibrary> getAuthorOpenLibrary(@RequestParam("author_key") String authorKey) {
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
