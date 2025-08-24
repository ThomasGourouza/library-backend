package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.openLibrary.AuthorFullInfo;
import com.tgourouza.library_backend.dto.openLibrary.AuthorInfo;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthorInfoService {

    private final OpenLibraryService openLibraryService;
    private final WikidataService wikidataService;

    public AuthorInfoService(OpenLibraryService openLibraryService, WikidataService wikidataService) {
        this.openLibraryService = openLibraryService;
        this.wikidataService = wikidataService;
    }

    public AuthorFullInfo getAuthorInfo(String authorKey) {
        AuthorInfo info = openLibraryService.getAuthorInfo(authorKey); // may throw
        if (info == null) return null;

        AuthorWikidata wd = null;
        if (StringUtils.hasText(info.getWikidataId())) {
            wd = wikidataService.getAuthorByQid(info.getWikidataId()).orElse(null); // may throw
        }
        return new AuthorFullInfo(info, wd);
    }
}