package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.*;
import com.tgourouza.library_backend.entity.constant.*;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final MultilingualMapperUtil multilingualUtil;
    private final AuthorMapper authorMapper;

    public BookMapper(
            MultilingualMapperUtil multilingualUtil,
            @Lazy AuthorMapper authorMapper
    ) {
        this.multilingualUtil = multilingualUtil;
        this.authorMapper = authorMapper;
    }

    public BookDTO toDTO(BookEntity book) {
        if (book == null) return null;
        AuthorDTO authorDto = authorMapper.toDTO(book.getAuthor());
        if (authorDto != null) {
            authorDto.setBooks(null);
        }
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                multilingualUtil.toMultilingualTitle(book),
                authorDto,
                calculateAuthorAgeAtPublication(book),
                book.getPublicationDate(),
                book.getLanguage() != null ? book.getLanguage().getName() : null,
                book.getType() != null ? book.getType().getName() : null,
                book.getCategory() != null ? book.getCategory().getName() : null,
                book.getAudience() != null ? book.getAudience().getName() : null,
                multilingualUtil.toMultilingualDescription(book),
                book.getWikipediaLink(),
                book.getStatus() != null ? book.getStatus().getName() : null,
                book.getFavorite(),
                book.getPersonalNotes()
        );
    }

    private Integer calculateAuthorAgeAtPublication(BookEntity book) {
        if (book == null || book.getAuthor() == null || book.getAuthor().getBirthDate() == null || book.getPublicationDate() == null) {
            return null;
        }
        return Period.between(book.getAuthor().getBirthDate(), book.getPublicationDate()).getYears();
    }

    public List<BookDTO> toDTOs(List<BookEntity> books) {
        if (books == null) return Collections.emptyList();
        return books.stream().map(this::toDTO).collect(Collectors.toList());
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
            multilingualUtil.applyMultilingualTitle(request.getTitle(), book);
        }
        if (request.getDescription() != null) {
            multilingualUtil.applyMultilingualDescription(request.getDescription(), book);
        }
    }
}
