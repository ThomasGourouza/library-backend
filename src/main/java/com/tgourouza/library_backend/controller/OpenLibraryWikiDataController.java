package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.openLibrary.AuthorInfo;
import com.tgourouza.library_backend.service.OpenLibraryWikiDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/openlibrary-wikidata")
public class OpenLibraryWikiDataController {

    private final OpenLibraryWikiDataService openLibraryWikiDataService;

    public OpenLibraryWikiDataController(OpenLibraryWikiDataService openLibraryWikiDataService) {
        this.openLibraryWikiDataService = openLibraryWikiDataService;
    }

    @GetMapping(value = "/author-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorInfo> getAuthorInfo(@RequestParam("author_key") String authorKey) {
        if (authorKey == null || authorKey.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        AuthorInfo full = openLibraryWikiDataService.getAuthorInfo(authorKey);
        if (full == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(full);
    }
}
