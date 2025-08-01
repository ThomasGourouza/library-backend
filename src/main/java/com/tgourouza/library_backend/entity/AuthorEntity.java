package com.tgourouza.library_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tgourouza.library_backend.constant.Country;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    @NotNull
    private String name;
    private Country country;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String wikipediaLink;

    private String descriptionFrench;
    private String descriptionSpanish;
    private String descriptionItalian;
    private String descriptionPortuguese;
    private String descriptionEnglish;
    private String descriptionGerman;
    private String descriptionRussian;
    private String descriptionJapanese;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<BookEntity> books;
}
