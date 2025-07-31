package com.tgourouza.library_backend.dto.author;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.book.BookWithoutAuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthorDTO {
    private UUID id;
    private AuthorIdentityDTO identity;
    private Multilingual description;
    private String wikipediaLink;
    private List<BookWithoutAuthorDTO> books;
}