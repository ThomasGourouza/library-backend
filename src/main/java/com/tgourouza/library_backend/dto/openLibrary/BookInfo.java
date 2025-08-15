package com.tgourouza.library_backend.dto.openLibrary;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookInfo {
    private String originalTitle; // -> title
    private String coverUrl;
    private String authorId; // -> openLibraryKey
    private int publicationYear;
    private String language; // remove
    private String type;
    private String category;
    private String audience;
    private String description; // EN-only (best-effort)
    private String wikipediaLink; // -> build
}
