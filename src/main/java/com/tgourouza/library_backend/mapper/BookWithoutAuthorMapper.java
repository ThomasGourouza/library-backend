package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.book.BookWithoutAuthorDTO;
import com.tgourouza.library_backend.entity.BookEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface BookWithoutAuthorMapper {
    @Mapping(source = "language.name", target = "language")
    @Mapping(source = "literaryMovement.name", target = "literaryMovement")
    @Mapping(source = "literaryGenre.name", target = "literaryGenre")
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "status.name", target = "status")
    BookWithoutAuthorDTO toDTO(BookEntity book);
}
