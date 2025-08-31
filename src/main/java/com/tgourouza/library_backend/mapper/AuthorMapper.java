package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                        author.getBirthCity(),
                        author.getBirthCountry()), // TimePlace birth; TODO: Country
                new TimePlace(
                        author.getDeathDate(),
                        author.getDeathCity(),
                        author.getDeathCountry()), // TimePlace death; TODO: Country
                calculateAuthorAgeAtDeathOrCurrent(author), // Integer ageAtDeathOrCurrent;
                toList(author.getCitizenships()), // List<String> citizenships; TODO: List<Country>
                toList(author.getOccupations()), // List<String> occupations; TODO: List<AuthorTag>
                toList(author.getLanguages()), // List<String> languages; TODO: List<Language>
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
            author.setBirthCity(request.getBirth().getCity());
            author.setBirthCountry(request.getBirth().getCountry());
        }
        if (request.getDeath() != null) {
            author.setDeathDate(request.getDeath().getDate());
            author.setDeathCity(request.getDeath().getCity());
            author.setDeathCountry(request.getDeath().getCountry());
        }
        author.setCitizenships(toCsv(request.getCitizenships()));
        author.setOccupations(toCsv(request.getOccupations()));
        author.setLanguages(toCsv(request.getLanguages()));
        multilingualMapper.applyMultilingualWikipediaLink(request.getWikipediaLink(), author);
    }

    private List<BookDTO> toDTOsWithoutAuthor(List<BookEntity> books) {
        if (books == null) {
            return Collections.emptyList();
        }
        return books.stream().map(this::toDTOWithoutAuthor).collect(Collectors.toList());
    }

    // TODO: duplicate code (BookMapper)
    private BookDTO toDTOWithoutAuthor(BookEntity book) {
        if (book == null) {
            return null;
        }
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                book.getLanguage(),
                multilingualMapper.toMultilingualTitle(book),
                null,
                book.getAuthorOLKey(),
                calculateAuthorAgeAtPublication(book),
                book.getPublicationYear(),
                book.getCoverUrl(),
                book.getNumberOfPages(),
                multilingualMapper.toMultilingualDescription(book),
                toList(book.getTags()),
                book.getWikipediaLink(),
                book.getPersonalNotes(),
                book.getStatus(),
                book.getFavorite());
    }
}
