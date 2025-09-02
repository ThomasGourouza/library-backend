package com.tgourouza.library_backend.entity;

import com.tgourouza.library_backend.dto.Multilingual;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "city")
@Getter @Setter @NoArgsConstructor
public class CityEntity extends MultilingualEntity {
    public CityEntity(Multilingual m) { super(m); }
    public static CityEntity of(Multilingual m) { return new CityEntity(m); }
}