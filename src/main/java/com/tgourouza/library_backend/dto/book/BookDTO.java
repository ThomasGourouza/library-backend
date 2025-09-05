package com.tgourouza.library_backend.dto.book;

import java.util.List;
import java.util.UUID;

import com.tgourouza.library_backend.dto.author.AuthorDTO;

public record BookDTO(
        UUID id,
        String originalTitle,
        String originalTitleLanguage,
        String title,
        AuthorDTO author,
        Integer authorAgeAtPublication,
        Integer publicationYear,
        String coverUrl,
        Integer numberOfPages,
        String description,
        List<String> bookTags,
        String wikipediaLink,

        // Editable fields
        String personalNotes,
        String status,
        Boolean favorite) {
}
