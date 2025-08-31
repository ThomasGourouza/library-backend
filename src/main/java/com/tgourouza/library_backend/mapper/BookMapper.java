package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.*;

import java.util.stream.Collectors;

import com.tgourouza.library_backend.constant.Status;
import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.service.LibreTranslateService;
import com.tgourouza.library_backend.service.MymemoryService;

@Component
public class BookMapper {

    private final MultilingualMapper multilingualMapper;
    private final MymemoryService mymemoryService;
    private final LibreTranslateService libreTranslateService;

    public BookMapper(
            MultilingualMapper multilingualMapper,
            MymemoryService mymemoryService,
            LibreTranslateService libreTranslateService) {
        this.multilingualMapper = multilingualMapper;
        this.mymemoryService = mymemoryService;
        this.libreTranslateService = libreTranslateService;
    }

    public BookDTO toDTO(BookEntity book) {
        if (book == null) {
            return null;
        }
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
                toList(book.getTags()), // TODO: List<BookTag>
                book.getWikipediaLink(),
                book.getPersonalNotes(),
                book.getStatus(),
                book.getFavorite());
    }

    public void updateEntity(BookEntity book, BookCreateRequest request, AuthorEntity author) {
        if (request == null || book == null) {
            return;
        }

        book.setOriginalTitle(request.getOriginalTitle());
        book.setLanguage(request.getOriginalTitleLanguage()); // TODO: rename originalTitleLanguage
        book.setAuthor(author);
        book.setAuthorOLKey(request.getAuthorOLKey());
        book.setPublicationYear(request.getPublicationYear());
        if (request.getOriginalTitle() != null) {
            Multilingual title = mymemoryService.translateTitle(
                    request.getOriginalTitle(), request.getOriginalTitleLanguage());
            multilingualMapper.applyMultilingualTitle(title, book);
        }
        if (request.getDescription() != null) {
            Multilingual description = libreTranslateService.translateTextMultilingual(request.getDescription(),
                    request.getDataLanguage());
            multilingualMapper.applyMultilingualDescription(description, book);
        }
        book.setCoverUrl(request.getCoverUrl());
        book.setNumberOfPages(request.getNumberOfPages());
        book.setTags(toCsv(request.getBookTags().stream().map(Enum::name).collect(Collectors.toList())));
        book.setWikipediaLink(request.getWikipediaLink());
        book.setPersonalNotes("");
        // TODO: translate or always in english ?
        book.setStatus(Status.UNREAD.getValue());
        book.setFavorite(false);
    }

    // TODO: duplicate code (AuthorMapper)
    private AuthorDTO toDTOWithoutBooks(AuthorEntity author) {
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
                toList(author.getLanguages()), // List<String> languages; TODO: List<DataLanguage>
                multilingualMapper.toMultilingualWikipediaLink(author), // Multilingual wikipediaLink;
                null // List<BookDTO> books;
        );
    }
}
