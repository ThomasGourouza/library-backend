package com.tgourouza.library_backend.dto.openLibrary;

import com.tgourouza.library_backend.controller.OpenLibraryController.AuthorInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookFullInfo {
    private String originalTitle;
    private String coverUrl;
    private AuthorInfo author;
    private int publicationYear;
    private String language;
    private String type;
    private String category;
    private String audience;
    private String description;
    private String wikipediaLink;
}