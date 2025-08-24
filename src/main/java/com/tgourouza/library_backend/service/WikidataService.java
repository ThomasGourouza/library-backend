package com.tgourouza.library_backend.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.tgourouza.library_backend.mapper.AuthorWikidataMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import com.tgourouza.library_backend.exception.WikidataUpstreamException;

@Service
public class WikidataService {

    private final RestClient wdqsClient;
    private final AuthorWikidataMapper authorWikidataMapper;

    public WikidataService(
            @Qualifier("wikidataRestClient") RestClient wikidataClient,
            AuthorWikidataMapper authorWikidataMapper
    ) {
        this.wdqsClient = wikidataClient;
        this.authorWikidataMapper = authorWikidataMapper;
    }

    public Optional<AuthorWikidata> getAuthorByQid(String qid) {
        if (qid == null || !qid.matches("Q\\d+")) {
            throw new IllegalArgumentException("Invalid Wikidata Q-ID: " + qid);
        }

        String sparql = buildQuery(qid);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("query", sparql);
        try {
            String json = wdqsClient.post()
                    .uri("/sparql")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(form) // <-- this was missing
                    .retrieve()
                    .body(String.class);

            return authorWikidataMapper.mapToAuthorWikiData(json, qid);
        } catch (RestClientResponseException ex) {
            throw new WikidataUpstreamException("POST", "/sparql", ex.getRawStatusCode());
        } catch (RestClientException ex) {
            throw new WikidataUpstreamException("POST", "/sparql", ex);
        } catch (Exception parse) {
            throw new WikidataUpstreamException("POST", "/sparql", parse);
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
}
