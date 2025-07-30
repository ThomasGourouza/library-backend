package com.tgourouza.library_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
@Data
@AllArgsConstructor
public class AuthorEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private String firstName;
    @NotNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;
    private LocalDate birthDate;
    private LocalDate deathDate;
    @ManyToOne
    @JoinColumn(name = "gender_id")
    private GenderEntity gender;
    private String description;
    private String wikipediaLink;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<BookEntity> books;
}
// auteur favoris = auteurs dont un des livres est dans les favoris de l'utilisateur