package com.tgourouza.library_backend.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "author")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @NotNull
    private String name;

    @Column(name = "ol_key")
    private String oLKey;
    private String pictureUrl;

    // private Multilingual description;
    private String descriptionFrench;
    private String descriptionSpanish;
    private String descriptionItalian;
    private String descriptionPortuguese;
    private String descriptionEnglish;
    private String descriptionGerman;
    private String descriptionRussian;
    private String descriptionJapanese;

    // private Multilingual shortDescription;
    private String shortDescriptionFrench;
    private String shortDescriptionSpanish;
    private String shortDescriptionItalian;
    private String shortDescriptionPortuguese;
    private String shortDescriptionEnglish;
    private String shortDescriptionGerman;
    private String shortDescriptionRussian;
    private String shortDescriptionJapanese;

    // private TimePlaceTranslated birth;
    private LocalDate birthDate;
    private String birthCity;
        // private Multilingual birthCountry;
    private String birthCountryFrench;
    private String birthCountrySpanish;
    private String birthCountryItalian;
    private String birthCountryPortuguese;
    private String birthCountryEnglish;
    private String birthCountryGerman;
    private String birthCountryRussian;
    private String birthCountryJapanese;

    // private TimePlaceTranslated death;
    private LocalDate deathDate;
    private String deathCity;
        // private Multilingual deathCountry;
    private String deathCountryFrench;
    private String deathCountrySpanish;
    private String deathCountryItalian;
    private String deathCountryPortuguese;
    private String deathCountryEnglish;
    private String deathCountryGerman;
    private String deathCountryRussian;
    private String deathCountryJapanese;

    // private Multilingual citizenships;
    private String citizenshipsFrench;
    private String citizenshipsSpanish;
    private String citizenshipsItalian;
    private String citizenshipsPortuguese;
    private String citizenshipsEnglish;
    private String citizenshipsGerman;
    private String citizenshipsRussian;
    private String citizenshipsJapanese;

    // private Multilingual occupations;
    private String occupationsFrench;
    private String occupationsSpanish;
    private String occupationsItalian;
    private String occupationsPortuguese;
    private String occupationsEnglish;
    private String occupationsGerman;
    private String occupationsRussian;
    private String occupationsJapanese;

    // private Multilingual languages;
    private String languagesFrench;
    private String languagesSpanish;
    private String languagesItalian;
    private String languagesPortuguese;
    private String languagesEnglish;
    private String languagesGerman;
    private String languagesRussian;
    private String languagesJapanese;

    // private Multilingual wikipediaLink;
    private String wikipediaLinkFrench;
    private String wikipediaLinkSpanish;
    private String wikipediaLinkItalian;
    private String wikipediaLinkPortuguese;
    private String wikipediaLinkEnglish;
    private String wikipediaLinkGerman;
    private String wikipediaLinkRussian;
    private String wikipediaLinkJapanese;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<BookEntity> books;
}
