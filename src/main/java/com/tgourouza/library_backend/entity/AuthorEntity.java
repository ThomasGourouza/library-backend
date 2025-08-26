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
    private String birthCountry;

    // private TimePlaceTranslated death;
    private LocalDate deathDate;
    private String deathCity;
    private String deathCountry;

    private String citizenships;

    // private Multilingual occupations;
    private String occupationsFrench;
    private String occupationsSpanish;
    private String occupationsItalian;
    private String occupationsPortuguese;
    private String occupationsEnglish;
    private String occupationsGerman;
    private String occupationsRussian;
    private String occupationsJapanese;

    private String languages;

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
