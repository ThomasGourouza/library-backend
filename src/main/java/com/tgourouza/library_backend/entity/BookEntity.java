package com.tgourouza.library_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    @NotNull
    private String originalTitle;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    private AuthorEntity author;

    private String frenchTitle;
    private String publicationDate;
    @Min(0)
    @Max(100)
    private Integer popularityEurope;
    @Min(0)
    @Max(100)
    private Integer popularityRussia;
    private Integer targetAge;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private LanguageEntity language;
    @ManyToOne
    @JoinColumn(name = "literary_movement_id")
    private LiteraryMovementEntity literaryMovement;
    @ManyToOne
    @JoinColumn(name = "literary_genre_id")
    private LiteraryGenreEntity literaryGenre;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    private String description;
    private String wikipediaLink;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusEntity status;
    private Boolean favorite;
    private String personalNotes;
}
