package com.tgourouza.library_backend.dto;

import java.util.List;

import com.tgourouza.library_backend.dto.openLibrary.Text;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorInfo {
    // Open Library fields
    private String oLKey;
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

    private Multilingual wikipediaLinks;
}
