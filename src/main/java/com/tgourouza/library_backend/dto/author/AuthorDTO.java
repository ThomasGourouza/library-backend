package com.tgourouza.library_backend.dto.author;

import java.util.List;
import java.util.UUID;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.MultilingualList;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.book.BookDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDTO {
    private UUID id;
    private String oLKey;
    private String name;
    private String pictureUrl;
    private Multilingual shortDescription;
    private Multilingual description;
    private TimePlace birth;
    private TimePlace death;
    private Integer ageAtDeathOrCurrent;
    private List<String> citizenships;
    private MultilingualList occupations;
    private List<String> languages;
    private Multilingual wikipediaLink;
    private List<BookDTO> books;
}