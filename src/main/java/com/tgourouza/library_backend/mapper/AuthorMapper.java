package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.constant.Country;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.author.AuthorDate;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;

@Component
public class AuthorMapper {

    private final MultilingualMapper multilingualMapper;

    public AuthorMapper(MultilingualMapper multilingualMapper) {
        this.multilingualMapper = multilingualMapper;
    }

    public AuthorDTO toDTO(AuthorEntity author) {
        if (author == null) return null;
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getCountry(),
                new AuthorDate(author.getBirthDate(), author.getDeathDate()),
                multilingualMapper.toMultilingualDescription(author),
                author.getWikipediaLink(),
                toDTOsWithoutAuthor(author.getBooks())
        );
    }

    public void updateEntity(AuthorEntity author, AuthorCreateRequest request, Country country) {
        if (request == null || author == null) return;
        author.setName(request.getName());
        author.setCountry(country);
        if (request.getDate() != null) {
            author.setBirthDate(request.getDate().getBirth());
            author.setDeathDate(request.getDate().getDeath());
        }
        multilingualMapper.applyMultilingualDescription(request.getDescription(), author);
        author.setWikipediaLink(request.getWikipediaLink());
    }

    private List<BookDTO> toDTOsWithoutAuthor(List<BookEntity> books) {
        if (books == null) return Collections.emptyList();
        return books.stream().map(this::toDTOWithoutAuthor).collect(Collectors.toList());
    }

    private BookDTO toDTOWithoutAuthor(BookEntity book) {
        if (book == null) return null;
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                multilingualMapper.toMultilingualTitle(book),
                null,
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
}
