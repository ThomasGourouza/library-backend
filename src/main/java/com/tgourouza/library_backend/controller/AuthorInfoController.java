package com.tgourouza.library_backend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import com.tgourouza.library_backend.mapper.AuthorCreateRequestMapper;
import com.tgourouza.library_backend.service.OpenLibraryService;
import com.tgourouza.library_backend.service.WikidataService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/author-info")
public class AuthorInfoController {

    private final OpenLibraryService openLibraryService;
    private final WikidataService wikidataService;
    private final AuthorCreateRequestMapper authorCreateRequestMapper;

    public AuthorInfoController(
            OpenLibraryService openLibraryService,
            WikidataService wikidataService,
            AuthorCreateRequestMapper authorCreateRequestMapper) {
        this.openLibraryService = openLibraryService;
        this.wikidataService = wikidataService;
        this.authorCreateRequestMapper = authorCreateRequestMapper;
    }

    @GetMapping(value = "/{authorKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorCreateRequest> getAuthorCreateRequest(
            @PathVariable String authorKey) {
        if (authorKey == null || authorKey.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        AuthorOpenLibrary authorOpenLibrary = openLibraryService.getAuthorOpenLibrary(authorKey);
        if (authorOpenLibrary == null) {
            return ResponseEntity.notFound().build();
        }
        AuthorWikidata authorWikidata = null;
        if (StringUtils.hasText(authorOpenLibrary.getWikidataId())) {
            authorWikidata = wikidataService.getAuthorByQid(authorOpenLibrary.getWikidataId()).orElse(null);
        }
        if (authorWikidata == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authorCreateRequestMapper.mapToAuthorCreateRequest(authorOpenLibrary, authorWikidata));
    }
}
