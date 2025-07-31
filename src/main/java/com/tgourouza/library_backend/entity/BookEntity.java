package com.tgourouza.library_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tgourouza.library_backend.entity.constant.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    @NotNull
    private String originalTitle;

    private String translatedTitleFrench;
    private String translatedTitleSpanish;
    private String translatedTitleItalian;
    private String translatedTitlePortuguese;
    private String translatedTitleEnglish;
    private String translatedTitleGerman;
    private String translatedTitleRussian;
    private String translatedTitleJapanese;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    @JsonBackReference
    private AuthorEntity author;

    private LocalDate publicationDate;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private LanguageEntity language;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeEntity type;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne
    @JoinColumn(name = "audience_id")
    private AudienceEntity audience;

    private String descriptionFrench;
    private String descriptionSpanish;
    private String descriptionItalian;
    private String descriptionPortuguese;
    private String descriptionEnglish;
    private String descriptionGerman;
    private String descriptionRussian;
    private String descriptionJapanese;

    private String wikipediaLink;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusEntity status;
    private Boolean favorite;
    private String personalNotes;
}
