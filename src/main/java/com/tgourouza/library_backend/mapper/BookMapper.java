package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.entity.*;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = { AuthorWithoutBooksMapper.class },
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface BookMapper {
    @Mapping(source = "author", target = "author")
    @Mapping(source = "language.name", target = "language")
    @Mapping(source = "literaryMovement.name", target = "literaryMovement")
    @Mapping(source = "literaryGenre.name", target = "literaryGenre")
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "status.name", target = "status")
    BookDTO toDTO(BookEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "request.frenchDescription", target = "frenchDescription")
    @Mapping(source = "request.englishDescription", target = "englishDescription")
    @Mapping(source = "request.wikipediaLink", target = "wikipediaLink")
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

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "request.originalTitle", target = "originalTitle")
    @Mapping(source = "request.frenchTitle", target = "frenchTitle")
    @Mapping(source = "request.englishTitle", target = "englishTitle")
    @Mapping(source = "request.publicationDate", target = "publicationDate")
    @Mapping(source = "request.popularityEurope", target = "popularityEurope")
    @Mapping(source = "request.popularityRussia", target = "popularityRussia")
    @Mapping(source = "request.targetAge", target = "targetAge")
    @Mapping(source = "request.frenchDescription", target = "frenchDescription")
    @Mapping(source = "request.englishDescription", target = "englishDescription")
    @Mapping(source = "request.wikipediaLink", target = "wikipediaLink")
    @Mapping(source = "request.favorite", target = "favorite")
    @Mapping(source = "request.personalNotes", target = "personalNotes")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "language", target = "language")
    @Mapping(source = "literaryMovement", target = "literaryMovement")
    @Mapping(source = "literaryGenre", target = "literaryGenre")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "status", target = "status")
    void updateEntityFromRequest(
            BookCreateRequest request,
            AuthorEntity author,
            LanguageEntity language,
            LiteraryMovementEntity literaryMovement,
            LiteraryGenreEntity literaryGenre,
            CategoryEntity category,
            StatusEntity status,
            @MappingTarget BookEntity target
    );
}
