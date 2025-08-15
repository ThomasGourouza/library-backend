package com.tgourouza.library_backend.dto.openLibrary;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookInfo {
    private Text originalTitle;
    private String authorId; // -> openLibraryKey
    private int publicationYear;
    private String coverUrl;
    // private int numberOfPages;
    private Text description;
    private Set<String> tags;
    private String wikipediaLink;
}
