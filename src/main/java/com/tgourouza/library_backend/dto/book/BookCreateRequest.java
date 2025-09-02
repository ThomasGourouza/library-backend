package com.tgourouza.library_backend.dto.book;

import java.util.List;
import java.util.UUID;

import com.tgourouza.library_backend.constant.DataLanguage;

public record BookCreateRequest(
        String originalTitle,
        DataLanguage originalTitleDataLanguage,
        String authorOLKey,
        Integer publicationYear,
        String coverUrl,
        Integer numberOfPages,
        String description,
        List<String> bookTags,
        String wikipediaLink,
        UUID authorId,

        // Data language
        DataLanguage dataLanguage) {

}
