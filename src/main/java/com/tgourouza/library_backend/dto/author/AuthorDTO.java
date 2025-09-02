package com.tgourouza.library_backend.dto.author;

import java.util.List;
import java.util.UUID;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.book.BookDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDTO { // TODO: record

    private UUID id;
    private String oLKey;
    private String name;
    private String pictureUrl;
    // TODO: String -> only from dataLanguage
    private Multilingual shortDescription;
    // TODO: String -> only from dataLanguage
    private Multilingual description;
    private TimePlace birth;
    private TimePlace death;
    private Integer ageAtDeathOrCurrent;
    private List<String> citizenships; // TODO: translate
    private List<String> occupations; // TODO: translate
    private List<String> languages; // TODO: translate
    // TODO: String -> only from dataLanguage
    private Multilingual wikipediaLink;
    private List<BookDTO> books;
}
