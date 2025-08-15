package com.tgourouza.library_backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgourouza.library_backend.dto.openLibrary.AuthorInfo;
import com.tgourouza.library_backend.dto.openLibrary.BookFullInfo;
import com.tgourouza.library_backend.dto.openLibrary.BookInfo;
import com.tgourouza.library_backend.mapper.AuthorInfoMapper;
import com.tgourouza.library_backend.mapper.BookInfoMapper;

@Service
public class OpenLibraryService {
    private final RestClient openLibrary; // https://openlibrary.org
    private final BookInfoMapper bookInfoMapper;
    private final AuthorInfoMapper authorInfoMapper;
    private final ObjectMapper mapper = new ObjectMapper();

    public OpenLibraryService(@Qualifier("openLibraryRestClient") RestClient openLibrary, BookInfoMapper bookInfoMapper,
            AuthorInfoMapper authorInfoMapper) {
        this.openLibrary = openLibrary;
        this.bookInfoMapper = bookInfoMapper;
        this.authorInfoMapper = authorInfoMapper;
    }

    public BookInfo getBookInfo(String title, String author) {
        // 1) Search works
        Optional<JsonNode> bestDoc = searchBestWorkDoc(title, author);
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

        // 4) Map to BookInfo and return
        return bookInfoMapper.mapToBookInfo(doc, work);
    }

    public BookFullInfo getBookFullInfo(String title, String author) {
        Optional<JsonNode> bestDoc = searchBestWorkDoc(title, author);
        if (bestDoc.isEmpty())
            return null;
        JsonNode doc = bestDoc.get();
        String workKey = doc.path("key").asText("");
        if (workKey.isBlank() || !workKey.startsWith("/works/")) {
            return null;
        }
        String workId = workKey.substring("/works/".length());
        JsonNode work = getJson("/works/" + workId + ".json");
        BookInfo info = bookInfoMapper.mapToBookInfo(doc, work);
        return new BookFullInfo(
            info.getOriginalTitle(),
            info.getCoverUrl(),
            getAuthorInfo(info.getAuthorId()),
            info.getPublicationYear(),
            info.getLanguage(),
            info.getType(),
            info.getCategory(),
            info.getAudience(),
            info.getDescription(),
            info.getWikipediaLink()
        );
    }

    public AuthorInfo getAuthorInfo(String authorKey) {
        String safe = authorKey.startsWith("OL") ? authorKey : authorKey.trim();
        JsonNode author = getJson("/authors/" + safe + ".json");
        if (author == null || author.isMissingNode())
            return null;

        return authorInfoMapper.mapToAuthorInfo(author, safe);
    }

    private Optional<JsonNode> searchBestWorkDoc(String title, String author) {
        // Build search.json query
        String json = openLibrary.get()
                .uri(uri -> {
                    var b = uri.path("/search.json");
                    if (title != null && !title.isBlank())
                        b = b.queryParam("title", title);
                    if (author != null && !author.isBlank())
                        b = b.queryParam("author", author);
                    b = b.queryParam("fields",
                            "key,title,author_key,cover_i,first_publish_year,language,subject,subject_facet,audience,audience_key");
                    return b.build();
                })
                .retrieve()
                .body(String.class);

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

    private JsonNode getJson(String pathAndQuery) {
        String json = openLibrary.get()
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
