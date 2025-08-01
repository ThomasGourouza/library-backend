package com.tgourouza.library_backend.dto.author;

import com.tgourouza.library_backend.constant.Country;
import com.tgourouza.library_backend.dto.Multilingual;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthorCreateRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255)
    private String name;
    private AuthorDate date;
    private Multilingual description;
    private String wikipediaLink;
    // Field with default value
    private String country;
}
