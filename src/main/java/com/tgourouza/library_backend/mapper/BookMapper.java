package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.constant.*;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.author.AuthorDate;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.*;
import org.springframework.stereotype.Component;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;

@Component
public class BookMapper {

    private final MultilingualMapper multilingualMapper;

    public BookMapper(
            MultilingualMapper multilingualMapper
    ) {
        this.multilingualMapper = multilingualMapper;
    }

    public BookDTO toDTO(BookEntity book) {
        if (book == null) return null;
        AuthorDTO authorDto = toDTOWithoutBooks(book.getAuthor());
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                multilingualMapper.toMultilingualTitle(book),
                authorDto,
                calculateAuthorAgeAtPublication(book),
                book.getPublicationDate(),
                book.getLanguage(),
                book.getGenre(),
                book.getSubject(),
                book.getAudience(),
                multilingualMapper.toMultilingualDescription(book),
                book.getWikipediaLink(),
                book.getStatus(),
                book.getFavorite(),
                book.getPersonalNotes()
        );
    }

    public void updateEntity(
            BookEntity book,
            BookCreateRequest request,
            AuthorEntity author,
            Language language,
            Genre genre,
            Subject subject,
            Audience audience,
            Status status
    ) {
        if (request == null || book == null) return;
        book.setOriginalTitle(request.getOriginalTitle());
        book.setAuthor(author);
        book.setPublicationDate(request.getPublicationDate());
        book.setLanguage(language);
        book.setGenre(genre);
        book.setSubject(subject);
        book.setAudience(audience);
        book.setStatus(status);
        book.setWikipediaLink(request.getWikipediaLink());
        book.setFavorite(request.getFavorite());
        book.setPersonalNotes(request.getPersonalNotes());
        multilingualMapper.applyMultilingualTitle(request.getTitle(), book);
        multilingualMapper.applyMultilingualDescription(request.getDescription(), book);
    }

    private AuthorDTO toDTOWithoutBooks(AuthorEntity author) {
        if (author == null) return null;
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getCountry(),
                new AuthorDate(author.getBirthDate(), author.getDeathDate()),
                multilingualMapper.toMultilingualDescription(author),
                author.getWikipediaLink(),
                null
        );
    }
}
