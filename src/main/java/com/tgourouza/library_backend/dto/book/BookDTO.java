package com.tgourouza.library_backend.dto.book;

import java.util.List;
import java.util.UUID;

import com.tgourouza.library_backend.constant.Language;
import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.author.AuthorDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDTO {

    private UUID id;
    private String originalTitle;
    private Language language; // title language
    private Multilingual title;
    private AuthorDTO author;
    private String authorOLKey;
    private Integer authorAgeAtPublication;
    private Integer publicationYear;
    private String coverUrl;
    private Integer numberOfPages;
    private Multilingual description;
    private List<String> tags; // TODO: List<BookTag>
    private String wikipediaLink;

    // Editable fields
    private String personalNotes;
    private Status status;
    private Boolean favorite;
}
