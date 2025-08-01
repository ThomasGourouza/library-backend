package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.author.AuthorDate;
import com.tgourouza.library_backend.entity.AuthorEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorWithoutBooksMapper {

    private final MultilingualMapper multilingualMapper;

    public AuthorWithoutBooksMapper(MultilingualMapper multilingualMapper) {
        this.multilingualMapper = multilingualMapper;
    }

    public AuthorDTO toDTO(AuthorEntity author) {
        if (author == null) return null;
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getCountry() != null ? author.getCountry().getName() : null,
                new AuthorDate(author.getBirthDate(), author.getDeathDate()),
                multilingualMapper.toMultilingualDescription(author),
                author.getWikipediaLink(),
                null
        );
    }
}
