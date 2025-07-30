package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.BookDTO;
import com.tgourouza.library_backend.dto.BookCreateRequest;
import com.tgourouza.library_backend.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDTO(BookEntity entity);

    @Mapping(target = "id", ignore = true)

    // propriétés simples → depuis request
    @Mapping(source = "request.originalTitle", target = "originalTitle")
    @Mapping(source = "request.frenchTitle", target = "frenchTitle")
    @Mapping(source = "request.publicationDate", target = "publicationDate")
    @Mapping(source = "request.popularityEurope", target = "popularityEurope")
    @Mapping(source = "request.popularityRussia", target = "popularityRussia")
    @Mapping(source = "request.targetAge", target = "targetAge")
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.wikipediaLink", target = "wikipediaLink")
    @Mapping(source = "request.favorite", target = "favorite")
    @Mapping(source = "request.personalNotes", target = "personalNotes")

    // relations → injectées
    @Mapping(source = "author", target = "author")
    @Mapping(source = "language", target = "language")
    @Mapping(source = "literaryMovement", target = "literaryMovement")
    @Mapping(source = "literaryGenre", target = "literaryGenre")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "status", target = "status")
    BookEntity toEntity(
            BookCreateRequest request,
            AuthorEntity author,
            LanguageEntity language,
            LiteraryMovementEntity literaryMovement,
            LiteraryGenreEntity literaryGenre,
            CategoryEntity category,
            StatusEntity status
    );
}
