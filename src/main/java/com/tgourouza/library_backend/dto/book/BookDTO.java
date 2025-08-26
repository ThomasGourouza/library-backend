package com.tgourouza.library_backend.dto.book;

import java.util.UUID;

import com.github.pemistahl.lingua.api.Language;
import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.MultilingualList;
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
    private MultilingualList tags;
    private Multilingual wikipediaLink;

    // Editable fields
    private String personalNotes;
    private Status status;
    private Boolean favorite;
}
