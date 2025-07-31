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

    private final AuthorIdentityMapper identityMapper;
    private final BookWithoutAuthorMapper bookWithoutAuthorMapper;
    private final MultilingualMapperUtil multilingualMapperUtil;

    public AuthorMapper(
            AuthorIdentityMapper identityMapper,
            BookWithoutAuthorMapper bookWithoutAuthorMapper,
            MultilingualMapperUtil multilingualMapperUtil
    ) {
        this.identityMapper = identityMapper;
        this.bookWithoutAuthorMapper = bookWithoutAuthorMapper;
        this.multilingualMapperUtil = multilingualMapperUtil;
    }

    public AuthorDTO toDTO(AuthorEntity author) {
        if (author == null) return null;

        return new AuthorDTO(
                author.getId(),
                identityMapper.toDTO(author),
                multilingualMapperUtil.toMultilingualDescription(author),
                author.getWikipediaLink(),
                author.getBooks() != null
                        ? author.getBooks().stream().map(bookWithoutAuthorMapper::toDTO).collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }

    public AuthorEntity toEntity(AuthorCreateRequest request, CountryEntity country, GenderEntity gender) {
        if (request == null) return null;

        AuthorEntity entity = new AuthorEntity();
        identityMapper.applyToEntity(entity, request.getIdentity(), gender, country);
        multilingualMapperUtil.applyMultilingualDescription(request.getDescription(), entity);
        entity.setWikipediaLink(request.getWikipediaLink());

        return entity;
    }

    public void updateEntity(AuthorEntity entity, AuthorCreateRequest request, CountryEntity country, GenderEntity gender) {
        if (entity == null || request == null) return;

        identityMapper.applyToEntity(entity, request.getIdentity(), gender, country);
        multilingualMapperUtil.applyMultilingualDescription(request.getDescription(), entity);
        entity.setWikipediaLink(request.getWikipediaLink());
    }
}
