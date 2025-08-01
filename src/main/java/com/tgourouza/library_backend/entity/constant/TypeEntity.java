package com.tgourouza.library_backend.entity.constant;

import com.tgourouza.library_backend.constant.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Type name;
}