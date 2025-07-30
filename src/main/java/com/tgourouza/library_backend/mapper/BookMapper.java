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
    @Mapping(source = "request.description", target = "description")
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
}
