package com.tgourouza.library_backend.dto.book;

import com.tgourouza.library_backend.dto.Multilingual;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BookCreateRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String originalTitle;

    private Multilingual title;

    @NotNull
    @Size(max = 255)
    private UUID authorId;

    private LocalDate publicationDate;

    @NotNull
    private Long languageId;

    @NotNull
    private Long typeId;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long AudienceId;

    private Multilingual description;
    private String wikipediaLink;

    @NotNull
    private Long statusId;

    private Boolean favorite;
    private String personalNotes;
}

