package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.AuthorInfo;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import com.tgourouza.library_backend.mapper.AuthorInfoMapper;
import com.tgourouza.library_backend.service.OpenLibraryService;
import com.tgourouza.library_backend.service.WikidataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/openlibrary-wikidata")
public class OpenLibraryWikiDataController {
    private final OpenLibraryService openLibraryService;
    private final WikidataService wikidataService;
    private final AuthorInfoMapper authorInfoMapper;

    public OpenLibraryWikiDataController(
            OpenLibraryService openLibraryService,
            WikidataService wikidataService,
            AuthorInfoMapper authorInfoMapper) {
        this.openLibraryService = openLibraryService;
        this.wikidataService = wikidataService;
        this.authorInfoMapper = authorInfoMapper;
    }

    @GetMapping(value = "/author-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorInfo> getAuthorInfo(@RequestParam("author_key") String authorKey) {
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
        return ResponseEntity.ok(authorInfoMapper.mapToAuthorInfo(authorOpenLibrary, authorWikidata));
    }
}
