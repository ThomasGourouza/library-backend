package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.author.AuthorWithoutBooksDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface AuthorWithoutBooksMapper {
    @Mapping(source = "country.name", target = "country")
    @Mapping(source = "gender.name", target = "gender")
    AuthorWithoutBooksDTO toDTO(AuthorEntity author);

}
