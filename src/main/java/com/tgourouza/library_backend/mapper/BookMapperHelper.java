package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;
import static com.tgourouza.library_backend.util.utils.toList;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.BookEntity;

@Component
public class BookMapperHelper {

    public BookMapperHelper() {
    }

    public BookDTO toDTO(BookEntity book, AuthorDTO authorDto, DataLanguage dataLanguage) {
        if (book == null) {
            return null;
        }
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                book.getOriginalTitleLanguageEnglish(),
                book.getTitleEnglish(), // TODO: Implement translation
                authorDto,
                book.getAuthorOLKey(),
                calculateAuthorAgeAtPublication(book),
                book.getPublicationYear(),
                book.getCoverUrl(),
                book.getNumberOfPages(),
                book.getDescriptionEnglish(), // TODO: Implement translation
                toList(book.getTagsEnglish()),
                book.getWikipediaLink(),
                "", // TODO: Implement translation dataLanguage
                book.getPersonalNotes(),
                book.getStatusEnglish(),
                book.getFavorite());
    }

    public List<BookDTO> toDTOsWithoutAuthor(List<BookEntity> books, DataLanguage dataLanguage) {
        if (books == null) {
            return Collections.emptyList();
        }
        return books.stream().map(book -> toDTO(book, null, dataLanguage)).toList();
    }
}
