package com.tgourouza.library_backend.dto.author;

import com.tgourouza.library_backend.dto.Multilingual;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthorWithoutBooksDTO {
    private UUID id;
    private AuthorIdentityDTO identity;
    private Multilingual description;
    private String wikipediaLink;
}