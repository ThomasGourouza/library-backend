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

    public void updateEntity(AuthorEntity author, AuthorCreateRequest request, CountryEntity country) {
        if (request == null || author == null) return;
        author.setName(request.getName());
        author.setCountry(country);
        if (request.getDate() != null) {
            author.setBirthDate(request.getDate().getBirth());
            author.setDeathDate(request.getDate().getDeath());
        }
        multilingualMapperUtil.applyMultilingualDescription(request.getDescription(), author);
        author.setWikipediaLink(request.getWikipediaLink());
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
