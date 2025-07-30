package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.author.AuthorWithoutBooksDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.CountryEntity;
import com.tgourouza.library_backend.entity.GenderEntity;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = { BookMapper.class },
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface AuthorMapper {

    @Named("withoutBooks")
    @Mapping(source = "country.name", target = "country")
    @Mapping(source = "gender.name", target = "gender")
    AuthorWithoutBooksDTO toDTOWithoutBooks(AuthorEntity author);

    @Mapping(source = "books", target = "books", qualifiedByName = "withoutAuthor")
    @Mapping(source = "country.name", target = "country")
    @Mapping(source = "gender.name", target = "gender")
    AuthorDTO toDTO(AuthorEntity author);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "country", target = "country")
    @Mapping(source = "gender", target = "gender")
    @Mapping(target = "books", ignore = true)
    AuthorEntity toEntity(
            AuthorCreateRequest request,
            CountryEntity country,
            GenderEntity gender
    );

//    AuthorEntity author = authorMapper.toEntity(
//            request,
//            countryRepository.findById(request.getCountryId()).orElseThrow(...),
//            genderRepository.findById(request.getGenderId()).orElseThrow(...)
//);
}
