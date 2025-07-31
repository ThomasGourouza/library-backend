package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.author.AuthorDate;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.entity.constant.CountryEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    private final BookMapper bookMapper;
    private final MultilingualMapperUtil multilingualMapperUtil;

    public AuthorMapper(BookMapper bookMapper, MultilingualMapperUtil multilingualUtil) {
        this.bookMapper = bookMapper;
        this.multilingualMapperUtil = multilingualUtil;
    }

    public AuthorDTO toDTO(AuthorEntity author) {
        if (author == null) return null;

        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getCountry() != null ? author.getCountry().getName() : null,
                new AuthorDate(author.getBirthDate(), author.getDeathDate()),
                multilingualMapperUtil.toMultilingualDescription(author),
                author.getWikipediaLink(),
                mapBooksWithoutAuthor(author.getBooks())
        );
    }

    public AuthorEntity toEntity(AuthorCreateRequest request) {
        if (request == null) return null;

        AuthorEntity author = new AuthorEntity();
        author.setName(request.getName());
        author.setCountry(new CountryEntity(request.getCountry()));
        if (request.getDate() != null) {
            author.setBirthDate(request.getDate().getBirth());
            author.setDeathDate(request.getDate().getDeath());
        }
        multilingualMapperUtil.applyMultilingualDescription(request.getDescription(), author);
        author.setWikipediaLink(request.getWikipediaLink());
        return author;
    }

    public void updateEntity(AuthorEntity entity, AuthorCreateRequest request) {
        if (request == null || entity == null) return;

        entity.setName(request.getName());
        entity.setCountry(new CountryEntity(request.getCountry()));
        if (request.getDate() != null) {
            entity.setBirthDate(request.getDate().getBirth());
            entity.setDeathDate(request.getDate().getDeath());
        }
        multilingualMapperUtil.applyMultilingualDescription(request.getDescription(), entity);
        entity.setWikipediaLink(request.getWikipediaLink());
    }

    private List<BookDTO> mapBooksWithoutAuthor(List<BookEntity> books) {
        if (books == null) return Collections.emptyList();
        return books.stream()
                .map(book -> {
                    BookDTO dto = bookMapper.toDTO(book);
                    dto.setAuthor(null);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
