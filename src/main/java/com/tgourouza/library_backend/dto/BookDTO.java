package com.tgourouza.library_backend.dto;

import com.tgourouza.library_backend.entity.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class BookDTO {

    private UUID id;
    private String originalTitle;
    private AuthorEntity author;
    private String frenchTitle;
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
    private StatusEntity status;
}
