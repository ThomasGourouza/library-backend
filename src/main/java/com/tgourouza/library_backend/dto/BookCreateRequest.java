package com.tgourouza.library_backend.dto;

import com.tgourouza.library_backend.entity.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String originalTitle;

    private String frenchTitle;

    @NotNull
    @Size(max = 255)
    private UUID authorId;

    private String publicationDate;

    @Min(0)
    @Max(100)
    private Integer popularityEurope;

    @Min(0)
    @Max(100)
    private Integer popularityRussia;

    private Integer targetAge;

    private UUID languageId;
    private UUID literaryMovementId;
    private UUID literaryGenreId;
    private UUID categoryId;

    private String description;
    private String wikipediaLink;
}

