package com.tgourouza.library_backend.dto.openLibrary;

import com.tgourouza.library_backend.dto.author.AuthorDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorInfo {
    private String oLKey;
    private String name;
    private String pictureUrl;
    private String country;
    private AuthorDate date;
    private String description;
    private String wikipediaLink;
}
