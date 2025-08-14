package com.tgourouza.library_backend.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/wikidata")
public class WikidataController {

    private final RestClient rest;
    private final ObjectMapper mapper = new ObjectMapper();

    public WikidataController(RestClient wikidataRestClient) {
        this.rest = Objects.requireNonNull(wikidataRestClient, "wikidataRestClient must not be null");
    }

    /* ============================ BOOK INFO ============================ */
    @GetMapping("/book-info")
    public ResponseEntity<BookInfo> getBookInfo(
            @RequestParam String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false, defaultValue = "en") String languageHint) {
        try {
            final String json = (author != null && !author.isBlank())
                    ? postSparql(buildSparqlLikeWithAuthor(title, author, languageHint))
                    : postSparql(buildSparqlLike(title, languageHint));

            JsonNode root = mapper.readTree(json);
            JsonNode bindings = root.path("results").path("bindings");
            if (!bindings.isArray() || bindings.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            BookInfo info = mapBindingToBookInfo(bindings.get(0));
            return ResponseEntity.ok(info);

        } catch (HttpServerErrorException e) {
            log.error("Wikidata upstream error [book-info] (title='{}', author='{}', languageHint='{}') {} {}",
                    title, author, languageHint, e.getStatusCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        } catch (Exception e) {
            log.error("Wikidata lookup failed [book-info] (title='{}', author='{}', languageHint='{}')",
                    title, author, languageHint, e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }

    /* =========================== AUTHOR INFO =========================== */
    @GetMapping("/author-info")
    public ResponseEntity<AuthorInfo> getAuthorInfo(@RequestParam String wikidataQid) {
        try {
            String json = postSparql(buildAuthorByWikidataQid(wikidataQid));
            JsonNode root = mapper.readTree(json);
            JsonNode bindings = root.path("results").path("bindings");
            if (!bindings.isArray() || bindings.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            AuthorInfo info = mapBindingToAuthorInfo(bindings.get(0));
            return ResponseEntity.ok(info);

        } catch (HttpServerErrorException e) {
            log.error("Wikidata upstream error [author-info] (wikidataQid='{}') {} {}",
                    wikidataQid, e.getStatusCode(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        } catch (Exception e) {
            log.error("Wikidata lookup failed [author-info] (wikidataQid='{}')", wikidataQid, e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }

    /* ============================ HTTP helper ============================ */
    private String postSparql(String sparql) {
        ResponseEntity<String> resp = rest.post()
                .uri("/sparql")
                .contentType(MediaType.parseMediaType("application/sparql-query; charset=UTF-8"))
                .accept(MediaType.parseMediaType("application/sparql-results+json"))
                .body(sparql)
                .retrieve()
                .toEntity(String.class);

        if (resp.getStatusCode().isError()) {
            log.error("WDQS error {} Body: {}", resp.getStatusCode(), resp.getBody());
            throw new HttpServerErrorException(resp.getStatusCode(), "WDQS error");
        }
        return resp.getBody();
    }

    /* ============================== Mapping ============================== */
    private BookInfo mapBindingToBookInfo(JsonNode b) {
        Function<String, String> gs = k -> getStr(b, k);
        Function<String, Integer> gi = k -> getInt(b, k);

        // Titles FR/EN
        String titleFr = gs.apply("workLabelFr");
        String titleEn = gs.apply("workLabelEn");

        // Original language + original title (true original language if available)
        String origLangLabel = gs.apply("origLangLabel");
        String origLangCode = gs.apply("origLangCode");
        String language = firstNonEmpty(origLangLabel, origLangCode); // raw
        String originalTitle = firstNonEmpty(
                gs.apply("workLabelOrig"),
                firstNonEmpty(gs.apply("workLabelAny"),
                        firstNonEmpty(titleFr, titleEn)));

        // EN-only description
        String workDescEn = gs.apply("workDescEn");

        // Cover
        String coverUrl = gs.apply("workImageFile").isEmpty() ? null : commonsFilePathUrl(gs.apply("workImageFile"));

        // Author Q-id (if any)
        String authorWikidataQid = getQidFromIri(b, "author");

        // Raw strings (multi-values allowed for type/category)
        String type = mergeCsv(gs.apply("typesFromClassCSV"), gs.apply("genresCSV"));
        String category = gs.apply("subjectsCSV");
        String audience = gs.apply("audiencesCSV");

        return new BookInfo(
                originalTitle,
                new BookInfo.Title(titleFr, titleEn),
                coverUrl,
                authorWikidataQid,
                gi.apply("pubYear") != null ? gi.apply("pubYear") : 0,
                language,
                type,
                category,
                audience,
                new BookInfo.Description(workDescEn),
                gs.apply("workWiki"));
    }

    private AuthorInfo mapBindingToAuthorInfo(JsonNode b) {
        Function<String, String> gs = k -> getStr(b, k);
        Function<String, LocalDate> gd = k -> getDate(b, k);

        String picture = gs.apply("authorImageFile").isEmpty() ? null : commonsFilePathUrl(gs.apply("authorImageFile"));

        return new AuthorInfo(
                gs.apply("authorLabel"),
                picture,
                gs.apply("authorCountryLabel"), // raw
                new AuthorInfo.Date(gd.apply("authorBirth"), gd.apply("authorDeath")),
                gs.apply("authorDescEn"), // EN-only
                gs.apply("authorWiki"));
    }

    /* ============================ SPARQL (LIKE) =========================== */
    // Book: title-only fuzzy (FR/EN titles, EN-only desc)
    private String buildSparqlLike(String title, String languageHint) {
        String qTitle = escapeForMwApi(title);
        String qTitleStr = escapeForStringLiteral(title);

        return ("""
                PREFIX hint: <http://blazegraph.com/queryHints#>
                SELECT
                  ?work ?workWiki ?workImageFile ?pubYear ?origLangCode ?origLangLabel
                  ?author
                  ?workLabelFr ?workLabelEn
                  ?workDescEn
                  ?workLabelAny
                  ?workLabelOrig
                  ?typesFromClassCSV ?genresCSV ?subjectsCSV ?audiencesCSV
                WHERE {
                  hint:Query hint:timeout "15000" .

                  SERVICE wikibase:mwapi {
                    bd:serviceParam wikibase:endpoint "www.wikidata.org";
                                    wikibase:api "EntitySearch";
                                    mwapi:search "%s";
                                    mwapi:language "%s";
                                    mwapi:limit "15".
                    ?work wikibase:apiOutputItem mwapi:item.
                  }
                  ?work wdt:P31/wdt:P279* ?class .
                  VALUES ?class { wd:Q7725634 wd:Q8261 wd:Q25379 wd:Q482 wd:Q35760 wd:Q36279 }.

                  BIND(LCASE("%s") AS ?qTitle)
                  FILTER (
                    EXISTS { ?work rdfs:label ?wl . FILTER(CONTAINS(LCASE(STR(?wl)), ?qTitle)) } ||
                    EXISTS { ?work skos:altLabel ?wa . FILTER(CONTAINS(LCASE(STR(?wa)), ?qTitle)) }
                  )

                  OPTIONAL { ?work wdt:P577 ?pubDate . BIND(YEAR(?pubDate) AS ?pubYear) }
                  OPTIONAL { ?work wdt:P18  ?workImage . BIND(STRAFTER(STR(?workImage), "Special:FilePath/") AS ?workImageFile) }
                  OPTIONAL {
                    ?work wdt:P364 ?origLang .
                    OPTIONAL { ?origLang wdt:P218 ?origLangCode } .
                    SERVICE wikibase:label { bd:serviceParam wikibase:language "en". ?origLang rdfs:label ?origLangLabel }
                  }

                  OPTIONAL { ?work wdt:P50 ?author. }  # we only need the Q-id

                  OPTIONAL {
                    ?workWiki schema:about ?work ;
                              schema:inLanguage "en" ;
                              schema:isPartOf [ wikibase:wikiGroup "wikipedia" ] .
                  }

                  OPTIONAL { ?work rdfs:label ?workLabelFr FILTER(LANG(?workLabelFr)="fr") }
                  OPTIONAL { ?work rdfs:label ?workLabelEn FILTER(LANG(?workLabelEn)="en") }

                  OPTIONAL { ?work schema:description ?workDescEn FILTER(LANG(?workDescEn)="en") }

                  OPTIONAL {
                    ?work rdfs:label ?workLabelOrig .
                    FILTER(BOUND(?origLangCode) && LANG(?workLabelOrig) = ?origLangCode)
                  }

                  OPTIONAL {
                    SELECT ?work (GROUP_CONCAT(DISTINCT ?classLabel; separator=",") AS ?typesFromClassCSV)
                    WHERE {
                      ?work wdt:P31/wdt:P279* ?c .
                      VALUES ?c { wd:Q7725634 wd:Q8261 wd:Q25379 wd:Q482 wd:Q35760 wd:Q36279 }.
                      SERVICE wikibase:label { bd:serviceParam wikibase:language "en". ?c rdfs:label ?classLabel }
                    } GROUP BY ?work
                  }
                  OPTIONAL {
                    SELECT ?work (GROUP_CONCAT(DISTINCT ?genreLabel; separator=",") AS ?genresCSV)
                    WHERE {
                      ?work wdt:P136 ?genre .
                      SERVICE wikibase:label { bd:serviceParam wikibase:language "en". ?genre rdfs:label ?genreLabel }
                    } GROUP BY ?work
                  }
                  OPTIONAL {
                    SELECT ?work (GROUP_CONCAT(DISTINCT ?subjectLabel; separator=",") AS ?subjectsCSV)
                    WHERE {
                      ?work wdt:P921 ?subject .
                      SERVICE wikibase:label { bd:serviceParam wikibase:language "en". ?subject rdfs:label ?subjectLabel }
                    } GROUP BY ?work
                  }
                  OPTIONAL {
                    SELECT ?work (GROUP_CONCAT(DISTINCT ?audLabel; separator=",") AS ?audiencesCSV)
                    WHERE {
                      ?work wdt:P2360 ?aud .
                      SERVICE wikibase:label { bd:serviceParam wikibase:language "en". ?aud rdfs:label ?audLabel }
                    } GROUP BY ?work
                  }

                  SERVICE wikibase:label {
                    bd:serviceParam wikibase:language "fr,en".
                    ?work rdfs:label ?workLabelAny .
                  }
                }
                LIMIT 1
                """)
                .formatted(qTitle, languageHint, qTitleStr);
    }

    // Book: title + author fuzzy
    private String buildSparqlLikeWithAuthor(String title, String author, String languageHint) {
        String qTitle = escapeForMwApi(title);
        String qAuthor = escapeForMwApi(author);
        String qTitleStr = escapeForStringLiteral(title);
        String qAuthorStr = escapeForStringLiteral(author);

        return ("""
                PREFIX hint: <http://blazegraph.com/queryHints#>
                SELECT
                  ?work ?workWiki ?workImageFile ?pubYear ?origLangCode ?origLangLabel
                  ?author
                  ?workLabelFr ?workLabelEn
                  ?workDescEn
                  ?workLabelAny
                  ?workLabelOrig
                  ?typesFromClassCSV ?genresCSV ?subjectsCSV ?audiencesCSV
                WHERE {
                  hint:Query hint:timeout "15000" .

                  SERVICE wikibase:mwapi {
                    bd:serviceParam wikibase:endpoint "www.wikidata.org";
                                    wikibase:api "EntitySearch";
                                    mwapi:search "%s";
                                    mwapi:language "%s";
                                    mwapi:limit "20".
                    ?work wikibase:apiOutputItem mwapi:item.
                  }
                  ?work wdt:P31/wdt:P279* ?class .
                  VALUES ?class { wd:Q7725634 wd:Q8261 wd:Q25379 wd:Q482 wd:Q35760 wd:Q36279 }.

                  SERVICE wikibase:mwapi {
                    bd:serviceParam wikibase:endpoint "www.wikidata.org";
                                    wikibase:api "EntitySearch";
                                    mwapi:search "%s";
                                    mwapi:language "%s";
                                    mwapi:limit "20".
                    ?authorHit wikibase:apiOutputItem mwapi:item.
                  }

                  ?work wdt:P50 ?author .
                  FILTER (?author = ?authorHit)

                  BIND(LCASE("%s") AS ?qTitle)
                  BIND(LCASE("%s") AS ?qAuthor)

                  FILTER (
                    EXISTS { ?work rdfs:label ?wl . FILTER(CONTAINS(LCASE(STR(?wl)), ?qTitle)) } ||
                    EXISTS { ?work skos:altLabel ?wa . FILTER(CONTAINS(LCASE(STR(?wa)), ?qTitle)) }
                  )
                  FILTER (
                    EXISTS { ?author rdfs:label ?al . FILTER(CONTAINS(LCASE(STR(?al)), ?qAuthor)) } ||
                    EXISTS { ?author skos:altLabel ?aa . FILTER(CONTAINS(LCASE(STR(?aa)), ?qAuthor)) }
                  )

                  OPTIONAL { ?work wdt:P577 ?pubDate . BIND(YEAR(?pubDate) AS ?pubYear) }
                  OPTIONAL { ?work wdt:P18  ?workImage . BIND(STRAFTER(STR(?workImage), "Special:FilePath/") AS ?workImageFile) }

                  OPTIONAL {
                    ?work wdt:P364 ?origLang .
                    OPTIONAL { ?origLang wdt:P218 ?origLangCode } .
                    SERVICE wikibase:label { bd:serviceParam wikibase:language "en". ?origLang rdfs:label ?origLangLabel }
                  }

                  OPTIONAL {
                    ?workWiki schema:about ?work ;
                              schema:inLanguage "en" ;
                              schema:isPartOf [ wikibase:wikiGroup "wikipedia" ] .
                  }

                  OPTIONAL { ?work rdfs:label ?workLabelFr FILTER(LANG(?workLabelFr)="fr") }
                  OPTIONAL { ?work rdfs:label ?workLabelEn FILTER(LANG(?workLabelEn)="en") }

                  OPTIONAL { ?work schema:description ?workDescEn FILTER(LANG(?workDescEn)="en") }

                  OPTIONAL {
                    ?work rdfs:label ?workLabelOrig .
                    FILTER(BOUND(?origLangCode) && LANG(?workLabelOrig) = ?origLangCode)
                  }

                  OPTIONAL {
                    SELECT ?work (GROUP_CONCAT(DISTINCT ?classLabel; separator=",") AS ?typesFromClassCSV)
                    WHERE {
                      ?work wdt:P31/wdt:P279* ?c .
                      VALUES ?c { wd:Q7725634 wd:Q8261 wd:Q25379 wd:Q482 wd:Q35760 wd:Q36279 }.
                      SERVICE wikibase:label { bd:serviceParam wikibase:language "en". ?c rdfs:label ?classLabel }
                    } GROUP BY ?work
                  }
                  OPTIONAL {
                    SELECT ?work (GROUP_CONCAT(DISTINCT ?genreLabel; separator=",") AS ?genresCSV)
                    WHERE {
                      ?work wdt:P136 ?genre .
                      SERVICE wikibase:label { bd:serviceParam wikibase:language "en". ?genre rdfs:label ?genreLabel }
                    } GROUP BY ?work
                  }
                  OPTIONAL {
                    SELECT ?work (GROUP_CONCAT(DISTINCT ?subjectLabel; separator=",") AS ?subjectsCSV)
                    WHERE {
                      ?work wdt:P921 ?subject .
                      SERVICE wikibase:label { bd:serviceParam wikibase:language "en". ?subject rdfs:label ?subjectLabel }
                    } GROUP BY ?work
                  }
                  OPTIONAL {
                    SELECT ?work (GROUP_CONCAT(DISTINCT ?audLabel; separator=",") AS ?audiencesCSV)
                    WHERE {
                      ?work wdt:P2360 ?aud .
                      SERVICE wikibase:label { bd:serviceParam wikibase:language "en". ?aud rdfs:label ?audLabel }
                    } GROUP BY ?work
                  }

                  SERVICE wikibase:label {
                    bd:serviceParam wikibase:language "fr,en".
                    ?work rdfs:label ?workLabelAny .
                    ?author rdfs:label ?authorLabel .
                  }
                }
                LIMIT 1
                """)
                .formatted(qTitle, languageHint, qAuthor, languageHint, qTitleStr, qAuthorStr);
    }

    // Author by Q-id
    private String buildAuthorByWikidataQid(String wikidataQid) {
        String safeId = wikidataQid.trim().toUpperCase(Locale.ROOT);
        return ("""
                PREFIX wd:    <http://www.wikidata.org/entity/>
                PREFIX wdt:   <http://www.wikidata.org/prop/direct/>
                PREFIX schema:<http://schema.org/>

                SELECT
                  ?authorLabel
                  ?authorImageFile
                  ?authorBirth
                  ?authorDeath
                  ?authorCountryLabel
                  ?authorDescEn
                  ?authorWiki
                WHERE {
                  BIND(wd:%s AS ?author)

                  OPTIONAL {
                    ?author wdt:P18 ?authorImage .
                    BIND(STRAFTER(STR(?authorImage), "Special:FilePath/") AS ?authorImageFile)
                  }
                  OPTIONAL { ?author wdt:P569 ?authorBirth }
                  OPTIONAL { ?author wdt:P570 ?authorDeath }

                  OPTIONAL {
                    ?author wdt:P27 ?authorCountry .
                    SERVICE wikibase:label { bd:serviceParam wikibase:language "en,fr". ?authorCountry rdfs:label ?authorCountryLabel }
                  }

                  OPTIONAL {
                    ?authorWiki schema:about ?author ;
                                schema:inLanguage "en" ;
                                schema:isPartOf [ wikibase:wikiGroup "wikipedia" ] .
                  }

                  OPTIONAL { ?author schema:description ?authorDescEn FILTER(LANG(?authorDescEn)="en") }

                  SERVICE wikibase:label {
                    bd:serviceParam wikibase:language "en,fr".
                    ?author rdfs:label ?authorLabel .
                  }
                }
                LIMIT 1
                """)
                .formatted(safeId);
    }

    /* ============================== Utilities ============================= */
    private String escapeForMwApi(String s) {
        if (s == null)
            return "";
        return s.replace("\"", "\\\"");
    }

    private String escapeForStringLiteral(String s) {
        if (s == null)
            return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    private String commonsFilePathUrl(String fileName) {
        return "https://commons.wikimedia.org/wiki/Special:FilePath/" +
                URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    }

    private String firstNonEmpty(String a, String b) {
        if (a != null && !a.isBlank())
            return a;
        if (b != null && !b.isBlank())
            return b;
        return "";
    }

    private String mergeCsv(String a, String b) {
        Set<String> out = new LinkedHashSet<>();
        if (a != null && !a.isBlank()) {
            for (String s : a.split(",")) {
                String t = s.trim();
                if (!t.isEmpty())
                    out.add(t);
            }
        }
        if (b != null && !b.isBlank()) {
            for (String s : b.split(",")) {
                String t = s.trim();
                if (!t.isEmpty())
                    out.add(t);
            }
        }
        return String.join(",", out);
    }

    private String getStr(JsonNode b, String field) {
        JsonNode n = b.path(field).path("value");
        return n.isMissingNode() ? "" : n.asText("");
    }

    private Integer getInt(JsonNode b, String field) {
        JsonNode n = b.path(field).path("value");
        if (n.isMissingNode())
            return null;
        try {
            return Integer.parseInt(n.asText());
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate getDate(JsonNode b, String field) {
        JsonNode n = b.path(field).path("value");
        if (n.isMissingNode())
            return null;
        try {
            String v = n.asText();
            if (v.length() == 4 && v.chars().allMatch(Character::isDigit)) {
                return LocalDate.of(Integer.parseInt(v), 1, 1);
            }
            return OffsetDateTime.parse(v).toLocalDate();
        } catch (Exception e) {
            try {
                return LocalDate.parse(n.asText().substring(0, 10));
            } catch (Exception e2) {
                return null;
            }
        }
    }

    private String getQidFromIri(JsonNode b, String field) {
        JsonNode n = b.path(field).path("value");
        if (n.isMissingNode())
            return null;
        String iri = n.asText();
        int idx = iri.lastIndexOf('/');
        return (idx >= 0 && idx + 1 < iri.length()) ? iri.substring(idx + 1) : iri;
    }

    /* ================================ DTOs ================================ */
    public static class BookInfo {
        public String originalTitle;
        public Title title; // FR/EN
        public String coverUrl;
        public String authorWikidataQid; // Q-id
        public int publicationYear;
        public String language; // original language (raw)
        public String type; // CSV
        public String category; // CSV
        public String audience; // CSV (may be empty)
        public Description description; // EN-only
        public String wikipediaLink;

        public BookInfo() {
        }

        public BookInfo(String originalTitle, Title title, String coverUrl, String authorWikidataQid,
                int publicationYear, String language, String type, String category,
                String audience, Description description, String wikipediaLink) {
            this.originalTitle = originalTitle;
            this.title = title;
            this.coverUrl = coverUrl;
            this.authorWikidataQid = authorWikidataQid;
            this.publicationYear = publicationYear;
            this.language = language;
            this.type = type;
            this.category = category;
            this.audience = audience;
            this.description = description;
            this.wikipediaLink = wikipediaLink;
        }

        public static class Title {
            public String french, english;

            public Title() {
            }

            public Title(String fr, String en) {
                this.french = fr;
                this.english = en;
            }
        }

        public static class Description {
            public String english;

            public Description() {
            }

            public Description(String en) {
                this.english = en;
            }
        }
    }

    public static class AuthorInfo {
        public String name;
        public String pictureUrl;
        public String country; // raw label
        public Date date;
        public String description; // EN-only
        public String wikipediaLink;

        public AuthorInfo() {
        }

        public AuthorInfo(String name, String pictureUrl, String country,
                Date date, String description, String wikipediaLink) {
            this.name = name;
            this.pictureUrl = pictureUrl;
            this.country = country;
            this.date = date;
            this.description = description;
            this.wikipediaLink = wikipediaLink;
        }

        public static class Date {
            public LocalDate birth;
            public LocalDate death;

            public Date() {
            }

            public Date(LocalDate birth, LocalDate death) {
                this.birth = birth;
                this.death = death;
            }
        }
    }
}
