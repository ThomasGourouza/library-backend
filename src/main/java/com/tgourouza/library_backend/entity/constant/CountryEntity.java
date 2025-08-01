package com.tgourouza.library_backend.entity.constant;

import com.tgourouza.library_backend.constant.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "country")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Country name;
}