package com.tgourouza.library_backend.mapper;

import org.springframework.stereotype.Component;

import com.github.pemistahl.lingua.api.LanguageDetector;
import com.tgourouza.library_backend.dto.AuthorInfo;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.dto.openLibrary.Text;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;

@Component
public class AuthorInfoMapper {

        private final LanguageDetector detector;

        public AuthorInfoMapper(LanguageDetector detector) {
                this.detector = detector;
        }

        public AuthorInfo mapToAuthorInfo(AuthorOpenLibrary authorOpenLibrary, AuthorWikidata authorWikidata) {
                return new AuthorInfo(
                                authorOpenLibrary.getOLKey(),
                                authorOpenLibrary.getName(),
                                authorOpenLibrary.getPictureUrl(),
                                authorOpenLibrary.getDescription(),
                                new Text(authorWikidata.shortDescription(),
                                                detector.detectLanguageOf(authorWikidata.shortDescription())),
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
                                new Multilingual(
                                                authorWikidata.wikipediaFr(),
                                                authorWikidata.wikipediaEs(),
                                                authorWikidata.wikipediaIt(),
                                                authorWikidata.wikipediaPt(),
                                                authorWikidata.wikipediaEn(),
                                                authorWikidata.wikipediaDe(),
                                                authorWikidata.wikipediaRu(),
                                                authorWikidata.wikipediaJa()));
        }

        // TODO
        public AuthorCreateRequest mapToAuthorCreateRequest(AuthorInfo authorInfo) {
                return new AuthorCreateRequest();
        }
}
