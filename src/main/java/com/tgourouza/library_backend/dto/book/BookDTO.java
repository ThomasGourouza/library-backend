package com.tgourouza.library_backend.dto.book;

import com.tgourouza.library_backend.constant.*;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BookDTO {
    private UUID id;
    private String originalTitle;
    private Multilingual title;
    private AuthorDTO author;
    private Integer authorAgeAtPublication;
    private LocalDate publicationDate;
    private Language language;
    private Subject subject;
    private Audience audience;
    private Multilingual description;
    private String wikipediaLink;
    // Editable fields
    private Status status;
    private Boolean favorite;
    private String personalNotes;
}
