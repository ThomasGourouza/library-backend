package com.tgourouza.library_backend.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.pemistahl.lingua.api.Language;
import com.tgourouza.library_backend.constant.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @NotNull
    private String originalTitle;
    private Language language;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    @JsonBackReference
    private AuthorEntity author;

    @Column(name = "author_ol_key")
    private String authorOLKey;
    private int publicationYear;
    // private Multilingual title;
    private String titleFrench;
    private String titleSpanish;
    private String titleItalian;
    private String titlePortuguese;
    private String titleEnglish;
    private String titleGerman;
    private String titleRussian;
    private String titleJapanese;

    private String coverUrl;
    private int numberOfPages;
    // private Multilingual description;
    private String descriptionFrench;
    private String descriptionSpanish;
    private String descriptionItalian;
    private String descriptionPortuguese;
    private String descriptionEnglish;
    private String descriptionGerman;
    private String descriptionRussian;
    private String descriptionJapanese;

    // private Multilingual tags;
    private String tagsFrench;
    private String tagsSpanish;
    private String tagsItalian;
    private String tagsPortuguese;
    private String tagsEnglish;
    private String tagsGerman;
    private String tagsRussian;
    private String tagsJapanese;

    // private Multilingual wikipediaLink;
    private String wikipediaLinkFrench;
    private String wikipediaLinkSpanish;
    private String wikipediaLinkItalian;
    private String wikipediaLinkPortuguese;
    private String wikipediaLinkEnglish;
    private String wikipediaLinkGerman;
    private String wikipediaLinkRussian;
    private String wikipediaLinkJapanese;

    // Editable fields
    private String personalNotes;
    private Status status;
    private Boolean favorite;
}
