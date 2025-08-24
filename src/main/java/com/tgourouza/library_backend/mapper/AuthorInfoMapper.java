package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.AuthorInfo;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import org.springframework.stereotype.Component;

@Component
public class AuthorInfoMapper {
    public AuthorInfo mapToAuthorInfo(AuthorOpenLibrary AuthorOpenLibrary, AuthorWikidata authorWikidata) {
        return new AuthorInfo();
    }
}
