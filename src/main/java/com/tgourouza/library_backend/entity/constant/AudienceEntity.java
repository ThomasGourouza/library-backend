package com.tgourouza.library_backend.entity.constant;

import com.tgourouza.library_backend.constant.Audience;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "audience")
@Data
public class AudienceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Audience name;

    public AudienceEntity(Audience name) {
        this.name = name;
    }
}
