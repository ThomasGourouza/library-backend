package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.toCsv;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.service.LibreTranslateService;

@Component
public class AuthorMapper {

    private final LibreTranslateService libreTranslateService;
    private final AuthorMapperHelper authorMapperHelper;
    private final BookMapperHelper bookMapperHelper;

    public AuthorMapper(LibreTranslateService libreTranslateService, BookMapperHelper bookMapperHelper,
            AuthorMapperHelper authorMapperHelper) {
        this.libreTranslateService = libreTranslateService;
        this.bookMapperHelper = bookMapperHelper;
        this.authorMapperHelper = authorMapperHelper;
    }

    public AuthorDTO toDTO(AuthorEntity author) {
        List<BookDTO> booksWithoutAuthor = bookMapperHelper.toDTOsWithoutAuthor(author.getBooks());
        return authorMapperHelper.toDTO(author, booksWithoutAuthor);
    }

    public void updateEntity(AuthorEntity author, AuthorCreateRequest request) {
        if (request == null || author == null) {
            return;
        }
        author.setName(request.name());
        author.setOLKey(request.oLKey());
        author.setPictureUrl(request.pictureUrl());
        if (request.description() != null) {
            Multilingual description = libreTranslateService.translateTextMultilingual(request.description());
            author.setDescriptionFrench(description.french());
            author.setDescriptionSpanish(description.spanish());
            author.setDescriptionItalian(description.italian());
            author.setDescriptionPortuguese(description.portuguese());
            author.setDescriptionEnglish(description.english());
            author.setDescriptionGerman(description.german());
            author.setDescriptionRussian(description.russian());
            author.setDescriptionJapanese(description.japanese());
        }
        if (request.shortDescription() != null) {
            Multilingual shortDescription = libreTranslateService.translateTextMultilingual(request.shortDescription());
            author.setShortDescriptionFrench(shortDescription.french());
            author.setShortDescriptionSpanish(shortDescription.spanish());
            author.setShortDescriptionItalian(shortDescription.italian());
            author.setShortDescriptionPortuguese(shortDescription.portuguese());
            author.setShortDescriptionEnglish(shortDescription.english());
            author.setShortDescriptionGerman(shortDescription.german());
            author.setShortDescriptionRussian(shortDescription.russian());
            author.setShortDescriptionJapanese(shortDescription.japanese());
        }
        if (request.birth() != null) {
            author.setBirthDate(request.birth().date());
            // TODO: Translate
            author.setBirthCityEnglish(request.birth().city());
            author.setBirthCountryEnglish(request.birth().country());
        }
        if (request.death() != null) {
            author.setDeathDate(request.death().date());
            author.setDeathCityEnglish(request.death().city());
            author.setDeathCountryEnglish(request.death().country());
        }
        author.setCitizenshipsEnglish(toCsv(request.citizenships()));
        author.setOccupationsEnglish(toCsv(request.occupations()));
        author.setLanguagesEnglish(toCsv(request.languages()));

        author.setWikipediaLinkFrench(request.wikipediaLinkFrench());
        author.setWikipediaLinkSpanish(request.wikipediaLinkSpanish());
        author.setWikipediaLinkItalian(request.wikipediaLinkItalian());
        author.setWikipediaLinkPortuguese(request.wikipediaLinkPortuguese());
        author.setWikipediaLinkEnglish(request.wikipediaLinkEnglish());
        author.setWikipediaLinkGerman(request.wikipediaLinkGerman());
        author.setWikipediaLinkRussian(request.wikipediaLinkRussian());
        author.setWikipediaLinkJapanese(request.wikipediaLinkJapanese());
    }
}
