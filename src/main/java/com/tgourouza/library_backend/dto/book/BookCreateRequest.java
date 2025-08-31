package com.tgourouza.library_backend.dto.book;

import java.util.Set;
import java.util.UUID;

import com.tgourouza.library_backend.constant.DataLanguage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCreateRequest {

    private String originalTitle;
    private DataLanguage originalTitleLanguage; // TODO: String ?
    private String authorOLKey;
    private Integer publicationYear;
    private String coverUrl;
    private Integer numberOfPages;
    private String description;
    private Set<String> bookTags;
    private String wikipediaLink;
    private UUID authorId;

    // Data language
    private DataLanguage dataLanguage;

}
