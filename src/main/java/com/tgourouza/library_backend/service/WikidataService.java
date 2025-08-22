package com.tgourouza.library_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.*;

@Service
public class WikidataService {

    private final RestClient wdqsClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public WikidataService(@Qualifier("wikidataRestClient") RestClient wikidataClient) {
        this.wdqsClient = wikidataClient;
    }

    public Optional<AuthorWikidata> getAuthorByQid(String qid) {
        if (qid == null || !qid.matches("Q\\d+")) {
            throw new IllegalArgumentException("Invalid Wikidata Q-ID: " + qid);
        }

        String sparql = buildQuery(qid);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("query", sparql);

        String json = wdqsClient.post()
                .uri("/sparql")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form) // <-- this was missing
                .retrieve()
                .body(String.class);

        return parseAuthorInfo(json, qid);
    }

    private Optional<AuthorWikidata> parseAuthorInfo(String json, String qid) {
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode bindings = root.path("results").path("bindings");
            if (!bindings.isArray() || bindings.isEmpty()) {
                return Optional.empty();
            }

            // Aggregators
            String label = null, description = null;
            LocalDate birthDate = null, deathDate = null;
            String birthPlace = null, birthCountry = null, deathPlace = null, deathCountry = null;
            String wikipediaEn = null, wikipediaFr = null, wikipediaEs = null, wikipediaDe = null, wikipediaRu = null, wikipediaIt = null, wikipediaPt = null, wikipediaJa = null;
            Set<String> citizenships = new LinkedHashSet<>();
            Set<String> occupations = new LinkedHashSet<>();
            Set<String> languages = new LinkedHashSet<>();
            Map<String, String> identifiers = new LinkedHashMap<>();
            identifiers.put("VIAF", null);
            identifiers.put("ISNI", null);
            identifiers.put("GND", null);

            for (JsonNode b : bindings) {
                label = firstNonNull(label, val(b, "personLabel"));
                description = firstNonNull(description, val(b, "personDescription"));

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

                identifiers.computeIfPresent("VIAF", (k, v) -> v != null ? v : val(b, "viaf"));
                identifiers.computeIfPresent("ISNI", (k, v) -> v != null ? v : val(b, "isni"));
                identifiers.computeIfPresent("GND", (k, v) -> v != null ? v : val(b, "gnd"));
            }

            // Remove null identifier entries
            identifiers.entrySet().removeIf(e -> e.getValue() == null);

            AuthorWikidata info = new AuthorWikidata(
                    qid,
                    label,
                    description,
                    birthDate,
                    deathDate,
                    birthPlace,
                    birthCountry,
                    deathPlace,
                    deathCountry,
                    List.copyOf(citizenships),
                    List.copyOf(occupations),
                    List.copyOf(languages),
                    Map.copyOf(identifiers),
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

    private static String buildQuery(String qid) {
        return """
                SELECT ?person ?personLabel ?personDescription
                       ?birthDate ?deathDate
                       ?birthPlace ?birthPlaceLabel
                       ?birthCountry ?birthCountryLabel
                       ?deathPlace ?deathPlaceLabel
                       ?deathCountry ?deathCountryLabel
                       ?citizenshipLabel ?occupationLabel ?languageLabel
                       ?viaf ?isni ?gnd
                       ?enwiki ?frwiki ?eswiki ?dewiki ?ruwiki ?itwiki ?ptwiki ?jawiki
                WHERE {
                  VALUES ?person { wd:%s }

                  OPTIONAL { ?person wdt:P569 ?birthDate. }
                  OPTIONAL { ?person wdt:P570 ?deathDate. }

                  OPTIONAL {
                    ?person wdt:P19 ?birthPlace.
                    OPTIONAL { ?birthPlace (wdt:P131*)/wdt:P17 ?birthCountry. }
                  }

                  OPTIONAL {
                    ?person wdt:P20 ?deathPlace.
                    OPTIONAL { ?deathPlace (wdt:P131*)/wdt:P17 ?deathCountry. }
                  }

                  OPTIONAL { ?person wdt:P27 ?citizenship. }
                  OPTIONAL { ?person wdt:P106 ?occupation. }
                  OPTIONAL { ?person wdt:P1412 ?language. }

                  OPTIONAL { ?person wdt:P214 ?viaf. }   # VIAF
                  OPTIONAL { ?person wdt:P213 ?isni. }   # ISNI
                  OPTIONAL { ?person wdt:P227 ?gnd. }    # GND

                  OPTIONAL { ?frwiki schema:about ?person ; schema:isPartOf <https://fr.wikipedia.org/> . }
                  OPTIONAL { ?enwiki schema:about ?person ; schema:isPartOf <https://en.wikipedia.org/> . }
                  OPTIONAL { ?eswiki schema:about ?person ; schema:isPartOf <https://es.wikipedia.org/> . }
                  OPTIONAL { ?dewiki schema:about ?person ; schema:isPartOf <https://de.wikipedia.org/> . }
                  OPTIONAL { ?ruwiki schema:about ?person ; schema:isPartOf <https://ru.wikipedia.org/> . }
                  OPTIONAL { ?itwiki schema:about ?person ; schema:isPartOf <https://it.wikipedia.org/> . }
                  OPTIONAL { ?ptwiki schema:about ?person ; schema:isPartOf <https://pt.wikipedia.org/> . }
                  OPTIONAL { ?jawiki schema:about ?person ; schema:isPartOf <https://ja.wikipedia.org/> . }

                  SERVICE wikibase:label { bd:serviceParam wikibase:language "[AUTO_LANGUAGE],en,fr". }
                }
                """.formatted(qid);
    }

    private static String val(JsonNode b, String name) {
        JsonNode n = b.path(name).path("value");
        return n.isMissingNode() ? null : n.asText(null);
    }

    private static String uri(JsonNode b, String name) {
        JsonNode n = b.path(name);
        if (n.isMissingNode())
            return null;
        if (!Objects.equals(n.path("type").asText(), "uri"))
            return null;
        return n.path("value").asText(null);
    }

    private static <T> T firstNonNull(T current, T candidate) {
        return current != null ? current : candidate;
    }

    private static void addIfPresent(Set<String> set, String v) {
        if (v != null && !v.isBlank())
            set.add(v);
    }

    private static LocalDate parseDate(String literal) {
        if (literal == null || literal.length() < 10)
            return null;
        // SPARQL returns xsd:dateTime or xsd:date, keep only YYYY-MM-DD
        return LocalDate.parse(literal.substring(0, 10));
    }
}
