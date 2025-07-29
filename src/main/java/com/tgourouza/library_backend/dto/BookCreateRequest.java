package com.tgourouza.library_backend.dto;

import com.tgourouza.library_backend.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class BookCreateRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String originalTitle;

    private String frenchTitle;

    @NotBlank(message = "Author is required")
    @Size(max = 255)
    private UUID authorId;


    private String publicationDate;
    private Integer popularityEurope;
    private Integer popularityRussia;
    private Integer targetAge;
    private LanguageEntity language;
    private LiteraryMovementEntity literaryMovement;
    private LiteraryGenreEntity literaryGenre;
    private CategoryEntity category;
    private String description;
    private String wikipediaLink;


}

