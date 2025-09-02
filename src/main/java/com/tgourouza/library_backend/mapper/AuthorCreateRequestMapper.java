package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.cleanText;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import com.tgourouza.library_backend.service.LibreTranslateService;

@Component
public class AuthorCreateRequestMapper {

    private final LibreTranslateService libreTranslateService;

    public AuthorCreateRequestMapper(LibreTranslateService libreTranslateService) {
        this.libreTranslateService = libreTranslateService;
    }

    public AuthorCreateRequest mapToAuthorCreateRequest(AuthorOpenLibrary authorOpenLibrary,
            AuthorWikidata authorWikidata, DataLanguage dataLanguage) {
        return new AuthorCreateRequest(
                authorOpenLibrary.getOLKey(),
                authorOpenLibrary.getName(),
                authorOpenLibrary.getPictureUrl(),
                libreTranslateService.translateText(cleanText(authorOpenLibrary.getDescription()), dataLanguage),
                libreTranslateService.translateTextFromSource(cleanText(authorWikidata.shortDescription()), DataLanguage.ENGLISH, dataLanguage),
                new TimePlace(
                        authorWikidata.birthDate(),
                        authorWikidata.birthCity(), // TODO: City
                        authorWikidata.birthCountry()), // TODO: Country
                new TimePlace(
                        authorWikidata.deathDate(),
                        authorWikidata.deathCity(), // TODO: Country
                        authorWikidata.deathCountry()), // TODO: Country
                authorWikidata.citizenships(), // TODO: List<Country>
                authorWikidata.occupations(), // TODO: List<AuthorTag>
                authorWikidata.languages(), // TODO: List<DataLanguage>

                authorWikidata.wikipediaFr(),
                authorWikidata.wikipediaEs(),
                authorWikidata.wikipediaIt(),
                authorWikidata.wikipediaPt(),
                authorWikidata.wikipediaEn(),
                authorWikidata.wikipediaDe(),
                authorWikidata.wikipediaRu(),
                authorWikidata.wikipediaJa(),

                dataLanguage);
    }
}
