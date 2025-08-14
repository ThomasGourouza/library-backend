package com.tgourouza.library_backend.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/openlibrary")
public class OpenLibraryController {

    private final RestClient ol; // https://openlibrary.org
    private final ObjectMapper mapper = new ObjectMapper();

    public OpenLibraryController(@Qualifier("openLibraryRestClient") RestClient ol) {
        this.ol = ol;
    }

    /* ============================ BOOK INFO ============================ */

    /**
     * Search a book by title (mandatory), optional author and language hint.
     * Returns a single best-match BookInfo or 404.
     */
    @GetMapping(value = "/book-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookInfo> getBookInfo(
            @RequestParam String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String language) {
        try {
            // 1) Search works
            Optional<JsonNode> bestDoc = searchBestWorkDoc(title, author, language);
            if (bestDoc.isEmpty())
                return ResponseEntity.notFound().build();

            JsonNode doc = bestDoc.get();
            String workKey = doc.path("key").asText(""); // e.g. "/works/OL12345W"
            if (workKey.isBlank() || !workKey.startsWith("/works/")) {
                return ResponseEntity.notFound().build();
            }
            String workId = workKey.substring("/works/".length()); // OL12345W

            // 2) Fetch work JSON
            JsonNode work = getJson("/works/" + workId + ".json");

            // 3) Try to fetch FR/EN titles from editions (best effort)
            Titles titles = fetchFrEnTitlesFromEditions(workId);

            // 4) Map to BookInfo
            BookInfo info = mapToBookInfo(doc, work, titles);

            return ResponseEntity.ok(info);

        } catch (Exception e) {
            log.error("OpenLibrary book-info failed (title='{}', author='{}', language='{}')",
                    title, author, language, e);
            return ResponseEntity.status(502).build();
        }
    }

    /* =========================== AUTHOR INFO =========================== */

    /**
     * Fetch author details by Open Library author key (e.g. OL23919A).
     */
    @GetMapping(value = "/author-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorInfo> getAuthorInfo(@RequestParam("author_key") String authorKey) {
        try {
            String safe = authorKey.startsWith("OL") ? authorKey : authorKey.trim();
            JsonNode author = getJson("/authors/" + safe + ".json");
            if (author == null || author.isMissingNode())
                return ResponseEntity.notFound().build();

            AuthorInfo info = mapToAuthorInfo(author, safe);
            return ResponseEntity.ok(info);

        } catch (Exception e) {
            log.error("OpenLibrary author-info failed (author_key='{}')", authorKey, e);
            return ResponseEntity.status(502).build();
        }
    }

    /* ============================== Mapping ============================== */

    private BookInfo mapToBookInfo(JsonNode doc, JsonNode work, Titles titles) {
        // Prefer search doc values first (fast), then work fields as fallback.

        // Original title: use the work's "title" (closest to canonical/original)
        String originalTitle = text(work, "title");
        if (originalTitle.isBlank())
            originalTitle = text(doc, "title");

        // Cover
        String coverUrl = null;
        int coverId = doc.path("cover_i").asInt(0);
        if (coverId > 0) {
            coverUrl = coverImage(coverId, 'L');
        } else {
            // fallback from work.covers[]
            JsonNode covers = work.path("covers");
            if (covers.isArray() && covers.size() > 0) {
                coverUrl = coverImage(covers.get(0).asInt(), 'L');
            }
        }

        // Author id
        String authorId = null;
        JsonNode ak = doc.path("author_key");
        if (ak.isArray() && ak.size() > 0) {
            authorId = ak.get(0).asText(null); // e.g. "OL23919A"
        } else {
            // fallback from work.authors[].author.key
            JsonNode workAuthors = work.path("authors");
            if (workAuthors.isArray() && workAuthors.size() > 0) {
                String akey = workAuthors.get(0).path("author").path("key").asText("");
                if (akey.startsWith("/authors/"))
                    authorId = akey.substring("/authors/".length());
            }
        }

        // Publication year
        int publicationYear = doc.path("first_publish_year").asInt(0);
        if (publicationYear == 0) {
            String fpd = text(work, "first_publish_date");
            publicationYear = parseYear(fpd);
        }

        // Original language (raw)
        String language = "";
        // From work.languages[].key like "/languages/eng"
        JsonNode langs = work.path("languages");
        if (langs.isArray() && langs.size() > 0) {
            String key = langs.get(0).path("key").asText("");
            language = lastPathSegment(key); // "eng"
        } else {
            // fallback from search doc "language":[ "eng","fre",... ]
            JsonNode langs2 = doc.path("language");
            if (langs2.isArray() && langs2.size() > 0) {
                language = langs2.get(0).asText("");
            }
        }

        // Type (CSV) – use subject_facet if present; otherwise empty.
        String type = joinCsv(doc.path("subject_facet"));

        // Category (CSV) – use "subject" array from search doc or work.subjects
        String category = joinCsv(doc.path("subject"));
        if (category.isBlank())
            category = joinCsv(work.path("subjects"));

        // Audience (CSV) – from search doc audience/audience_key if present
        String audience = firstNonEmpty(joinCsv(doc.path("audience")), joinCsv(doc.path("audience_key")));

        // Description (EN-only desired; Open Library doesn’t tag lang reliably — just
        // use if present)
        String description = readDescription(work);

        // Wikipedia link – from work.wikipedia or work.links[*].url containing
        // wikipedia.org
        String wikipedia = readWikipediaLink(work);

        return new BookInfo(
                originalTitle,
                new BookInfo.Title(titles.french, titles.english),
                coverUrl,
                authorId,
                publicationYear,
                language,
                type,
                category,
                audience,
                description,
                wikipedia);
    }

    private AuthorInfo mapToAuthorInfo(JsonNode a, String authorKey) {
        String name = text(a, "name");

        // Picture (author photos use /a/id/{photoId}-L.jpg)
        String pictureUrl = null;
        JsonNode photos = a.path("photos");
        if (photos.isArray() && photos.size() > 0) {
            int pid = photos.get(0).asInt(0);
            if (pid > 0)
                pictureUrl = authorImage(pid, 'L');
        }

        // "country" – Open Library rarely has a country; use birth_place if present
        // (raw).
        String country = text(a, "birth_place");

        // Dates
        LocalDate birth = parseFlexibleDate(text(a, "birth_date"));
        LocalDate death = parseFlexibleDate(text(a, "death_date"));

        // Description / bio (may be string or object with "value")
        String description = readBio(a);

        // Wikipedia link – from "wikipedia" or links[].url
        String wikipedia = readWikipediaLink(a);

        return new AuthorInfo(
                authorKey,
                name,
                pictureUrl,
                country,
                new AuthorInfo.AuthorDate(birth, death),
                description,
                wikipedia);
    }

    /* ============================== Search ============================== */

    private Optional<JsonNode> searchBestWorkDoc(String title, String author, String language) {
        // Build search.json query
        String json = ol.get()
                .uri(uri -> {
                    var b = uri.path("/search.json");
                    if (title != null && !title.isBlank())
                        b = b.queryParam("title", title);
                    if (author != null && !author.isBlank())
                        b = b.queryParam("author", author);
                    String olLang = mapToOlLang(language);
                    if (olLang != null)
                        b = b.queryParam("language", olLang);
                    b = b.queryParam("fields",
                            "key,title,author_key,cover_i,first_publish_year,language,subject,subject_facet,audience,audience_key");
                    return b.build();
                })
                .retrieve()
                .body(String.class); // or .toEntity(String.class).getBody()

        if (json == null || json.isBlank())
            return Optional.empty();

        JsonNode root = read(json);
        JsonNode docs = root.path("docs");
        if (!docs.isArray() || docs.size() == 0)
            return Optional.empty();

        // Heuristic: first doc with a work key
        for (JsonNode d : docs) {
            String key = d.path("key").asText("");
            if (key.startsWith("/works/")) {
                return Optional.of(d);
            }
        }
        // else return the first anyway
        return Optional.of(docs.get(0));
    }

    /*
     * ============================== Editions (FR/EN titles)
     * ==============================
     */

    private Titles fetchFrEnTitlesFromEditions(String workId) {
        // Try first page of editions (limit 100) and look for titles by language
        try {
            JsonNode eds = getJson("/works/" + workId + "/editions.json?limit=100");
            JsonNode entries = eds.path("entries");
            if (entries.isArray()) {
                String fr = null, en = null;
                for (JsonNode e : entries) {
                    String title = text(e, "title");
                    if (title.isBlank())
                        continue;
                    Set<String> langs = new HashSet<>();
                    JsonNode larr = e.path("languages");
                    if (larr.isArray()) {
                        for (JsonNode l : larr)
                            langs.add(lastPathSegment(l.path("key").asText("")));
                    }
                    // Open Library may use "fre" or "fra" for French
                    if (fr == null && (langs.contains("fre") || langs.contains("fra")))
                        fr = title;
                    if (en == null && langs.contains("eng"))
                        en = title;
                    if (fr != null && en != null)
                        break;
                }
                return new Titles(fr, en);
            }
        } catch (Exception ignore) {
        }
        return new Titles(null, null);
    }

    /* ============================== HTTP helpers ============================== */

    private JsonNode getJson(String pathAndQuery) {
        String json = ol.get()
                .uri(pathAndQuery)
                .retrieve()
                .body(String.class);
        return read(json);
    }

    private JsonNode read(String json) {
        try {
            return mapper.readTree(json);
        } catch (Exception e) {
            throw new IllegalStateException("Bad JSON from OpenLibrary", e);
        }
    }

    /* ============================== Small utils ============================== */

    private static String text(JsonNode node, String field) {
        if (node == null || node.isMissingNode())
            return "";
        JsonNode n = node.path(field);
        if (n.isMissingNode())
            return "";
        if (n.isTextual())
            return n.asText("");
        // description/bio can be object { "value": "..." }
        String val = n.path("value").asText("");
        return val == null ? "" : val;
    }

    private static String joinCsv(JsonNode arr) {
        if (!arr.isArray() || arr.size() == 0)
            return "";
        List<String> list = new ArrayList<>();
        for (JsonNode n : arr) {
            String s = n.asText("");
            if (s != null && !s.isBlank())
                list.add(s.trim());
        }
        // de-dup but keep order
        LinkedHashSet<String> set = new LinkedHashSet<>(list);
        return String.join(",", set);
    }

    private static String firstNonEmpty(String a, String b) {
        if (a != null && !a.isBlank())
            return a;
        return b == null ? "" : b;
    }

    private static String lastPathSegment(String path) {
        if (path == null)
            return "";
        int i = path.lastIndexOf('/');
        return (i >= 0 && i + 1 < path.length()) ? path.substring(i + 1) : path;
    }

    private static int parseYear(String s) {
        if (s == null)
            return 0;
        // grab leading 4-digit year if present
        for (int i = 0; i + 3 < s.length(); i++) {
            if (Character.isDigit(s.charAt(i)) && Character.isDigit(s.charAt(i + 1))
                    && Character.isDigit(s.charAt(i + 2)) && Character.isDigit(s.charAt(i + 3))) {
                try {
                    return Integer.parseInt(s.substring(i, i + 4));
                } catch (NumberFormatException ignore) {
                }
            }
        }
        return 0;
    }

    private static LocalDate parseFlexibleDate(String s) {
        if (s == null || s.isBlank())
            return null;
        // Try ISO first
        List<String> patterns = List.of("yyyy-MM-dd", "yyyy-MM", "yyyy");
        for (String p : patterns) {
            try {
                if ("yyyy".equals(p))
                    return LocalDate.of(Integer.parseInt(s.substring(0, 4)), 1, 1);
                return LocalDate.parse(s, DateTimeFormatter.ofPattern(p));
            } catch (DateTimeParseException | NumberFormatException ignore) {
            }
        }
        // Last resort: extract a 4-digit year
        int y = parseYear(s);
        return y == 0 ? null : LocalDate.of(y, 1, 1);
    }

    private static String readDescription(JsonNode work) {
        // "description" may be string or {"value": "..."}
        String d = text(work, "description");
        return d == null ? "" : d;
    }

    private static String readBio(JsonNode author) {
        String bio = text(author, "bio");
        return bio == null ? "" : bio;
    }

    private static String readWikipediaLink(JsonNode node) {
        // direct "wikipedia" field
        String wiki = text(node, "wikipedia");
        if (!wiki.isBlank())
            return wiki;

        // links: [ { title, url }, ... ]
        JsonNode links = node.path("links");
        if (links.isArray()) {
            for (JsonNode l : links) {
                String url = l.path("url").asText("");
                if (url.contains("wikipedia.org"))
                    return url;
            }
        }
        return null;
    }

    private static String coverImage(int id, char size) {
        // b/id/{id}-{S|M|L}.jpg
        return "https://covers.openlibrary.org/b/id/" + id + "-" + size + ".jpg";
    }

    private static String authorImage(int id, char size) {
        // a/id/{id}-{S|M|L}.jpg
        return "https://covers.openlibrary.org/a/id/" + id + "-" + size + ".jpg";
    }

    private static String mapToOlLang(String language) {
        if (language == null || language.isBlank())
            return null;
        String lang = language.trim().toLowerCase(Locale.ROOT);
        // Map common 2-letter to Open Library's 3-letter codes (ISO639-2)
        return switch (lang) {
            case "en", "eng" -> "eng";
            case "fr", "fra", "fre" -> "fre"; // OL accepts "fre"
            case "de", "deu", "ger" -> "ger";
            case "es", "spa" -> "spa";
            case "it", "ita" -> "ita";
            default -> null; // leave unfiltered
        };
    }

    private static class Titles {
        final String french;
        final String english;

        Titles(String fr, String en) {
            this.french = fr;
            this.english = en;
        }
    }

    /* ============================== DTOs ============================== */

    public static class BookInfo {
        public String originalTitle;
        public Title title;
        public String coverUrl;
        public String authorId;
        public int publicationYear;
        public String language; // original language (raw)
        public String type;
        public String category;
        public String audience;
        public String description; // EN-only (best-effort)
        public String wikipediaLink;

        public BookInfo() {
        }

        public BookInfo(String originalTitle, Title title, String coverUrl, String authorId,
                int publicationYear, String language, String type, String category,
                String audience, String description, String wikipediaLink) {
            this.originalTitle = originalTitle;
            this.title = title;
            this.coverUrl = coverUrl;
            this.authorId = authorId;
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
    }

    public static class AuthorInfo {
        public String authorId;
        public String name;
        public String pictureUrl;
        public String country;
        public AuthorDate date;
        public String description; // EN-only (best-effort)
        public String wikipediaLink;

        public AuthorInfo() {
        }

        public AuthorInfo(String authorId, String name, String pictureUrl, String country,
                AuthorDate date, String description, String wikipediaLink) {
            this.authorId = authorId;
            this.name = name;
            this.pictureUrl = pictureUrl;
            this.country = country;
            this.date = date;
            this.description = description;
            this.wikipediaLink = wikipediaLink;
        }

        public static class AuthorDate {
            public LocalDate birth, death;

            public AuthorDate() {
            }

            public AuthorDate(LocalDate b, LocalDate d) {
                this.birth = b;
                this.death = d;
            }
        }
    }
}
