package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtDeathOrCurrent;
import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;
import static com.tgourouza.library_backend.util.utils.toCsv;
import static com.tgourouza.library_backend.util.utils.toList;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

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
                multilingualMapper.toMultilingualShortDescription(author), // Multilingual shortDescription;
                multilingualMapper.toMultilingualDescription(author), // Multilingual description;
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
                multilingualMapper.toMultilingualWikipediaLink(author), // Multilingual wikipediaLink;
                toDTOsWithoutAuthor(author.getBooks()) // List<BookDTO> books;
        );
    }

    public void updateEntity(AuthorEntity author, AuthorCreateRequest request) {
        if (request == null || author == null) {
            return;
        }

        author.setName(request.getName());
        author.setOLKey(request.getOLKey());
        author.setPictureUrl(request.getPictureUrl());
        if (request.getDescription() != null) {
            Multilingual description = libreTranslateService.translateTextMultilingual(request.getDescription(),
                    request.getDataLanguage());
            multilingualMapper.applyMultilingualDescription(description, author);
        }
        if (request.getShortDescription() != null) {
            Multilingual shortDescription = libreTranslateService.translateTextMultilingual(request.getShortDescription(),
                    request.getDataLanguage());
            multilingualMapper.applyMultilingualShortDescription(shortDescription, author);
        }
        if (request.getBirth() != null) {
            author.setBirthDate(request.getBirth().getDate());
            author.setBirthCityEnglish(request.getBirth().getCity());
            author.setBirthCountryEnglish(request.getBirth().getCountry());
        }
        if (request.getDeath() != null) {
            author.setDeathDate(request.getDeath().getDate());
            author.setDeathCityEnglish(request.getDeath().getCity());
            author.setDeathCountryEnglish(request.getDeath().getCountry());
        }
        author.setCitizenshipsEnglish(toCsv(request.getCitizenships()));
        author.setOccupationsEnglish(toCsv(request.getOccupations()));
        author.setLanguagesEnglish(toCsv(request.getLanguages()));
        multilingualMapper.applyMultilingualWikipediaLink(request.getWikipediaLink(), author);
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
                multilingualMapper.toMultilingualTitle(book),
                null,
                book.getAuthorOLKey(),
                calculateAuthorAgeAtPublication(book),
                book.getPublicationYear(),
                book.getCoverUrl(),
                book.getNumberOfPages(),
                multilingualMapper.toMultilingualDescription(book),
                toList(book.getTagsEnglish()),
                book.getWikipediaLink(),
                book.getPersonalNotes(),
                book.getStatusEnglish(),
                book.getFavorite());
    }
}
