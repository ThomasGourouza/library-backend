package com.tgourouza.library_backend.dto;

import com.tgourouza.library_backend.dto.openLibrary.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorInfo {
    // Open Library fields
    private String name;
    private String pictureUrl;
    private Text description;

    // Wikidata fields
    private Text shortDescription;

    private TimePlace birth;
//    private LocalDate birthDate;
//    private String birthCity;
//    private String birthCountry;

    private TimePlace death;
//    private LocalDate deathDate;
//    private String deathCity;
//    private String deathCountry;

    private List<String> citizenships;
    private List<String> occupations;
    private List<String> languages;

    private Multilingual wikipediaLinks;
//    private String wikipediaEn;
//    private String wikipediaFr;
//    private String wikipediaEs;
//    private String wikipediaDe;
//    private String wikipediaRu;
//    private String wikipediaIt;
//    private String wikipediaPt;
//    private String wikipediaJa;
}
