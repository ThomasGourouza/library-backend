package com.tgourouza.library_backend.mapper;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.tgourouza.library_backend.dto.openLibrary.BookInfo;
import com.tgourouza.library_backend.dto.openLibrary.Text;

import static com.tgourouza.library_backend.util.openLibraryUtils.coverImage;
import static com.tgourouza.library_backend.util.openLibraryUtils.getLanguage;
import static com.tgourouza.library_backend.util.openLibraryUtils.mergeJsonArraysToSet;
import static com.tgourouza.library_backend.util.openLibraryUtils.parseYear;
import static com.tgourouza.library_backend.util.openLibraryUtils.readDescription;
import static com.tgourouza.library_backend.util.openLibraryUtils.readWikipediaLink;
import static com.tgourouza.library_backend.util.openLibraryUtils.text;

@Component
public class BookInfoMapper {

    private final CategoryMapper categoryMapper;

    public BookInfoMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public BookInfo mapToBookInfo(JsonNode doc, JsonNode work) {
        String originalTitle = text(work, "title");
        if (originalTitle.isBlank())
            originalTitle = text(doc, "title");

        String originalTitleLanguage = getLanguage(originalTitle);

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
        int numberOfPages = doc.path("number_of_pages_median").asInt(0);

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
                if (akey.startsWith("/authors/"))
                    authorOLKey = akey.substring("/authors/".length());
            }
        }

        // Publication year
        int publicationYear = doc.path("first_publish_year").asInt(0);
        if (publicationYear == 0) {
            String fpd = text(work, "first_publish_date");
            publicationYear = parseYear(fpd);
        }

        HashSet<String> tags = mergeJsonArraysToSet(
                doc.path("subject_facet"),
                doc.path("subject"),
                work.path("subjects"));

        String description = readDescription(work);

        String wikipedia = readWikipediaLink(work, originalTitle, originalTitleLanguage);

        return new BookInfo(
                new Text(
                        originalTitle,
                        originalTitleLanguage),
                authorOLKey,
                publicationYear,
                coverUrl,
                numberOfPages,
                new Text(
                        description,
                        getLanguage(description)),
                tags,
                categoryMapper.fromTags(tags),
                wikipedia);
    }
}
