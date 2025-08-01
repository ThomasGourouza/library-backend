package com.tgourouza.library_backend.mapper.book;

import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.*;
import com.tgourouza.library_backend.entity.constant.*;
import com.tgourouza.library_backend.mapper.MultilingualMapper;
import com.tgourouza.library_backend.mapper.author.AuthorWithoutBooksMapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;

@Component
public class BookMapper {

    private final MultilingualMapper multilingualMapper;
    private final AuthorWithoutBooksMapper authorWithoutBooksMapper;

    public BookMapper(
            MultilingualMapper multilingualMapper,
            AuthorWithoutBooksMapper authorWithoutBooksMapper
    ) {
        this.multilingualMapper = multilingualMapper;
        this.authorWithoutBooksMapper = authorWithoutBooksMapper;
    }

    public BookDTO toDTO(BookEntity book) {
        if (book == null) return null;
        AuthorDTO authorDto = authorWithoutBooksMapper.toDTO(book.getAuthor());
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                multilingualMapper.toMultilingualTitle(book),
                authorDto,
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

    public void updateEntity(
            @MappingTarget BookEntity book,
            BookCreateRequest request,
            AuthorEntity author,
            LanguageEntity language,
            TypeEntity type,
            CategoryEntity category,
            AudienceEntity audience,
            StatusEntity status
    ) {
        if (request == null || book == null) return;
        book.setOriginalTitle(request.getOriginalTitle());
        book.setAuthor(author);
        book.setPublicationDate(request.getPublicationDate());
        book.setLanguage(language);
        book.setType(type);
        book.setCategory(category);
        book.setAudience(audience);
        book.setStatus(status);
        book.setWikipediaLink(request.getWikipediaLink());
        book.setFavorite(request.getFavorite());
        book.setPersonalNotes(request.getPersonalNotes());
        if (request.getTitle() != null) {
            multilingualMapper.applyMultilingualTitle(request.getTitle(), book);
        }
        if (request.getDescription() != null) {
            multilingualMapper.applyMultilingualDescription(request.getDescription(), book);
        }
    }
}
