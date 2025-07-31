package com.tgourouza.library_backend.entity.constant;

import com.tgourouza.library_backend.constant.Status;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "status")
@Data
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status name;

    public StatusEntity(Status name) {
        this.name = name;
    }
}
