package com.tgourouza.library_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tgourouza.library_backend.constant.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

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

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    @JsonBackReference
    private AuthorEntity author;

    private LocalDate publicationDate;
    private String wikipediaLink;
    private Language language;
    private Subject subject;
    private Audience audience;
    private Status status;
    private Boolean favorite;
    private String personalNotes;

    private String titleFrench;
    private String titleSpanish;
    private String titleItalian;
    private String titlePortuguese;
    private String titleEnglish;
    private String titleGerman;
    private String titleRussian;
    private String titleJapanese;

    private String descriptionFrench;
    private String descriptionSpanish;
    private String descriptionItalian;
    private String descriptionPortuguese;
    private String descriptionEnglish;
    private String descriptionGerman;
    private String descriptionRussian;
    private String descriptionJapanese;
}
