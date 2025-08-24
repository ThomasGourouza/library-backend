package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.openLibrary.AuthorFullInfo;
import com.tgourouza.library_backend.service.AuthorInfoService;
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
    private final AuthorInfoService authorInfoService;

    public OpenLibraryController(OpenLibraryService openLibraryService, AuthorInfoService authorInfoService) {
        this.openLibraryService = openLibraryService;
        this.authorInfoService = authorInfoService;
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

    @GetMapping(value = "/author-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorFullInfo> getAuthorInfo(@RequestParam("author_key") String authorKey) {
        if (authorKey == null || authorKey.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        AuthorFullInfo full = authorInfoService.getAuthorInfo(authorKey); // may throw 502-mapped exceptions
        if (full == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(full);
    }
}
