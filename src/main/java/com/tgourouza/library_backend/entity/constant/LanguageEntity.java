package com.tgourouza.library_backend.entity.constant;

import com.tgourouza.library_backend.constant.Language;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "language")
@Data
public class LanguageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Language name;

    public LanguageEntity(Language name) {
        this.name = name;
    }
}