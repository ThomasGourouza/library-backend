package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.openLibraryUtils.coverImage;
import static com.tgourouza.library_backend.util.openLibraryUtils.firstNonEmpty;
import static com.tgourouza.library_backend.util.openLibraryUtils.joinCsv;
import static com.tgourouza.library_backend.util.openLibraryUtils.lastPathSegment;
import static com.tgourouza.library_backend.util.openLibraryUtils.parseYear;
import static com.tgourouza.library_backend.util.openLibraryUtils.readDescription;
import static com.tgourouza.library_backend.util.openLibraryUtils.readWikipediaLink;
import static com.tgourouza.library_backend.util.openLibraryUtils.text;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.tgourouza.library_backend.dto.openLibrary.BookInfo;

@Component
public class BookInfoMapper {

    public BookInfo mapToBookInfo(JsonNode doc, JsonNode work) {
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
}
