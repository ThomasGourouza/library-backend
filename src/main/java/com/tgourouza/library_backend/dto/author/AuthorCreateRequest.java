package com.tgourouza.library_backend.dto.author;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.openLibrary.Text;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthorCreateRequest {
    // Open Library fields
    private String oLKey;
    @NotBlank(message = "Name is required")
    @Size(max = 255)
    private String name;
    private String pictureUrl;
    private Text description;

    // Wikidata fields
    private Text shortDescription;
    private TimePlace birth;
    private TimePlace death;

    private List<String> citizenships;
    private List<String> occupations;
    private List<String> languages;

    private Multilingual wikipediaLink;
}
