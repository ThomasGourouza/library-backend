package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.toCsv;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.constant.Type;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.service.LibreTranslateService;
import com.tgourouza.library_backend.service.MultilingualService;
import com.tgourouza.library_backend.service.MymemoryService;

@Component
public class BookMapper {

    private final MymemoryService mymemoryService;
    private final LibreTranslateService libreTranslateService;
    private final MultilingualService multilingualService;
    private final BookMapperHelper bookMapperHelper;
    private final AuthorMapperHelper authorMapperHelper;

    public BookMapper(
            MymemoryService mymemoryService,
            LibreTranslateService libreTranslateService,
            BookMapperHelper bookMapperHelper,
            AuthorMapperHelper authorMapperHelper,
            MultilingualService multilingualService) {
        this.mymemoryService = mymemoryService;
        this.libreTranslateService = libreTranslateService;
        this.bookMapperHelper = bookMapperHelper;
        this.authorMapperHelper = authorMapperHelper;
        this.multilingualService = multilingualService;
    }

    public BookDTO toDTO(BookEntity book, DataLanguage dataLanguage) {
        AuthorDTO authorDtoWithoutBooks = authorMapperHelper.toDTO(book.getAuthor(), null, dataLanguage);
        return bookMapperHelper.toDTO(book, authorDtoWithoutBooks, dataLanguage);
    }

    public void updateEntity(BookEntity book, BookCreateRequest request, AuthorEntity author) {
        if (request == null || book == null) {
            return;
        }
        book.setOriginalTitle(request.originalTitle());
        String originalTitleLanguage = request.originalTitleDataLanguage().getValue();
        book.setOriginalTitleLanguageEnglish(originalTitleLanguage);
        // if exists, do nothing. else save
        if (!multilingualService.findByEnglishIgnoreCase(Type.LANGUAGE, originalTitleLanguage)
                .isPresent()) {
            Multilingual language = libreTranslateService.translateTextMultilingual(originalTitleLanguage);
            multilingualService.create(Type.LANGUAGE, language);
        }
        book.setAuthor(author);
        book.setAuthorOLKey(request.authorOLKey());
        book.setPublicationYear(request.publicationYear());
        Multilingual title = mymemoryService.translateTitle(
                request.originalTitle(), request.originalTitleDataLanguage());
        book.setTitleFrench(title.french());
        book.setTitleSpanish(title.spanish());
        book.setTitleItalian(title.italian());
        book.setTitlePortuguese(title.portuguese());
        book.setTitleEnglish(title.english());
        book.setTitleGerman(title.german());
        book.setTitleRussian(title.russian());
        book.setTitleJapanese(title.japanese());

        book.setCoverUrl(request.coverUrl());
        book.setNumberOfPages(request.numberOfPages());

        if (request.description() != null) {
            Multilingual description = libreTranslateService.translateTextMultilingual(request.description());
            book.setDescriptionFrench(description.french());
            book.setDescriptionSpanish(description.spanish());
            book.setDescriptionItalian(description.italian());
            book.setDescriptionPortuguese(description.portuguese());
            book.setDescriptionEnglish(description.english());
            book.setDescriptionGerman(description.german());
            book.setDescriptionRussian(description.russian());
            book.setDescriptionJapanese(description.japanese());
        }

        book.setTagsEnglish(toCsv(request.bookTags()));
        book.setWikipediaLink(request.wikipediaLink());
        book.setPersonalNotes("");
        book.setStatusEnglish(Status.UNREAD.getValue());
        book.setFavorite(false);
    }
}
