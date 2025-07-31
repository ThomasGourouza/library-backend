package com.tgourouza.library_backend.dto.author;

import com.tgourouza.library_backend.dto.Multilingual;
import lombok.Data;

@Data
public class AuthorCreateRequest {
    private AuthorIdentityCreateRequest identity;
    private Multilingual description;
    private String wikipediaLink;
}
