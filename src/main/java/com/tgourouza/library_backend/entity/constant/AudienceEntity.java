package com.tgourouza.library_backend.entity.constant;

import com.tgourouza.library_backend.constant.Audience;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "audience")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudienceEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Audience name;
}
