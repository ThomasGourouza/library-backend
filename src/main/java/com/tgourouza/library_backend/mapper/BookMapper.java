package com.tgourouza.library_backend.mapper;

import com.github.pemistahl.lingua.api.Language;
import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.service.MymemoryService;
import com.tgourouza.library_backend.service.NllbService;
import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.dto.TimePlaceTranslated;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.service.EnumResolver;

import java.util.ArrayList;

import static com.tgourouza.library_backend.util.utils.*;

@Component
public class BookMapper {

    private final MultilingualMapper multilingualMapper;
    private final EnumResolver enumResolver;
    private final MymemoryService mymemoryService;
    private final NllbService nllbService;

    public BookMapper(
            MultilingualMapper multilingualMapper,
            EnumResolver enumResolver,
            MymemoryService mymemoryService,
            NllbService nllbService) {
        this.multilingualMapper = multilingualMapper;
        this.enumResolver = enumResolver;
        this.mymemoryService = mymemoryService;
        this.nllbService = nllbService;
    }

    public BookDTO toDTO(BookEntity book) {
        if (book == null)
            return null;
        AuthorDTO authorDto = toDTOWithoutBooks(book.getAuthor());
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                book.getLanguage(),
                multilingualMapper.toMultilingualTitle(book),
                authorDto,
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

    public void updateEntity(BookEntity book, BookCreateRequest request, AuthorEntity author) {
        if (request == null || book == null)
            return;

        book.setOriginalTitle(request.getOriginalTitle().value());
        book.setLanguage(request.getOriginalTitle().language());
        book.setAuthor(author);
        book.setAuthorOLKey(request.getAuthorOLKey());
        book.setPublicationYear(request.getPublicationYear());
        if (request.getOriginalTitle() != null) {
            Multilingual title = mymemoryService.translateTitle(
                    request.getOriginalTitle().value(), request.getOriginalTitle().language());
            multilingualMapper.applyMultilingualTitle(title, book);
        }
        if (request.getDescription() != null) {
            Multilingual description = nllbService.translateText(request.getDescription().value(),
                    request.getDescription().language());
            multilingualMapper.applyMultilingualDescription(description, book);
        }
        book.setCoverUrl(request.getCoverUrl());
        book.setNumberOfPages(request.getNumberOfPages());
        if (request.getTags() != null) {
            Multilingual tags = nllbService.translateText(toCsv(new ArrayList<>(request.getTags())), Language.ENGLISH);
            multilingualMapper.applyMultilingualTags(tags, book);
        }
        multilingualMapper.applyMultilingualWikipediaLink(request.getWikipediaLink(), book);
        book.setPersonalNotes("");
        book.setStatus(Status.UNREAD);
        book.setFavorite(false);
    }

    private AuthorDTO toDTOWithoutBooks(AuthorEntity author) {
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
                null // List<BookDTO> books;
        );
    }
}
