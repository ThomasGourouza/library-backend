package com.tgourouza.library_backend.dto.book;

import java.util.List;
import java.util.UUID;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.author.AuthorDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDTO {

    private UUID id;
    private String originalTitle;
    private String originalTitleLanguage; // title language
    // TODO: String -> only from dataLanguage
    private Multilingual title;
    private AuthorDTO author;
    private String authorOLKey;
    private Integer authorAgeAtPublication;
    private Integer publicationYear;
    private String coverUrl;
    private Integer numberOfPages;
    // TODO: String -> only from dataLanguage
    private Multilingual description;
    private List<String> bookTags;
    private String wikipediaLink;

    // Editable fields
    private String personalNotes;
    private String status;
    private Boolean favorite;
}
