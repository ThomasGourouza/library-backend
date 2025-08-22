package com.tgourouza.library_backend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgourouza.library_backend.dto.openLibrary.AuthorInfo;
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
        try {
            BookInfo info = openLibraryService.getBookInfo(title, author, resultNumber);
            if (info == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(info);

        } catch (Exception e) {
            log.error("OpenLibrary book-info failed (title='{}', author='{}')",
                    title, author, e);
            return ResponseEntity.status(502).build();
        }
    }

    @GetMapping(value = "/author-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorInfo> getAuthorInfo(@RequestParam("author_key") String authorKey) {
        try {
            AuthorInfo info = openLibraryService.getAuthorInfo(authorKey);
            if (info == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(info);

        } catch (Exception e) {
            log.error("OpenLibrary author-info failed (author_key='{}')", authorKey, e);
            return ResponseEntity.status(502).build();
        }
    }
}
