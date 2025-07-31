package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.author.AuthorWithoutBooksDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorWithoutBooksMapper {

    private final MultilingualMapperUtil multilingualMapperUtil;

    public AuthorWithoutBooksMapper(MultilingualMapperUtil multilingualMapperUtil) {
        this.multilingualMapperUtil = multilingualMapperUtil;
    }

    public AuthorWithoutBooksDTO toDTO(AuthorEntity author) {
        if (author == null) return null;

        return new AuthorWithoutBooksDTO(
                author.getId(),
                author.getFirstName(),
                author.getName(),
                author.getCountry() != null ? author.getCountry().getName() : null,
                author.getBirthDate(),
                author.getDeathDate(),
                author.getGender() != null ? author.getGender().getName() : null,
                multilingualMapperUtil.toMultilingualDescription(author),
                author.getWikipediaLink()
        );
    }
}
