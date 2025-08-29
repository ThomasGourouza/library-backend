package com.tgourouza.library_backend.mapper;

import org.springframework.stereotype.Component;

import com.github.pemistahl.lingua.api.Language;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;

@Component
public class AuthorCreateRequestMapper {

        public AuthorCreateRequest mapToAuthorCreateRequest(AuthorOpenLibrary authorOpenLibrary,
                        AuthorWikidata authorWikidata) {
                return new AuthorCreateRequest(
                                authorOpenLibrary.getOLKey(),
                                authorOpenLibrary.getName(),
                                authorOpenLibrary.getPictureUrl(),
                                authorOpenLibrary.getDescription(),
                                authorWikidata.shortDescription(),
                                new TimePlace(
                                                authorWikidata.birthDate(),
                                                authorWikidata.birthCity(),
                                                authorWikidata.birthCountry()), // TODO: Country
                                new TimePlace(
                                                authorWikidata.deathDate(),
                                                authorWikidata.deathCity(),
                                                authorWikidata.deathCountry()), // TODO: Country
                                authorWikidata.citizenships(), // TODO: List<Country>
                                authorWikidata.occupations(), // TODO: List<AuthorTag>
                                authorWikidata.languages(), // TODO: List<Language>
                                new Multilingual(
                                                authorWikidata.wikipediaFr(),
                                                authorWikidata.wikipediaEs(),
                                                authorWikidata.wikipediaIt(),
                                                authorWikidata.wikipediaPt(),
                                                authorWikidata.wikipediaEn(),
                                                authorWikidata.wikipediaDe(),
                                                authorWikidata.wikipediaRu(),
                                                authorWikidata.wikipediaJa()),
                                Language.ENGLISH);
        }
}
