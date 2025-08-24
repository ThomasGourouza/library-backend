package com.tgourouza.library_backend.dto;

import com.tgourouza.library_backend.dto.author.AuthorDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorInfo {

    // Open Library fields
    private String oLKey;
    private String wikidataId;

    private String name;
    private String pictureUrl;
    private String description;


    // Wikidata fields
    private String shortDescription;
    private AuthorDate date;
//    private LocalDate birthDate;
//    private LocalDate deathDate;

    private String birthCity;

    private String birthCountry;
    private String deathCity;

    private String deathCountry;

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
