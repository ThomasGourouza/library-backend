package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtDeathOrCurrent;
import static com.tgourouza.library_backend.util.utils.toList;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.constant.Type;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.service.LocalTranslateService;

@Component
public class AuthorMapperHelper {

    private final LocalTranslateService localTranslateService;

    public AuthorMapperHelper(LocalTranslateService localTranslateService) {
        this.localTranslateService = localTranslateService;
    }

    public AuthorDTO toDTO(AuthorEntity author, List<BookDTO> books, DataLanguage dataLanguage) {
        if (author == null) {
            return null;
        }
        return new AuthorDTO(
                author.getId(),
                author.getOLKey(),
                author.getName(),
                author.getPictureUrl(),
                localTranslateService.translateAuthorShortDescription(author, dataLanguage),
                localTranslateService.translateAuthorDescription(author, dataLanguage),
                new TimePlace(
                        author.getBirthDate(),
                        localTranslateService.translateFromEnglish(Type.CITY, author.getBirthCityEnglish(), dataLanguage),
                        localTranslateService.translateFromEnglish(Type.COUNTRY, author.getBirthCountryEnglish(), dataLanguage)),
                new TimePlace(
                        author.getDeathDate(),
                        localTranslateService.translateFromEnglish(Type.CITY, author.getDeathCityEnglish(), dataLanguage),
                        localTranslateService.translateFromEnglish(Type.COUNTRY, author.getDeathCountryEnglish(), dataLanguage)),
                calculateAuthorAgeAtDeathOrCurrent(author),
                localTranslateService.translateListFromEnglish(Type.COUNTRY, toList(author.getCitizenshipsEnglish()), dataLanguage),
                localTranslateService.translateListFromEnglish(Type.OCCUPATION, toList(author.getOccupationsEnglish()), dataLanguage),
                localTranslateService.translateListFromEnglish(Type.LANGUAGE, toList(author.getLanguagesEnglish()), dataLanguage),
                localTranslateService.translateAuthorWikipediaLink(author, dataLanguage),
                books,
                localTranslateService.translateFromEnglish(Type.LANGUAGE, dataLanguage.toString(), dataLanguage)
        );
    }
}
