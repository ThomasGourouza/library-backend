package com.tgourouza.library_backend.mapper.book;

import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.mapper.MultilingualMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;

@Component
public class BookWithoutAuthorMapper {

    private final MultilingualMapper multilingualMapper;

    public BookWithoutAuthorMapper(
            MultilingualMapper multilingualMapper
    ) {
        this.multilingualMapper = multilingualMapper;
    }

    public BookDTO toDTO(BookEntity book) {
        if (book == null) return null;
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                multilingualMapper.toMultilingualTitle(book),
                null,
                calculateAuthorAgeAtPublication(book),
                book.getPublicationDate(),
                book.getLanguage() != null ? book.getLanguage().getName() : null,
                book.getType() != null ? book.getType().getName() : null,
                book.getCategory() != null ? book.getCategory().getName() : null,
                book.getAudience() != null ? book.getAudience().getName() : null,
                multilingualMapper.toMultilingualDescription(book),
                book.getWikipediaLink(),
                book.getStatus() != null ? book.getStatus().getName() : null,
                book.getFavorite(),
                book.getPersonalNotes()
        );
    }

    public List<BookDTO> toDTOs(List<BookEntity> books) {
        if (books == null) return Collections.emptyList();
        return books.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
