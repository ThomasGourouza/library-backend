package com.tgourouza.library_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
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

    private String englishTitle;
    private String publicationDate;
    private String popularityEurope;
    private String popularityRussia;
    private String targetAge;
    private String language;
    private String literaryMovement;
    private String literaryGenre;
    private String category;
    private String description;
    private String wikipediaLink;
}
