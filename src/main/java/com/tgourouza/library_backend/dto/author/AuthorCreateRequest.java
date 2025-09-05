package com.tgourouza.library_backend.dto.author;

import java.util.List;

import com.tgourouza.library_backend.dto.TimePlace;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorCreateRequest(
        // Open Library fields
        String oLKey,
        @NotBlank(message = "Name is required") @Size(max = 255)
        String name,
        String pictureUrl,
        String description,

        // Wikidata fields
        String shortDescription,
        TimePlace birth,
        TimePlace death,

        List<String> citizenships,
        List<String> occupations,
        List<String> languages,

        String wikipediaLinkFrench,
        String wikipediaLinkSpanish,
        String wikipediaLinkItalian,
        String wikipediaLinkPortuguese,
        String wikipediaLinkEnglish,
        String wikipediaLinkGerman,
        String wikipediaLinkRussian,
        String wikipediaLinkJapanese
) {

}
