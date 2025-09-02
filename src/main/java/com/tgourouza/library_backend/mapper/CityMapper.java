package com.tgourouza.library_backend.mapper;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.entity.CityEntity;

@Component
public class CityMapper {

    public Multilingual toMultilingual(CityEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Multilingual(
                entity.getFrench(),
                entity.getSpanish(),
                entity.getItalian(),
                entity.getPortuguese(),
                entity.getEnglish(),
                entity.getGerman(),
                entity.getRussian(),
                entity.getJapanese());
    }

    public CityEntity toEntity(Multilingual multilingual) {
        if (multilingual == null) {
            return null;
        }
        CityEntity cityEntity = new CityEntity();
        cityEntity.setEnglish(multilingual.english());
        cityEntity.setFrench(multilingual.french());
        cityEntity.setSpanish(multilingual.spanish());
        cityEntity.setItalian(multilingual.italian());
        cityEntity.setGerman(multilingual.german());
        cityEntity.setPortuguese(multilingual.portuguese());
        cityEntity.setRussian(multilingual.russian());
        cityEntity.setJapanese(multilingual.japanese());
        return cityEntity;
    }
}
