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
    private int publicationYear;
    private String coverUrl;
    private int numberOfPages;
    private Text description;
    private Set<String> tags;
    private Multilingual wikipediaLink;

    private UUID authorId;
//    private String personalNotes; // can be empty
//    private Status status; // can be empty & default value
//    private Boolean favorite; // can be empty & default value
}
