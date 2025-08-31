package com.tgourouza.library_backend.dto.author;

import java.util.List;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.TimePlace;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorCreateRequest {

    // Open Library fields
    private String oLKey;
    @NotBlank(message = "Name is required")
    @Size(max = 255)
    private String name;
    private String pictureUrl;
    private String description;

    // Wikidata fields
    private String shortDescription;
    private TimePlace birth;
    private TimePlace death;

    private List<String> citizenships; // TODO: translate
    private List<String> occupations; // TODO: translate
    private List<String> languages; // TODO: translate

    private Multilingual wikipediaLink;

    // Data language
    private DataLanguage dataLanguage;
}
