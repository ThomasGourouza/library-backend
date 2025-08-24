package com.tgourouza.library_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tgourouza.library_backend.dto.openLibrary.AuthorInfo;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;

@Service
public class OpenLibraryWikiDataService {

    private final OpenLibraryService openLibraryService;
    private final WikidataService wikidataService;

    public OpenLibraryWikiDataService(OpenLibraryService openLibraryService, WikidataService wikidataService) {
        this.openLibraryService = openLibraryService;
        this.wikidataService = wikidataService;
    }

    public AuthorInfo getAuthorInfo(String authorKey) {
        AuthorOpenLibrary authorOpenLibrary = openLibraryService.getAuthorOpenLibrary(authorKey); // may throw
        if (authorOpenLibrary == null) {
            return null;
        }

        AuthorWikidata authorWikidata = null;
        if (StringUtils.hasText(authorOpenLibrary.getWikidataId())) {
            authorWikidata = wikidataService.getAuthorByQid(authorOpenLibrary.getWikidataId()).orElse(null); // may throw
        }
        return new AuthorInfo(authorOpenLibrary, authorWikidata);
    }
}
