package com.tgourouza.library_backend.dto.author;

import java.util.List;
import java.util.UUID;

import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.book.BookDTO;

public record AuthorDTO(
        UUID id,
        // TODO: remove
        String oLKey,
        String name,
        String pictureUrl,
        String shortDescription,
        String description,
        TimePlace birth,
        TimePlace death,
        Integer ageAtDeathOrCurrent,
        List<String> citizenships,
        List<String> occupations,
        List<String> languages,
        String wikipediaLink,
        List<BookDTO> books,
        // TODO: remove
        String dataLanguage) {
}
