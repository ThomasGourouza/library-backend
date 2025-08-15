package com.tgourouza.library_backend.dto.openLibrary;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookInfo {
    private String originalTitle; // -> title
    private String authorId; // -> openLibraryKey
    private int publicationYear;
    private String coverUrl;
    // private int numberOfPages;
    private String description; // EN-only (best-effort)
    private String language; // remove
    private String type;
    private String category;
    private String audience;
    private String wikipediaLink; // -> build
}
