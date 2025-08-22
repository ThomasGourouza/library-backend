package com.tgourouza.library_backend.dto.wikidata;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record AuthorWikidata(
        String id,
        String label,
        String description,
        LocalDate birthDate,
        LocalDate deathDate,
        String birthPlace,
        String birthCountry,
        String deathPlace,
        String deathCountry,
        List<String> citizenships,
        List<String> occupations,
        List<String> languages,
        Map<String, String> identifiers, // VIAF, ISNI, GND
        String wikipediaEn,
        String wikipediaFr
) {}
