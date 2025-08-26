package com.tgourouza.library_backend.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.exception.OpenLibraryUpstreamException;
import com.tgourouza.library_backend.mapper.AuthorOpenLibraryMapper;
import com.tgourouza.library_backend.mapper.BookCreateRequestMapper;

@Service
public class OpenLibraryService {

    private final RestClient openLibraryClient; // https://openlibrary.org
    private final BookCreateRequestMapper bookCreateRequestMapper;
    private final AuthorOpenLibraryMapper authorOpenLibraryMapper;
    private final ObjectMapper mapper = new ObjectMapper();

    public OpenLibraryService(@Qualifier("openLibraryRestClient") RestClient openLibraryClient,
            BookCreateRequestMapper bookCreateRequestMapper,
            AuthorOpenLibraryMapper authorOpenLibraryMapper) {
        this.openLibraryClient = openLibraryClient;
        this.bookCreateRequestMapper = bookCreateRequestMapper;
        this.authorOpenLibraryMapper = authorOpenLibraryMapper;
    }

    public BookCreateRequest getBookCreateRequest(String title, String author, int resultNumber) {
        // 1) Search works
        Optional<JsonNode> bestDoc = searchBestWorkDoc(title, author, resultNumber);
        if (bestDoc.isEmpty()) {
            return null;
        }

        JsonNode doc = bestDoc.get();
        String workKey = doc.path("key").asText(""); // e.g. "/works/OL12345W"
        if (workKey.isBlank() || !workKey.startsWith("/works/")) {
            return null;
        }
        String workId = workKey.substring("/works/".length()); // OL12345W

        // 2) Fetch work JSON
        JsonNode work = getJson("/works/" + workId + ".json");

        // 4) Map to BookCreateRequest and return
        return bookCreateRequestMapper.mapToBookCreateRequest(doc, work);
    }

    public AuthorOpenLibrary getAuthorOpenLibrary(String authorKey) {
        String safe = authorKey.startsWith("OL") ? authorKey : authorKey.trim();
        JsonNode author = getJson("/authors/" + safe + ".json");
        if (author == null || author.isMissingNode()) {
            return null;
        }

        return authorOpenLibraryMapper.mapToAuthorOpenLibrary(author, safe);
    }

    private Optional<JsonNode> searchBestWorkDoc(String title, String author, int resultNumber) {
        try {
            String json = openLibraryClient.get()
                    .uri(uri -> {
                        var b = uri.path("/search.json");
                        if (title != null && !title.isBlank()) {
                            b = b.queryParam("title", title);
                        }
                        if (author != null && !author.isBlank()) {
                            b = b.queryParam("author", author);
                        }
                        b = b.queryParam("fields",
                                "key,title,author_key,cover_i,first_publish_year,subject,subject_facet,number_of_pages_median");
                        return b.build();
                    })
                    .retrieve()
                    .body(String.class);

            if (json == null || json.isBlank()) {
                return Optional.empty();
            }

            JsonNode root = read(json);
            JsonNode docs = root.path("docs");
            if (!docs.isArray() || docs.size() == 0) {
                return Optional.empty();
            }

            List<JsonNode> works = new ArrayList<>();
            for (JsonNode d : docs) {
                String key = d.path("key").asText("");
                if (key.startsWith("/works/")) {
                    works.add(d);
                }
            }

            if (works.isEmpty()) {
                return Optional.of(docs.get(0));
            }

            // 1-based -> 0-based index, wrap around if needed
            // If resultNumber <= 0, treat it as 1
            int size = works.size();
            int idx = (Math.max(1, resultNumber) - 1) % size;

            return Optional.of(works.get(idx));
        } catch (RestClientResponseException ex) {
            int status = ex.getRawStatusCode();
            if (status == 404) {
                return null;
            }
            throw new OpenLibraryUpstreamException("GET", "/search.json", status);
        } catch (RestClientException ex) {
            throw new OpenLibraryUpstreamException("GET", "/search.json", ex);
        } catch (Exception parse) {
            throw new OpenLibraryUpstreamException("GET", "/search.json", parse);
        }
    }

    private JsonNode getJson(String pathAndQuery) {
        URI uri = URI.create(pathAndQuery);
        try {
            String json = openLibraryClient.get()
                    .uri(pathAndQuery)
                    .retrieve()
                    .body(String.class);
            return read(json);
        } catch (RestClientResponseException ex) {
            int status = ex.getRawStatusCode();
            if (status == 404) {
                return null;
            }
            throw new OpenLibraryUpstreamException("GET", uri.toString(), status);
        } catch (RestClientException ex) {
            throw new OpenLibraryUpstreamException("GET", uri.toString(), ex);
        } catch (Exception parse) {
            throw new OpenLibraryUpstreamException("GET", uri.toString(), parse);
        }
    }

    private JsonNode read(String json) {
        try {
            return mapper.readTree(json);
        } catch (Exception e) {
            throw new IllegalStateException("Bad JSON from OpenLibrary", e);
        }
    }
}
