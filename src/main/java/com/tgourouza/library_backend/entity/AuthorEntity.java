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

    // private String description;
    private String descriptionFrench;
    private String descriptionSpanish;
    private String descriptionItalian;
    private String descriptionPortuguese;
    private String descriptionEnglish;
    private String descriptionGerman;
    private String descriptionRussian;
    private String descriptionJapanese;

    // private String shortDescription;
    private String shortDescriptionFrench;
    private String shortDescriptionSpanish;
    private String shortDescriptionItalian;
    private String shortDescriptionPortuguese;
    private String shortDescriptionEnglish;
    private String shortDescriptionGerman;
    private String shortDescriptionRussian;
    private String shortDescriptionJapanese;

    // private TimePlace birth;
    private LocalDate birthDate;
    private String birthCityEnglish;
    private String birthCountryEnglish;

    // private TimePlace death;
    private LocalDate deathDate;
    private String deathCityEnglish;
    private String deathCountryEnglish;

    private String citizenshipsEnglish;

    // private List<String> occupations;
    private String occupationsEnglish;

    private String languagesEnglish;

    // private String wikipediaLink;
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
