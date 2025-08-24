package com.tgourouza.library_backend.dto.wikidata;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record AuthorWikidata(
        String id,
        String label,
        String shortDescription,
        LocalDate birthDate,
        LocalDate deathDate,
        String birthPlace,
        String birthCountry,
        String deathPlace,
        String deathCountry,
        List<String> citizenships,
        List<String> occupations,
        List<String> languages,
        String wikipediaEn,
        String wikipediaFr,
        String wikipediaEs,
        String wikipediaDe,
        String wikipediaRu,
        String wikipediaIt,
        String wikipediaPt,
        String wikipediaJa
) {}
