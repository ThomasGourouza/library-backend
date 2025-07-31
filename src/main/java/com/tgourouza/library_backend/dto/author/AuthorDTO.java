package com.tgourouza.library_backend.dto.author;

import com.tgourouza.library_backend.constant.Country;
import com.tgourouza.library_backend.constant.Gender;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.book.BookWithoutAuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthorDTO {
    private UUID id;
    private String firstName;
    private String name;
    private Country country;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private Gender gender;
    private Multilingual description;
    private String wikipediaLink;
    private List<BookWithoutAuthorDTO> books;
}