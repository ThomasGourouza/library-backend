package com.tgourouza.library_backend.service;

import static com.tgourouza.library_backend.util.openLibraryUtils.lastPathSegment;
import static com.tgourouza.library_backend.util.openLibraryUtils.mapToOlLang;
import static com.tgourouza.library_backend.util.openLibraryUtils.text;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgourouza.library_backend.controller.OpenLibraryController.AuthorInfo;
import com.tgourouza.library_backend.controller.OpenLibraryController.BookInfo;
import com.tgourouza.library_backend.controller.OpenLibraryController.Titles;
import com.tgourouza.library_backend.mapper.AuthorInfoMapper;
import com.tgourouza.library_backend.mapper.BookInfoMapper;

@Service
public class OpenLibraryService {
    private final RestClient ol; // https://openlibrary.org
    private final BookInfoMapper bookInfoMapper;
    private final AuthorInfoMapper authorInfoMapper;
    private final ObjectMapper mapper = new ObjectMapper();

    public OpenLibraryService(@Qualifier("openLibraryRestClient") RestClient ol, BookInfoMapper bookInfoMapper, AuthorInfoMapper authorInfoMapper) {
        this.ol = ol;
        this.bookInfoMapper = bookInfoMapper;
        this.authorInfoMapper = authorInfoMapper;
    }

    public BookInfo getBookInfo(String title, String author, String language) {
        // 1) Search works
        Optional<JsonNode> bestDoc = searchBestWorkDoc(title, author, language);
        if (bestDoc.isEmpty())
            return null;

        JsonNode doc = bestDoc.get();
        String workKey = doc.path("key").asText(""); // e.g. "/works/OL12345W"
        if (workKey.isBlank() || !workKey.startsWith("/works/")) {
            return null;
        }
        String workId = workKey.substring("/works/".length()); // OL12345W

        // 2) Fetch work JSON
        JsonNode work = getJson("/works/" + workId + ".json");

        // 3) Try to fetch FR/EN titles from editions (best effort)
        Titles titles = fetchFrEnTitlesFromEditions(workId);

        // 4) Map to BookInfo and return
        return bookInfoMapper.mapToBookInfo(doc, work, titles);
    }

    public AuthorInfo getAuthorInfo(String authorKey) {
        String safe = authorKey.startsWith("OL") ? authorKey : authorKey.trim();
            JsonNode author = getJson("/authors/" + safe + ".json");
            if (author == null || author.isMissingNode())
                return null;

            return authorInfoMapper.mapToAuthorInfo(author, safe);
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
}
