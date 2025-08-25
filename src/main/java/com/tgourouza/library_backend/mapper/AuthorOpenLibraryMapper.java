package com.tgourouza.library_backend.mapper;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.dto.openLibrary.Text;
import static com.tgourouza.library_backend.util.openLibraryUtils.authorImage;
import static com.tgourouza.library_backend.util.openLibraryUtils.readBio;
import static com.tgourouza.library_backend.util.openLibraryUtils.text;

@Component
public class AuthorOpenLibraryMapper {

    private final LanguageDetector detector;

    public AuthorOpenLibraryMapper(LanguageDetector detector) {
        this.detector = detector;
    }

    public AuthorOpenLibrary mapToAuthorOpenLibrary(JsonNode a, String authorOLKey) {
        String name = text(a, "name");
        String wikidataId = text(a.path("remote_ids"), "wikidata");

        // Picture (author photos use /a/id/{photoId}-L.jpg)
        String pictureUrl = null;
        JsonNode photos = a.path("photos");
        if (photos.isArray() && photos.size() > 0) {
            int pid = photos.get(0).asInt(0);
            if (pid > 0) {
                pictureUrl = authorImage(pid, 'L');
            }
        }

        String description = readBio(a);
        Language descriptionLanguage = detector.detectLanguageOf(description);

        return new AuthorOpenLibrary(
                authorOLKey,
                wikidataId,
                name,
                pictureUrl,
                new Text(description, descriptionLanguage));
    }
}
