package com.tgourouza.library_backend.entity;

import com.tgourouza.library_backend.constant.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "gender")
@Data
@AllArgsConstructor
public class GenderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender name;
}