package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.cleanText;

import org.springframework.stereotype.Component;

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
                        AuthorWikidata authorWikidata) {
                return new AuthorCreateRequest(
                                authorOpenLibrary.getOLKey(),
                                authorOpenLibrary.getName(),
                                authorOpenLibrary.getPictureUrl(),
                                libreTranslateService.translateTextToEnglish(cleanText(authorOpenLibrary.getDescription())),
                                cleanText(authorWikidata.shortDescription()),
                                new TimePlace(
                                                authorWikidata.birthDate(),
                                                authorWikidata.birthCity(),
                                                authorWikidata.birthCountry()),
                                new TimePlace(
                                                authorWikidata.deathDate(),
                                                authorWikidata.deathCity(),
                                                authorWikidata.deathCountry()),

                                authorWikidata.citizenships(),
                                authorWikidata.occupations(),
                                authorWikidata.languages(),

                                authorWikidata.wikipediaFr(),
                                authorWikidata.wikipediaEs(),
                                authorWikidata.wikipediaIt(),
                                authorWikidata.wikipediaPt(),
                                authorWikidata.wikipediaEn(),
                                authorWikidata.wikipediaDe(),
                                authorWikidata.wikipediaRu(),
                                authorWikidata.wikipediaJa());
        }
}
