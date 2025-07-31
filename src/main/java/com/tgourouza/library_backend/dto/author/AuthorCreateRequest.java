package com.tgourouza.library_backend.dto.author;

import com.tgourouza.library_backend.dto.Multilingual;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorCreateRequest {
    private String firstName;

    @NotNull(message = "Name must not be null")
    private String name;

    @NotNull(message = "Country ID must not be null")
    private Long countryId;

    private LocalDate birthDate;
    private LocalDate deathDate;

    @NotNull(message = "Gender ID must not be null")
    private Long genderId;

    private Multilingual description;
    private String wikipediaLink;
}
