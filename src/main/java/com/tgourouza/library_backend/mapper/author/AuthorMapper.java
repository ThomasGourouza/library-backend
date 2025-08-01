package com.tgourouza.library_backend.mapper.author;

import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.author.AuthorDate;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.constant.CountryEntity;
import com.tgourouza.library_backend.mapper.book.BookWithoutAuthorMapper;
import com.tgourouza.library_backend.mapper.MultilingualMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    private final BookWithoutAuthorMapper bookWithoutAuthorMapper;
    private final MultilingualMapper multilingualMapper;

    public AuthorMapper(BookWithoutAuthorMapper bookWithoutAuthorMapper, MultilingualMapper multilingualMapper) {
        this.bookWithoutAuthorMapper = bookWithoutAuthorMapper;
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
                bookWithoutAuthorMapper.toDTOs(author.getBooks())
        );
    }

    public void updateEntity(AuthorEntity author, AuthorCreateRequest request, CountryEntity country) {
        if (request == null || author == null) return;
        author.setName(request.getName());
        author.setCountry(country);
        if (request.getDate() != null) {
            author.setBirthDate(request.getDate().getBirth());
            author.setDeathDate(request.getDate().getDeath());
        }
        multilingualMapper.applyMultilingualDescription(request.getDescription(), author);
        author.setWikipediaLink(request.getWikipediaLink());
    }
}
