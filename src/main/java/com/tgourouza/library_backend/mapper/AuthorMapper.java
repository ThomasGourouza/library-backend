package com.tgourouza.library_backend.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.pemistahl.lingua.api.Language;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.TimePlaceTranslated;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.service.EnumResolver;
import com.tgourouza.library_backend.service.NllbService;

import static com.tgourouza.library_backend.util.utils.*;

@Component
public class AuthorMapper {

    private final MultilingualMapper multilingualMapper;
    private final EnumResolver enumResolver;
    private final NllbService nllbService;

    public AuthorMapper(MultilingualMapper multilingualMapper, EnumResolver enumResolver, NllbService nllbService) {
        this.multilingualMapper = multilingualMapper;
        this.enumResolver = enumResolver;
        this.nllbService = nllbService;
    }

    public AuthorDTO toDTO(AuthorEntity author) {
        if (author == null)
            return null;
        return new AuthorDTO(
                author.getId(), // UUID id;
                author.getOLKey(), // String oLKey;
                author.getName(), // String name;
                author.getPictureUrl(), // String pictureUrl;
                multilingualMapper.toMultilingualShortDescription(author), // Multilingual shortDescription;
                multilingualMapper.toMultilingualDescription(author), // Multilingual description;
                new TimePlaceTranslated(
                        author.getBirthDate(),
                        author.getBirthCity(),
                        multilingualMapper.toMultilingualBirthCountry(author)), // TimePlaceTranslated birth;
                new TimePlaceTranslated(
                        author.getDeathDate(),
                        author.getDeathCity(),
                        multilingualMapper.toMultilingualDeathCountry(author)), // TimePlaceTranslated death;
                calculateAuthorAgeAtDeathOrCurrent(author), // Integer ageAtDeathOrCurrent;
                multilingualMapper.toMultilingualListCitizenships(author), // MultilingualList citizenships;
                multilingualMapper.toMultilingualListOccupations(author), // MultilingualList occupations;
                multilingualMapper.toMultilingualListLanguages(author), // MultilingualList languages;
                multilingualMapper.toMultilingualWikipediaLink(author), // Multilingual wikipediaLink;
                toDTOsWithoutAuthor(author.getBooks()) // List<BookDTO> books;
        );
    }

    public void updateEntity(AuthorEntity author, AuthorCreateRequest request) {
        if (request == null || author == null)
            return;

        author.setName(request.getName());
        author.setOLKey(request.getOLKey());
        author.setPictureUrl(request.getPictureUrl());
        if (request.getDescription() != null) {
            Multilingual description = nllbService.translateText(request.getDescription().value(),
                    request.getDescription().language());
            multilingualMapper.applyMultilingualDescription(description, author);
        }
        if (request.getShortDescription() != null) {
            Multilingual shortDescription = nllbService.translateText(request.getShortDescription().value(),
                    request.getShortDescription().language());
            multilingualMapper.applyMultilingualShortDescription(shortDescription, author);
        }
        if (request.getBirth() != null) {
            author.setBirthDate(request.getBirth().getDate());
            author.setBirthCity(request.getBirth().getCity());
            if (request.getBirth().getCountry() != null) {
                Multilingual country = nllbService.translateText(request.getBirth().getCountry(), Language.ENGLISH);
                multilingualMapper.applyMultilingualBirthCountry(country, author);
            }
        }
        if (request.getDeath() != null) {
            author.setDeathDate(request.getDeath().getDate());
            author.setDeathCity(request.getDeath().getCity());
            if (request.getDeath().getCountry() != null) {
                Multilingual country = nllbService.translateText(request.getDeath().getCountry(), Language.ENGLISH);
                multilingualMapper.applyMultilingualDeathCountry(country, author);
            }
        }
        if (request.getCitizenships() != null) {
            Multilingual citizenships = nllbService.translateText(toCsv(request.getCitizenships()), Language.ENGLISH);
            multilingualMapper.applyMultilingualCitizenships(citizenships, author);
        }
        if (request.getOccupations() != null) {
            Multilingual occupations = nllbService.translateText(toCsv(request.getOccupations()), Language.ENGLISH);
            multilingualMapper.applyMultilingualOccupations(occupations, author);
        }
        if (request.getLanguages() != null) {
            Multilingual languages = nllbService.translateText(toCsv(request.getLanguages()), Language.ENGLISH);
            multilingualMapper.applyMultilingualLanguages(languages, author);
        }
        multilingualMapper.applyMultilingualWikipediaLink(request.getWikipediaLink(), author);
    }

    private List<BookDTO> toDTOsWithoutAuthor(List<BookEntity> books) {
        if (books == null)
            return Collections.emptyList();
        return books.stream().map(this::toDTOWithoutAuthor).collect(Collectors.toList());
    }

    private BookDTO toDTOWithoutAuthor(BookEntity book) {
        if (book == null)
            return null;
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
                multilingualMapper.toMultilingualListTags(book),
                multilingualMapper.toMultilingualWikipediaLink(book),
                book.getPersonalNotes(),
                enumResolver.getStatus(book.getStatus().toString()),
                book.getFavorite());
    }
}
