package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.openLibraryUtils.authorImage;
import static com.tgourouza.library_backend.util.openLibraryUtils.parseFlexibleDate;
import static com.tgourouza.library_backend.util.openLibraryUtils.readBio;
import static com.tgourouza.library_backend.util.openLibraryUtils.readWikipediaLink;
import static com.tgourouza.library_backend.util.openLibraryUtils.text;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.pemistahl.lingua.api.Language;
import com.tgourouza.library_backend.dto.author.AuthorDate;
import com.tgourouza.library_backend.dto.openLibrary.AuthorInfo;

@Component
public class AuthorInfoMapper {

    public AuthorInfo mapToAuthorInfo(JsonNode a, String authorOLKey) {
        String name = text(a, "name");
        String wikidataId = text(a.path("remote_ids"), "wikidata");

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
        // TODO: use wikidata language
        String wikipedia = readWikipediaLink(a, name, Language.ENGLISH.toString());

        return new AuthorInfo(
                authorOLKey,
                wikidataId,
                name,
                pictureUrl,
                country,
                new AuthorDate(birth, death),
                description,
                wikipedia);
    }
}
