package com.tgourouza.library_backend.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.service.MymemoryService;
import com.tgourouza.library_backend.service.NllbService;
import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtDeathOrCurrent;
import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;
import static com.tgourouza.library_backend.util.utils.toCsv;
import static com.tgourouza.library_backend.util.utils.toList;

@Component
public class BookMapper {

    private final MultilingualMapper multilingualMapper;
    private final MymemoryService mymemoryService;
    private final NllbService nllbService;

    public BookMapper(
            MultilingualMapper multilingualMapper,
            MymemoryService mymemoryService,
            NllbService nllbService) {
        this.multilingualMapper = multilingualMapper;
        this.mymemoryService = mymemoryService;
        this.nllbService = nllbService;
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
        book.setTags(toCsv(request.getTags().stream().map(Enum::name).collect(Collectors.toList())));
        book.setWikipediaLink(request.getWikipediaLink());
        book.setPersonalNotes("");
        book.setStatus(Status.UNREAD);
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
                toList(author.getLanguages()), // List<String> languages; TODO: List<Language>
                multilingualMapper.toMultilingualWikipediaLink(author), // Multilingual wikipediaLink;
                null // List<BookDTO> books;
        );
    }
}
