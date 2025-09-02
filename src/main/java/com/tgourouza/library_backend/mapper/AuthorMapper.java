package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtDeathOrCurrent;
import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;
import static com.tgourouza.library_backend.util.utils.toCsv;
import static com.tgourouza.library_backend.util.utils.toList;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.service.LibreTranslateService;

@Component
public class AuthorMapper {

    private final MultilingualMapper multilingualMapper;
    private final LibreTranslateService libreTranslateService;

    public AuthorMapper(MultilingualMapper multilingualMapper, LibreTranslateService libreTranslateService) {
        this.multilingualMapper = multilingualMapper;
        this.libreTranslateService = libreTranslateService;
    }

    public AuthorDTO toDTO(AuthorEntity author) {
        if (author == null) {
            return null;
        }
        return new AuthorDTO(
                author.getId(), // UUID id;
                author.getOLKey(), // String oLKey;
                author.getName(), // String name;
                author.getPictureUrl(), // String pictureUrl;
                multilingualMapper.toMultilingualShortDescription(author).english(), // TODO: Implement translation
                multilingualMapper.toMultilingualDescription(author).english(), // TODO: Implement translation
                new TimePlace(
                        author.getBirthDate(),
                        author.getBirthCityEnglish(),
                        author.getBirthCountryEnglish()), // TimePlace birth; TODO: Country
                new TimePlace(
                        author.getDeathDate(),
                        author.getDeathCityEnglish(),
                        author.getDeathCountryEnglish()), // TimePlace death; TODO: Country
                calculateAuthorAgeAtDeathOrCurrent(author), // Integer ageAtDeathOrCurrent;
                toList(author.getCitizenshipsEnglish()), // List<String> citizenships; TODO: List<Country>
                toList(author.getOccupationsEnglish()), // List<String> occupations; TODO: List<AuthorTag>
                toList(author.getLanguagesEnglish()), // List<String> languages; TODO: List<DataLanguage>
                multilingualMapper.toMultilingualWikipediaLink(author).english(), // TODO: Implement translation
                toDTOsWithoutAuthor(author.getBooks()), // List<BookDTO> books;
                DataLanguage.ENGLISH.getValue() // TODO: Implement language
        );
    }

    public void updateEntity(AuthorEntity author, AuthorCreateRequest request) {
        if (request == null || author == null) {
            return;
        }

        author.setName(request.name());
        author.setOLKey(request.oLKey());
        author.setPictureUrl(request.pictureUrl());
        if (request.description() != null) {
            Multilingual description = libreTranslateService.translateTextMultilingual(request.description(),
                    request.dataLanguage());
            multilingualMapper.applyMultilingualDescription(description, author);
        }
        if (request.shortDescription() != null) {
            Multilingual shortDescription = libreTranslateService.translateTextMultilingual(request.shortDescription(),
                    request.dataLanguage());
            multilingualMapper.applyMultilingualShortDescription(shortDescription, author);
        }
        if (request.birth() != null) {
            author.setBirthDate(request.birth().date());
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
        multilingualMapper.applyMultilingualWikipediaLink(
        new Multilingual(
            request.wikipediaLinkFrench(),
            request.wikipediaLinkSpanish(),
            request.wikipediaLinkItalian(),
            request.wikipediaLinkPortuguese(),
            request.wikipediaLinkEnglish(),
            request.wikipediaLinkGerman(),
            request.wikipediaLinkRussian(),
            request.wikipediaLinkJapanese() // TODO: Implement translation
        )
        , author); // TODO: Implement translation
    }

    private List<BookDTO> toDTOsWithoutAuthor(List<BookEntity> books) {
        if (books == null) {
            return Collections.emptyList();
        }
        return books.stream().map(this::toDTOWithoutAuthor).toList();
    }

    // TODO: duplicate code (BookMapper)
    private BookDTO toDTOWithoutAuthor(BookEntity book) {
        if (book == null) {
            return null;
        }
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                book.getOriginalTitleLanguageEnglish(),
                multilingualMapper.toMultilingualTitle(book).english(), // TODO: Implement translation
                null,
                book.getAuthorOLKey(),
                calculateAuthorAgeAtPublication(book),
                book.getPublicationYear(),
                book.getCoverUrl(),
                book.getNumberOfPages(),
                multilingualMapper.toMultilingualDescription(book).english(), // TODO: Implement translation
                toList(book.getTagsEnglish()),
                book.getWikipediaLink(),
                "", // TODO: Implement translation dataLanguage
                book.getPersonalNotes(),
                book.getStatusEnglish(),
                book.getFavorite());
    }
}
