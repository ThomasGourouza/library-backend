package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.BookDTO;
import com.tgourouza.library_backend.dto.BookCreateRequest;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.LanguageEntity;
import com.tgourouza.library_backend.entity.LiteraryGenreEntity;
import com.tgourouza.library_backend.entity.LiteraryMovementEntity;
import com.tgourouza.library_backend.entity.CategoryEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDTO(BookEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", source = "author")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "literaryMovement", source = "literaryMovement")
    @Mapping(target = "literaryGenre", source = "literaryGenre")
    @Mapping(target = "category", source = "category")
    BookEntity toEntity(
            BookCreateRequest request,
            AuthorEntity author,
            LanguageEntity language,
            LiteraryMovementEntity literaryMovement,
            LiteraryGenreEntity literaryGenre,
            CategoryEntity category
    );
}
