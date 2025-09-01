package com.tgourouza.library_backend.dto.book;

import java.util.List;
import java.util.UUID;

import com.tgourouza.library_backend.constant.DataLanguage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCreateRequest {

    private String originalTitle;
    private DataLanguage originalTitleDataLanguage;
    private String authorOLKey;
    private Integer publicationYear;
    private String coverUrl;
    private Integer numberOfPages;
    private String description;
    private List<String> bookTags;
    private String wikipediaLink;
    private UUID authorId;

    // Data language
    private DataLanguage dataLanguage;
}
