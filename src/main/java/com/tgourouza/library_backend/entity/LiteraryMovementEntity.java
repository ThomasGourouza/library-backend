package com.tgourouza.library_backend.entity;

import com.tgourouza.library_backend.constant.LiteraryMovement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "literary_movement")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiteraryMovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private LiteraryMovement name;
}