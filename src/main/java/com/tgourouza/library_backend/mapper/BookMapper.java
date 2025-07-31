package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.Multilingual;
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

    public BookDTO toDTO(BookEntity entity) {
        if (entity == null) return null;

        AuthorDTO authorDto = authorMapper.toDTO(entity.getAuthor());
        if (authorDto != null) {
            authorDto.setBooks(null);
        }

        return new BookDTO(
                entity.getId(),
                entity.getOriginalTitle(),
                multilingualUtil.toMultilingualTitle(entity),
                authorDto,
                calculateAuthorAgeAtPublication(entity),
                entity.getPublicationDate(),
                entity.getLanguage() != null ? entity.getLanguage().getName() : null,
                entity.getType() != null ? entity.getType().getName() : null,
                entity.getCategory() != null ? entity.getCategory().getName() : null,
                entity.getAudience() != null ? entity.getAudience().getName() : null,
                multilingualUtil.toMultilingualDescription(entity),
                entity.getWikipediaLink(),
                entity.getStatus() != null ? entity.getStatus().getName() : null,
                entity.getFavorite(),
                entity.getPersonalNotes()
        );
    }

    private Integer calculateAuthorAgeAtPublication(BookEntity book) {
        if (book == null || book.getAuthor() == null || book.getAuthor().getBirthDate() == null || book.getPublicationDate() == null) {
            return null;
        }
        return Period.between(book.getAuthor().getBirthDate(), book.getPublicationDate()).getYears();
    }

    public List<BookDTO> toDTOs(List<BookEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public BookEntity toEntity(
            BookCreateRequest request,
            AuthorEntity author,
            LanguageEntity language,
            TypeEntity type,
            CategoryEntity category,
            AudienceEntity audience,
            StatusEntity status
    ) {
        if (request == null) return null;

        BookEntity entity = new BookEntity();
        entity.setOriginalTitle(request.getOriginalTitle());
        entity.setAuthor(author);
        entity.setPublicationDate(request.getPublicationDate());
        entity.setLanguage(language);
        entity.setType(type);
        entity.setCategory(category);
        entity.setAudience(audience);
        entity.setStatus(status);
        entity.setWikipediaLink(request.getWikipediaLink());
        entity.setFavorite(request.getFavorite());
        entity.setPersonalNotes(request.getPersonalNotes());

        if (request.getTitle() != null) {
            multilingualUtil.applyMultilingualTitle(request.getTitle(), entity);
        }

        if (request.getDescription() != null) {
            multilingualUtil.applyMultilingualDescription(request.getDescription(), entity);
        }

        return entity;
    }

    public void updateEntity(
            @MappingTarget BookEntity target,
            BookCreateRequest request,
            AuthorEntity author,
            LanguageEntity language,
            TypeEntity type,
            CategoryEntity category,
            AudienceEntity audience,
            StatusEntity status
    ) {
        if (request == null || target == null) return;

        target.setOriginalTitle(request.getOriginalTitle());
        target.setAuthor(author);
        target.setPublicationDate(request.getPublicationDate());
        target.setLanguage(language);
        target.setType(type);
        target.setCategory(category);
        target.setAudience(audience);
        target.setStatus(status);
        target.setWikipediaLink(request.getWikipediaLink());
        target.setFavorite(request.getFavorite());
        target.setPersonalNotes(request.getPersonalNotes());

        if (request.getTitle() != null) {
            multilingualUtil.applyMultilingualTitle(request.getTitle(), target);
        }

        if (request.getDescription() != null) {
            multilingualUtil.applyMultilingualDescription(request.getDescription(), target);
        }
    }

}
