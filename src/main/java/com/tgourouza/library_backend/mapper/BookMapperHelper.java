package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;
import static com.tgourouza.library_backend.util.utils.toList;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.constant.Type;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.service.LocalTranslateService;

@Component
public class BookMapperHelper {

    private final LocalTranslateService localTranslateService;

    public BookMapperHelper(LocalTranslateService localTranslateService) {
        this.localTranslateService = localTranslateService;
    }

    public BookDTO toDTO(BookEntity book, AuthorDTO authorDto, DataLanguage dataLanguage) {
        if (book == null) {
            return null;
        }
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                localTranslateService.translateFromEnglish(Type.LANGUAGE, book.getOriginalTitleLanguageEnglish(), dataLanguage),
                localTranslateService.translateBookTitle(book, dataLanguage),
                authorDto,
                book.getAuthorOLKey(),
                calculateAuthorAgeAtPublication(book),
                book.getPublicationYear(),
                book.getCoverUrl(),
                book.getNumberOfPages(),
                localTranslateService.translateBookDescription(book, dataLanguage),
                localTranslateService.translateListFromEnglish(Type.BOOK_TAG, toList(book.getTagsEnglish()), dataLanguage),
                book.getWikipediaLink(),
                localTranslateService.translateFromEnglish(Type.LANGUAGE, dataLanguage.getValue(), dataLanguage),
                book.getPersonalNotes(),
                localTranslateService.translateFromEnglish(Type.STATUS, book.getStatusEnglish(), dataLanguage),
                book.getFavorite());
    }

    public List<BookDTO> toDTOsWithoutAuthor(List<BookEntity> books, DataLanguage dataLanguage) {
        if (books == null) {
            return Collections.emptyList();
        }
        return books.stream().map(book -> toDTO(book, null, dataLanguage)).toList();
    }
}
