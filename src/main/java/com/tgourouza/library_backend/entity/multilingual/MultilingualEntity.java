package com.tgourouza.library_backend.entity.multilingual;

import java.util.UUID;

import com.tgourouza.library_backend.dto.Multilingual;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class MultilingualEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String english;

    private String french;
    private String spanish;
    private String italian;
    private String german;
    private String portuguese;
    private String russian;
    private String japanese;

    protected MultilingualEntity(Multilingual m) {
        if (m == null) return;
        setEnglish(m.english());
        setFrench(m.french());
        setSpanish(m.spanish());
        setItalian(m.italian());
        setGerman(m.german());
        setPortuguese(m.portuguese());
        setRussian(m.russian());
        setJapanese(m.japanese());
    }

    public Multilingual toDto() {
        return new Multilingual(
            getFrench(), getSpanish(), getItalian(), getPortuguese(),
            getEnglish(), getGerman(), getRussian(), getJapanese()
        );
    }
}
