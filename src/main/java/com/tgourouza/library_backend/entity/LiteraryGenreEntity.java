package com.tgourouza.library_backend.entity;

import com.tgourouza.library_backend.constant.LiteraryGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "literary_genre")
@Data
@AllArgsConstructor
public class LiteraryGenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private LiteraryGenre name;
}