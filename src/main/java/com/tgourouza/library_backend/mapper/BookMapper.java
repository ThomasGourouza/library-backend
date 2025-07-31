package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.entity.*;
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
                authorMapper.toDTO(entity.getAuthor()),
                multilingualUtil.toMultilingualTitle(entity),
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
            LiteraryGenreEntity literaryGenre,
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
            BookEntity target,
            BookCreateRequest request,
            AuthorEntity author,
            LanguageEntity language,
            LiteraryMovementEntity literaryMovement,
            LiteraryGenreEntity literaryGenre,
            CategoryEntity category,
            StatusEntity status
    ) {
        target.setOriginalTitle(request.getOriginalTitle());
        target.setAuthor(author);
        target.setLanguage(language);
        target.setLiteraryMovement(literaryMovement);
        target.setLiteraryGenre(literaryGenre);
        target.setCategory(category);
        target.setStatus(status);
        target.setPublicationDate(request.getPublicationDate());
        target.setPopularityEurope(request.getPopularityEurope());
        target.setPopularityRussia(request.getPopularityRussia());
        target.setTargetAge(request.getTargetAge());
        target.setWikipediaLink(request.getWikipediaLink());
        target.setFavorite(request.getFavorite());
        target.setPersonalNotes(request.getPersonalNotes());

        multilingualUtil.applyMultilingualTitle(request.getTranslatedTitle(), target);
        multilingualUtil.applyMultilingualDescription(request.getDescription(), target);
    }
}
