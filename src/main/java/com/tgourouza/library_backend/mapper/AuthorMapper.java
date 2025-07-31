package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.CountryEntity;
import com.tgourouza.library_backend.entity.GenderEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    private final BookWithoutAuthorMapper bookWithoutAuthorMapper;
    private final MultilingualMapperUtil multilingualMapperUtil;

    public AuthorMapper(
            BookWithoutAuthorMapper bookWithoutAuthorMapper,
            MultilingualMapperUtil multilingualMapperUtil
    ) {
        this.bookWithoutAuthorMapper = bookWithoutAuthorMapper;
        this.multilingualMapperUtil = multilingualMapperUtil;
    }

    public AuthorDTO toDTO(AuthorEntity author) {
        if (author == null) return null;

        return new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getName(),
                author.getCountry() != null ? author.getCountry().getName() : null,
                author.getBirthDate(),
                author.getDeathDate(),
                author.getGender() != null ? author.getGender().getName() : null,
                multilingualMapperUtil.toMultilingualDescription(author),
                author.getWikipediaLink(),
                author.getBooks() != null
                        ? author.getBooks().stream()
                        .map(bookWithoutAuthorMapper::toDTO)
                        .collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }

    public AuthorEntity toEntity(AuthorCreateRequest request, CountryEntity country, GenderEntity gender) {
        if (request == null) return null;

        AuthorEntity entity = new AuthorEntity();
        entity.setFirstName(request.getFirstName());
        entity.setName(request.getName());
        entity.setBirthDate(request.getBirthDate());
        entity.setDeathDate(request.getDeathDate());
        entity.setWikipediaLink(request.getWikipediaLink());
        entity.setCountry(country);
        entity.setGender(gender);
        multilingualMapperUtil.applyMultilingualDescription(request.getDescription(), entity);

        return entity;
    }

    public void updateEntity(AuthorEntity entity, AuthorCreateRequest request, CountryEntity country, GenderEntity gender) {
        if (entity == null || request == null) return;

        entity.setFirstName(request.getFirstName());
        entity.setName(request.getName());
        entity.setBirthDate(request.getBirthDate());
        entity.setDeathDate(request.getDeathDate());
        entity.setWikipediaLink(request.getWikipediaLink());
        entity.setCountry(country);
        entity.setGender(gender);
        multilingualMapperUtil.applyMultilingualDescription(request.getDescription(), entity);
    }
}
