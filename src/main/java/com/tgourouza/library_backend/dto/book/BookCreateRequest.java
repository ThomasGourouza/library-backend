package com.tgourouza.library_backend.dto.book;

import com.tgourouza.library_backend.dto.Multilingual;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BookCreateRequest {
    @NotBlank(message = "Original title is required")
    @Size(max = 255)
    private String originalTitle;

    @NotNull
    private UUID authorId;

    private LocalDate publicationDate;
    private Multilingual title;
    private Multilingual description;
    private String wikipediaLink;
    private String personalNotes;
    // Fields with default values
    private String language;
    private String type;
    private String category;
    private String audience;
    private String status;
    private Boolean favorite;
}
