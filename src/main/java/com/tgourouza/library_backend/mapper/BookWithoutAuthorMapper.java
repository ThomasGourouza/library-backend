package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.book.BookWithoutAuthorDTO;
import com.tgourouza.library_backend.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookWithoutAuthorMapper {

    private final MultilingualMapperUtil multilingualUtil;

    public BookWithoutAuthorMapper(MultilingualMapperUtil multilingualUtil) {
        this.multilingualUtil = multilingualUtil;
    }

    public BookWithoutAuthorDTO toDTO(BookEntity book) {
        if (book == null) return null;

        return new BookWithoutAuthorDTO(
                book.getId(),
                book.getOriginalTitle(),
                multilingualUtil.toMultilingualTitle(book),
                book.getPublicationDate(),
                book.getPopularityEurope(),
                book.getPopularityRussia(),
                book.getTargetAge(),
                book.getLanguage() != null ? book.getLanguage().getName() : null,
                book.getLiteraryMovement() != null ? book.getLiteraryMovement().getName() : null,
                book.getLiteraryGenre() != null ? book.getLiteraryGenre().getName() : null,
                book.getCategory() != null ? book.getCategory().getName() : null,
                multilingualUtil.toMultilingualDescription(book),
                book.getWikipediaLink(),
                book.getStatus() != null ? book.getStatus().getName() : null,
                book.getFavorite(),
                book.getPersonalNotes()
        );
    }
}
