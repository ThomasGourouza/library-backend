package com.tgourouza.library_backend.dto.openLibrary;

import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorInfo {

    private AuthorOpenLibrary authorOpenLibrary;
    private AuthorWikidata authorWikidata;
}
