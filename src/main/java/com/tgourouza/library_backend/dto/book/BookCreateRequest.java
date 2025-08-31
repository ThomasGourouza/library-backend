package com.tgourouza.library_backend.dto.book;

import java.util.Set;
import java.util.UUID;

import com.tgourouza.library_backend.constant.Language;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCreateRequest {

    private String originalTitle;
    private Language originalTitleLanguage;
    private String authorOLKey;
    private Integer publicationYear;
    private String coverUrl;
    private Integer numberOfPages;
    private String description;
    private Set<String> bookTags;
    private String wikipediaLink;
    private UUID authorId;

    // Data language
    private Language dataLanguage;

}
