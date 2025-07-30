package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookWithoutAuthorDTO;
import com.tgourouza.library_backend.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = { AuthorMapper.class },
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface BookMapper {

    @Named("withoutAuthor")
    @Mapping(source = "language.name", target = "language")
    @Mapping(source = "literaryMovement.name", target = "literaryMovement")
    @Mapping(source = "literaryGenre.name", target = "literaryGenre")
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "status.name", target = "status")
    BookWithoutAuthorDTO toDTOWithoutAuthor(BookEntity book);

    @Mapping(source = "author", target = "author", qualifiedByName = "withoutBooks")
    @Mapping(source = "language.name", target = "language")
    @Mapping(source = "literaryMovement.name", target = "literaryMovement")
    @Mapping(source = "literaryGenre.name", target = "literaryGenre")
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "status.name", target = "status")
    BookDTO toDTO(BookEntity entity);

    @Mapping(target = "id", ignore = true)
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
