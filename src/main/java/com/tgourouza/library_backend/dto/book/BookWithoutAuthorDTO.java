package com.tgourouza.library_backend.dto.book;

import com.tgourouza.library_backend.constant.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class BookWithoutAuthorDTO {
    private UUID id;
    private String originalTitle;
    private String frenchTitle;
    private String publicationDate;
    private Integer popularityEurope;
    private Integer popularityRussia;
    private Integer targetAge;
    private Language language;
    private LiteraryMovement literaryMovement;
    private LiteraryGenre literaryGenre;
    private Category category;
    private String description;
    private String wikipediaLink;
    private Status status;
    private Boolean favorite;
    private String personalNotes;
}