package com.tgourouza.library_backend.entity.constant;

import com.tgourouza.library_backend.constant.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "language")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Language name;
}