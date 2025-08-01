package com.tgourouza.library_backend.entity.constant;

import com.tgourouza.library_backend.constant.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "type")
@Data
@AllArgsConstructor
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Type name;
}