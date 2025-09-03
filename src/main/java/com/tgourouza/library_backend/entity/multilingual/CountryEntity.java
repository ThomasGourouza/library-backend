package com.tgourouza.library_backend.entity.multilingual;

import com.tgourouza.library_backend.dto.Multilingual;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "country")
@Getter @Setter @NoArgsConstructor
public class CountryEntity extends MultilingualEntity {
    public CountryEntity(Multilingual m) { super(m); }
    public static CountryEntity of(Multilingual m) { return new CountryEntity(m); }
}