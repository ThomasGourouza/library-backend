package com.tgourouza.library_backend.dto.book;

import com.tgourouza.library_backend.constant.*;
import com.tgourouza.library_backend.dto.Multilingual;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BookWithoutAuthorDTO {
    private UUID id;
    private String originalTitle;
    private Multilingual translatedTitle;
    private LocalDate publicationDate;
    private Integer popularityEurope;
    private Integer popularityRussia;
    private Integer targetAge;
    private Language language;
    private LiteraryMovement literaryMovement;
    private LiteraryGenre literaryGenre;
    private Category category;
    private Multilingual description;
    private String wikipediaLink;
    private Status status;
    private Boolean favorite;
    private String personalNotes;
}