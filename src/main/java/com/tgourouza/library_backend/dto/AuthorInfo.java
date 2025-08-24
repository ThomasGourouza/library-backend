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

    private String oLKey;
    private String wikidataId;
    private String name;
    private String pictureUrl;
    private String country;
    private AuthorDate date;
    private String description;
    private String wikipediaLink;



    private String id;
    private String label;
    private String shortDescription;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String birthPlace;
    private String birthCountry;
    private String deathPlace;
    private String deathCountry;
    private List<String> citizenships;
    private List<String> occupations;
    private List<String> languages;
    private String wikipediaEn;
    private String wikipediaFr;
    private String wikipediaEs;
    private String wikipediaDe;
    private String wikipediaRu;
    private String wikipediaIt;
    private String wikipediaPt;
    private String wikipediaJa;
}
