package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.AuthorInfo;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.openLibrary.AuthorOpenLibrary;
import com.tgourouza.library_backend.dto.openLibrary.Text;
import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import org.springframework.stereotype.Component;
import com.tgourouza.library_backend.dto.Multilingual;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static com.tgourouza.library_backend.util.openLibraryUtils.getLanguage;

@Component
public class AuthorInfoMapper {
    public AuthorInfo mapToAuthorInfo(AuthorOpenLibrary authorOpenLibrary, AuthorWikidata authorWikidata) {
        LocalDate birth = authorWikidata.birthDate();
        LocalDate death = authorWikidata.deathDate();
        ZoneId zone = ZoneId.of("Europe/Paris");
        int ageYears = (birth == null)
                ? 0
                : (death == null)
                ? (int) ChronoUnit.YEARS.between(birth, LocalDate.now(zone))
                : (int) ChronoUnit.YEARS.between(birth, death);
        if (ageYears < 0) ageYears = 0;
        return new AuthorInfo(
                authorOpenLibrary.getName(),
                authorOpenLibrary.getPictureUrl(),
                authorOpenLibrary.getDescription(),
                new Text(
                        authorWikidata.shortDescription(),
                        getLanguage(authorWikidata.shortDescription())
                ),
                new TimePlace(
                        authorWikidata.birthDate(),
                        authorWikidata.birthCity(),
                        authorWikidata.birthCountry(),
                        0
                ),
                new TimePlace(
                        authorWikidata.deathDate(),
                        authorWikidata.deathCity(),
                        authorWikidata.deathCountry(),
                        ageYears
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
