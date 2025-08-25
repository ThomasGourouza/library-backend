package com.tgourouza.library_backend.mapper;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.dto.AuthorInfo;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;

@Component
public class AuthorInfoMapper {
    public AuthorInfo mapToAuthorInfo(AuthorOpenLibrary authorOpenLibrary, AuthorWikidata authorWikidata) {
        return new AuthorInfo(
                authorOpenLibrary.getOLKey(),
                authorOpenLibrary.getName(),
                authorOpenLibrary.getPictureUrl(),
                authorOpenLibrary.getDescription(),
                authorWikidata.shortDescription(),
                new TimePlace(
                        authorWikidata.birthDate(),
                        authorWikidata.birthCity(),
                        authorWikidata.birthCountry()
                ),
                new TimePlace(
                        authorWikidata.deathDate(),
                        authorWikidata.deathCity(),
                        authorWikidata.deathCountry()
                ),
                authorWikidata.citizenships(),
                authorWikidata.occupations(),
                authorWikidata.languages(),
                new Multilingual(
                        authorWikidata.wikipediaFr(),
                        authorWikidata.wikipediaEs(),
                        authorWikidata.wikipediaIt(),
                        authorWikidata.wikipediaPt(),
                        authorWikidata.wikipediaEn(),
                        authorWikidata.wikipediaDe(),
                        authorWikidata.wikipediaRu(),
                        authorWikidata.wikipediaJa()
                )
        );
    }
}
