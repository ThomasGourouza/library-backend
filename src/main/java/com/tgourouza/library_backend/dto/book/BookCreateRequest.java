package com.tgourouza.library_backend.dto.book;

import java.util.Set;
import java.util.UUID;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.openLibrary.Text;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCreateRequest {

    private Text originalTitle;
    private String authorOLKey;
    private Integer publicationYear;
    private String coverUrl;
    private Integer numberOfPages;
    private Text description;
    private Set<String> tags;
    private Multilingual wikipediaLink;
    private UUID authorId;
}
