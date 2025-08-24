package com.tgourouza.library_backend.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class AuthorWikidataMapper {
    private final ObjectMapper mapper = new ObjectMapper();

    public Optional<AuthorWikidata> mapToAuthorWikiData(String json, String qid) {
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode bindings = root.path("results").path("bindings");
            if (!bindings.isArray() || bindings.isEmpty()) {
                return Optional.empty();
            }

            // Aggregators
            String label = null, shortDescription = null;
            LocalDate birthDate = null, deathDate = null;
            String birthPlace = null, birthCountry = null, deathPlace = null, deathCountry = null;
            String wikipediaEn = null, wikipediaFr = null, wikipediaEs = null, wikipediaDe = null, wikipediaRu = null, wikipediaIt = null, wikipediaPt = null, wikipediaJa = null;
            Set<String> citizenships = new LinkedHashSet<>();
            Set<String> occupations = new LinkedHashSet<>();
            Set<String> languages = new LinkedHashSet<>();

            for (JsonNode b : bindings) {
                label = firstNonNull(label, val(b, "personLabel"));
                shortDescription = firstNonNull(shortDescription, val(b, "personDescription"));

                birthDate = firstNonNull(birthDate, parseDate(val(b, "birthDate")));
                deathDate = firstNonNull(deathDate, parseDate(val(b, "deathDate")));

                birthPlace = firstNonNull(birthPlace, val(b, "birthPlaceLabel"));
                birthCountry = firstNonNull(birthCountry, val(b, "birthCountryLabel"));
                deathPlace = firstNonNull(deathPlace, val(b, "deathPlaceLabel"));
                deathCountry = firstNonNull(deathCountry, val(b, "deathCountryLabel"));

                addIfPresent(citizenships, val(b, "citizenshipLabel"));
                addIfPresent(occupations, val(b, "occupationLabel"));
                addIfPresent(languages, val(b, "languageLabel"));

                wikipediaEn = firstNonNull(wikipediaEn, uri(b, "enwiki"));
                wikipediaFr = firstNonNull(wikipediaFr, uri(b, "frwiki"));
                wikipediaEs = firstNonNull(wikipediaEs, uri(b, "eswiki"));
                wikipediaDe = firstNonNull(wikipediaDe, uri(b, "dewiki"));
                wikipediaRu = firstNonNull(wikipediaRu, uri(b, "ruwiki"));
                wikipediaIt = firstNonNull(wikipediaIt, uri(b, "itwiki"));
                wikipediaPt = firstNonNull(wikipediaPt, uri(b, "ptwiki"));
                wikipediaJa = firstNonNull(wikipediaJa, uri(b, "jawiki"));
            }

            AuthorWikidata info = new AuthorWikidata(
                    qid,
                    label,
                    shortDescription,
                    birthDate,
                    deathDate,
                    birthPlace,
                    birthCountry,
                    deathPlace,
                    deathCountry,
                    List.copyOf(citizenships),
                    List.copyOf(occupations),
                    List.copyOf(languages),
                    wikipediaEn,
                    wikipediaFr,
                    wikipediaEs,
                    wikipediaDe,
                    wikipediaRu,
                    wikipediaIt,
                    wikipediaPt,
                    wikipediaJa
            );

            return Optional.of(info);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Wikidata response", e);
        }
    }

    private static String val(JsonNode b, String name) {
        JsonNode n = b.path(name).path("value");
        return n.isMissingNode() ? null : n.asText(null);
    }

    private static String uri(JsonNode b, String name) {
        JsonNode n = b.path(name);
        if (n.isMissingNode()) {
            return null;
        }
        if (!Objects.equals(n.path("type").asText(), "uri")) {
            return null;
        }
        return n.path("value").asText(null);
    }

    private static <T> T firstNonNull(T current, T candidate) {
        return current != null ? current : candidate;
    }

    private static void addIfPresent(Set<String> set, String v) {
        if (v != null && !v.isBlank()) {
            set.add(v);
        }
    }

    private static LocalDate parseDate(String literal) {
        if (literal == null || literal.length() < 10) {
            return null;
        }
        // SPARQL returns xsd:dateTime or xsd:date, keep only YYYY-MM-DD
        return LocalDate.parse(literal.substring(0, 10));
    }
}
