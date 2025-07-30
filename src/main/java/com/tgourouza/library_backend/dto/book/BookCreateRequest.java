package com.tgourouza.library_backend.dto.book;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BookCreateRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String originalTitle;

    private String frenchTitle;
    private String englishTitle;

    @NotNull
    @Size(max = 255)
    private UUID authorId;

    private LocalDate publicationDate;

    @Min(0)
    @Max(100)
    private Integer popularityEurope;

    @Min(0)
    @Max(100)
    private Integer popularityRussia;

    private Integer targetAge;

    @NotNull
    private Long languageId;
    @NotNull
    private Long literaryMovementId;
    @NotNull
    private Long literaryGenreId;
    @NotNull
    private Long categoryId;
    @NotNull
    private Long statusId;

    private String frenchDescription;
    private String englishDescription;
    private String wikipediaLink;
    private Boolean favorite;
    private String personalNotes;
}

