package com.tgourouza.library_backend.entity.multilingual;

import com.tgourouza.library_backend.dto.Multilingual;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "language")
@Getter @Setter @NoArgsConstructor
public class LanguageEntity extends MultilingualEntity {
    public LanguageEntity(Multilingual m) { super(m); }
    public static LanguageEntity of(Multilingual m) { return new LanguageEntity(m); }
}