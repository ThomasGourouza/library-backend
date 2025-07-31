package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.author.AuthorWithoutBooksDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorWithoutBooksMapper {

    private final MultilingualMapperUtil multilingualMapperUtil;
    private final AuthorIdentityMapper identityMapper;

    public AuthorWithoutBooksMapper(MultilingualMapperUtil multilingualMapperUtil, AuthorIdentityMapper identityMapper) {
        this.multilingualMapperUtil = multilingualMapperUtil;
        this.identityMapper = identityMapper;
    }

    public AuthorWithoutBooksDTO toDTO(AuthorEntity author) {
        if (author == null) return null;

        return new AuthorWithoutBooksDTO(
                author.getId(),
                identityMapper.toDTO(author),
                multilingualMapperUtil.toMultilingualDescription(author),
                author.getWikipediaLink()
        );
    }
}
