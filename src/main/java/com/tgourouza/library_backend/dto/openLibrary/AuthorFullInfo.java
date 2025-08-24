package com.tgourouza.library_backend.dto.openLibrary;

import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorFullInfo {
    private AuthorInfo authorInfo;
    private AuthorWikidata authorWikidata;
}
