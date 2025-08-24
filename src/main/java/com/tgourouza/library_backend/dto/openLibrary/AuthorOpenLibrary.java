package com.tgourouza.library_backend.dto.openLibrary;

import com.tgourouza.library_backend.dto.author.AuthorDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorOpenLibrary {
    private String oLKey;
    private String wikidataId;
    private String name;
    private String pictureUrl;
    private String country;
    private AuthorDate date;
    private Text description;
    // TODO: Remove
    private String wikipediaLink;
}
