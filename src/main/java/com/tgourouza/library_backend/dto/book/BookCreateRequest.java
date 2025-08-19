package com.tgourouza.library_backend.dto.book;

import java.time.LocalDate;
import java.util.UUID;

import com.tgourouza.library_backend.dto.Multilingual;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookCreateRequest {
    @NotBlank(message = "Original title is required")
    @Size(max = 255)
    private String originalTitle;
    @NotNull
    private UUID authorId;
    private Multilingual title;
    private LocalDate publicationDate; // publicationYear
    // private String coverUrl;
    // private int numberOfPages;
    private Multilingual description;
    private String language;
    private String genre; // default value
    private String subject; // default value
    private String audience; // default value
    private String wikipediaLink;

    private String personalNotes; // can be empty
    private String status; // can be empty & default value
    private Boolean favorite; // can be empty & default value
}
