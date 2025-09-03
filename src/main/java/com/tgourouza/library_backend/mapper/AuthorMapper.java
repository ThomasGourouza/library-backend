package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.toCsv;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.constant.Type;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.service.LibreTranslateService;
import com.tgourouza.library_backend.service.MultilingualService;

@Component
public class AuthorMapper {

    private final LibreTranslateService libreTranslateService;
    private final AuthorMapperHelper authorMapperHelper;
    private final BookMapperHelper bookMapperHelper;
    private final MultilingualService multilingualService;

    public AuthorMapper(LibreTranslateService libreTranslateService, BookMapperHelper bookMapperHelper,
            AuthorMapperHelper authorMapperHelper, MultilingualService multilingualService) {
        this.libreTranslateService = libreTranslateService;
        this.bookMapperHelper = bookMapperHelper;
        this.authorMapperHelper = authorMapperHelper;
        this.multilingualService = multilingualService;
    }

    public AuthorDTO toDTO(AuthorEntity author, DataLanguage dataLanguage) {
        List<BookDTO> booksWithoutAuthor = bookMapperHelper.toDTOsWithoutAuthor(author.getBooks(), dataLanguage);
        return authorMapperHelper.toDTO(author, booksWithoutAuthor, dataLanguage);
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
            author.setBirthCityEnglish(request.birth().city());
            // if exists, do nothing. else save
            String birthCountry = request.birth().country();
            if (!multilingualService.findByEnglishIgnoreCase(Type.COUNTRY, birthCountry)
                    .isPresent()) {
                Multilingual country = libreTranslateService.translateTextMultilingual(birthCountry);
                multilingualService.create(Type.COUNTRY, country);
            }
            author.setBirthCountryEnglish(request.birth().country());
        }
        if (request.death() != null) {
            author.setDeathDate(request.death().date());
            author.setDeathCityEnglish(request.death().city());
            // if exists, do nothing. else save
            String deathCountry = request.death().country();
            if (!multilingualService.findByEnglishIgnoreCase(Type.COUNTRY, deathCountry)
                    .isPresent()) {
                Multilingual country = libreTranslateService.translateTextMultilingual(deathCountry);
                multilingualService.create(Type.COUNTRY, country);
            }
            author.setDeathCountryEnglish(request.death().country());
        }
        // if exists, do nothing. else save
        request.citizenships().forEach(citizenship -> {
            if (!multilingualService.findByEnglishIgnoreCase(Type.COUNTRY, citizenship)
                    .isPresent()) {
                Multilingual country = libreTranslateService.translateTextMultilingual(citizenship);
                multilingualService.create(Type.COUNTRY, country);
            }
        });
        author.setCitizenshipsEnglish(toCsv(request.citizenships()));
        // if exists, do nothing. else save
        request.occupations().forEach(occupation -> {
            if (!multilingualService.findByEnglishIgnoreCase(Type.OCCUPATION, occupation)
                    .isPresent()) {
                Multilingual occ = libreTranslateService.translateTextMultilingual(occupation);
                multilingualService.create(Type.OCCUPATION, occ);
            }
        });
        author.setOccupationsEnglish(toCsv(request.occupations()));
        // if exists, do nothing. else save
        request.languages().forEach(language -> {
            if (!multilingualService.findByEnglishIgnoreCase(Type.LANGUAGE, language)
                    .isPresent()) {
                Multilingual lang = libreTranslateService.translateTextMultilingual(language);
                multilingualService.create(Type.LANGUAGE, lang);
            }
        });
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
