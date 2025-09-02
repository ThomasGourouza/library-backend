package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtDeathOrCurrent;
import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;
import static com.tgourouza.library_backend.util.utils.toCsv;
import static com.tgourouza.library_backend.util.utils.toList;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.constant.Status;
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
                book.getOriginalTitleLanguageEnglish(),
                multilingualMapper.toMultilingualTitle(book).english(), // TODO: Implement translation
                authorDto,
                book.getAuthorOLKey(),
                calculateAuthorAgeAtPublication(book),
                book.getPublicationYear(),
                book.getCoverUrl(),
                book.getNumberOfPages(),
                multilingualMapper.toMultilingualDescription(book).english(), // TODO: Implement translation
                toList(book.getTagsEnglish()),
                book.getWikipediaLink(),
                "", // TODO: Implement translation dataLanguage
                book.getPersonalNotes(),
                book.getStatusEnglish(),
                book.getFavorite());
    }

    public void updateEntity(BookEntity book, BookCreateRequest request, AuthorEntity author) {
        if (request == null || book == null) {
            return;
        }

        book.setOriginalTitle(request.originalTitle());
        book.setOriginalTitleLanguageEnglish(request.originalTitleDataLanguage().getValue());
        book.setAuthor(author);
        book.setAuthorOLKey(request.authorOLKey());
        book.setPublicationYear(request.publicationYear());
        if (request.originalTitle() != null) {
            Multilingual title = mymemoryService.translateTitle(
                    request.originalTitle(), request.originalTitleDataLanguage());
            multilingualMapper.applyMultilingualTitle(title, book);
        }
        if (request.description() != null) {
            Multilingual description = libreTranslateService.translateTextMultilingual(request.description(),
                    request.dataLanguage());
            multilingualMapper.applyMultilingualDescription(description, book);
        }
        book.setCoverUrl(request.coverUrl());
        book.setNumberOfPages(request.numberOfPages());
        // TODO: translate
        // book.setTagsEnglish(toCsv(request.getBookTags().stream().map(tag -> translate(tag, request.getDataLanguage())).toList()));
        book.setTagsEnglish(toCsv(request.bookTags()));
        book.setWikipediaLink(request.wikipediaLink());
        book.setPersonalNotes("");
        book.setStatusEnglish(Status.UNREAD.getValue());
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
                multilingualMapper.toMultilingualShortDescription(author).english(), // TODO: Implement translation
                multilingualMapper.toMultilingualDescription(author).english(), // TODO: Implement translation
                new TimePlace(
                        author.getBirthDate(),
                        author.getBirthCityEnglish(),
                        author.getBirthCountryEnglish()), // TimePlace birth; TODO: Country
                new TimePlace(
                        author.getDeathDate(),
                        author.getDeathCityEnglish(),
                        author.getDeathCountryEnglish()), // TimePlace death; TODO: Country
                calculateAuthorAgeAtDeathOrCurrent(author), // Integer ageAtDeathOrCurrent;
                toList(author.getCitizenshipsEnglish()), // List<String> citizenships; TODO: List<Country>
                toList(author.getOccupationsEnglish()), // List<String> occupations; TODO: List<AuthorTag>
                toList(author.getLanguagesEnglish()), // List<String> languages; TODO: List<DataLanguage>
                multilingualMapper.toMultilingualWikipediaLink(author).english(), // TODO: Implement translation
                null,
                DataLanguage.ENGLISH.getValue() // TODO: Implement language
        );
    }
}
