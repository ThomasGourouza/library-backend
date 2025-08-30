package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.openLibraryUtils.coverImage;
import static com.tgourouza.library_backend.util.openLibraryUtils.mergeJsonArraysToSet;
import static com.tgourouza.library_backend.util.openLibraryUtils.parseYear;
import static com.tgourouza.library_backend.util.openLibraryUtils.readDescription;
import static com.tgourouza.library_backend.util.openLibraryUtils.readWikipediaLink;
import static com.tgourouza.library_backend.util.openLibraryUtils.text;
import static com.tgourouza.library_backend.util.utils.cleanText;

import java.util.HashSet;

import com.tgourouza.library_backend.service.LibreTranslateService;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.service.AuthorService;

@Component
public class BookCreateRequestMapper {

    private final TagsMapper tagsMapper;
    private final LanguageDetector detector;
    private final AuthorService authorService;
    private final LibreTranslateService libreTranslateService;

    public BookCreateRequestMapper(
            TagsMapper tagsMapper,
            LanguageDetector detector,
            AuthorService authorService,
            LibreTranslateService libreTranslateService) {
        this.tagsMapper = tagsMapper;
        this.detector = detector;
        this.authorService = authorService;
        this.libreTranslateService = libreTranslateService;
    }

    public BookCreateRequest mapToBookCreateRequest(JsonNode doc, JsonNode work, Language dataLanguage) {
        String originalTitle = text(work, "title");
        if (originalTitle.isBlank()) {
            originalTitle = text(doc, "title");
        }

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

        // Number of pages
        var number_of_pages_median = doc.path("number_of_pages_median");
        String number_of_pages_medianText = number_of_pages_median != null ? number_of_pages_median.asText() : null;
        Integer numberOfPages = number_of_pages_medianText != null && !number_of_pages_medianText.isEmpty()
                ? Integer.valueOf(number_of_pages_medianText)
                : null;

        // Author id
        String authorOLKey = null;
        JsonNode ak = doc.path("author_key");
        if (ak.isArray() && ak.size() > 0) {
            authorOLKey = ak.get(0).asText(null); // e.g. "OL23919A"
        } else {
            // fallback from work.authors[].author.key
            JsonNode workAuthors = work.path("authors");
            if (workAuthors.isArray() && workAuthors.size() > 0) {
                String akey = workAuthors.get(0).path("author").path("key").asText("");
                if (akey.startsWith("/authors/")) {
                    authorOLKey = akey.substring("/authors/".length());
                }
            }
        }

        // Publication year
        var first_publish_year = doc.path("first_publish_year");
        String first_publish_yearText = first_publish_year != null ? first_publish_year.asText() : null;
        Integer publicationYear = first_publish_yearText != null && !first_publish_yearText.isEmpty()
                ? Integer.valueOf(first_publish_yearText)
                : null;
        if (publicationYear == null) {
            String fpd = text(work, "first_publish_date");
            publicationYear = parseYear(fpd);
        }

        HashSet<String> tags = mergeJsonArraysToSet(
                doc.path("subject_facet"),
                doc.path("subject"),
                work.path("subjects"));

        String description = cleanText(readDescription(work));

        String wikipedia = readWikipediaLink(work);

        return new BookCreateRequest(
                originalTitle,
                detector.detectLanguageOf(originalTitle),
                authorOLKey,
                publicationYear,
                coverUrl,
                numberOfPages,
                libreTranslateService.translateText(cleanText(description), dataLanguage),
                tagsMapper.fromSet(tags),
                wikipedia,
                authorService.getAuthorEntityId(authorOLKey),
                dataLanguage);
    }
}
