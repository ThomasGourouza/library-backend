package com.tgourouza.library_backend.mapper.book;

import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.entity.*;
import com.tgourouza.library_backend.entity.constant.CategoryEntity;
import com.tgourouza.library_backend.entity.constant.LanguageEntity;
import com.tgourouza.library_backend.entity.constant.StatusEntity;
import com.tgourouza.library_backend.entity.constant.TypeEntity;
import com.tgourouza.library_backend.mapper.MultilingualMapperUtil;
import com.tgourouza.library_backend.mapper.author.AuthorWithoutBooksMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private final AuthorWithoutBooksMapper authorMapper;
    private final MultilingualMapperUtil multilingualUtil;

    public BookMapper(AuthorWithoutBooksMapper authorMapper, MultilingualMapperUtil multilingualUtil) {
        this.authorMapper = authorMapper;
        this.multilingualUtil = multilingualUtil;
    }

    public BookDTO toDTO(BookEntity entity) {
        if (entity == null) return null;

        return new BookDTO(
                entity.getId(),
                entity.getOriginalTitle(),
                multilingualUtil.toMultilingualTitle(entity),
                authorMapper.toDTO(entity.getAuthor()),
                entity.getPublicationDate(),
                entity.getPopularityEurope(),
                entity.getPopularityRussia(),
                entity.getTargetAge(),
                entity.getLanguage() != null ? entity.getLanguage().getName() : null,
                entity.getLiteraryMovement() != null ? entity.getLiteraryMovement().getName() : null,
                entity.getLiteraryGenre() != null ? entity.getLiteraryGenre().getName() : null,
                entity.getCategory() != null ? entity.getCategory().getName() : null,
                multilingualUtil.toMultilingualDescription(entity),
                entity.getWikipediaLink(),
                entity.getStatus() != null ? entity.getStatus().getName() : null,
                entity.getFavorite(),
                entity.getPersonalNotes()
        );
    }

    public BookEntity toEntity(
            BookCreateRequest request,
            AuthorEntity author,
            LanguageEntity language,
            LiteraryMovementEntity literaryMovement,
            TypeEntity literaryGenre,
            CategoryEntity category,
            StatusEntity status
    ) {
        BookEntity entity = new BookEntity();
        entity.setAuthor(author);
        entity.setLanguage(language);
        entity.setLiteraryMovement(literaryMovement);
        entity.setLiteraryGenre(literaryGenre);
        entity.setCategory(category);
        entity.setStatus(status);

        entity.setOriginalTitle(request.getOriginalTitle());
        entity.setPublicationDate(request.getPublicationDate());
        entity.setPopularityEurope(request.getPopularityEurope());
        entity.setPopularityRussia(request.getPopularityRussia());
        entity.setTargetAge(request.getTargetAge());
        entity.setWikipediaLink(request.getWikipediaLink());
        entity.setFavorite(request.getFavorite());
        entity.setPersonalNotes(request.getPersonalNotes());
        multilingualUtil.applyMultilingualTitle(request.getTranslatedTitle(), entity);
        multilingualUtil.applyMultilingualDescription(request.getDescription(), entity);

        return entity;
    }

    public void updateEntity(
            BookEntity entity,
            BookCreateRequest request,
            AuthorEntity author,
            LanguageEntity language,
            LiteraryMovementEntity literaryMovement,
            TypeEntity literaryGenre,
            CategoryEntity category,
            StatusEntity status
    ) {
        entity.setOriginalTitle(request.getOriginalTitle());
        entity.setAuthor(author);
        entity.setLanguage(language);
        entity.setLiteraryMovement(literaryMovement);
        entity.setLiteraryGenre(literaryGenre);
        entity.setCategory(category);
        entity.setStatus(status);
        entity.setPublicationDate(request.getPublicationDate());
        entity.setPopularityEurope(request.getPopularityEurope());
        entity.setPopularityRussia(request.getPopularityRussia());
        entity.setTargetAge(request.getTargetAge());
        entity.setWikipediaLink(request.getWikipediaLink());
        entity.setFavorite(request.getFavorite());
        entity.setPersonalNotes(request.getPersonalNotes());

        multilingualUtil.applyMultilingualTitle(request.getTranslatedTitle(), entity);
        multilingualUtil.applyMultilingualDescription(request.getDescription(), entity);
    }
}
