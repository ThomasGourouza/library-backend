package com.tgourouza.library_backend.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.dto.TimePlaceTranslated;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;

@Component
public class AuthorMapper {

    private final MultilingualMapper multilingualMapper;

    public AuthorMapper(MultilingualMapper multilingualMapper) {
        this.multilingualMapper = multilingualMapper;
    }

    public AuthorDTO toDTO(AuthorEntity author) {
        if (author == null) return null;
        return new AuthorDTO(
            author.getId(), // UUID id;
            author.getOLKey(), // String oLKey;
            author.getName(), // String name;
            author.getPictureUrl(), // String pictureUrl;
            multilingualMapper.toMultilingualShortDescription(author), // Multilingual shortDescription;
            multilingualMapper.toMultilingualDescription(author), // Multilingual description;
            new TimePlaceTranslated(
                author.getBirthDate(),
                author.getBirthCity(),
                multilingualMapper.toMultilingualBirthCountry(author)
            ), // TimePlaceTranslated birth;
            new TimePlaceTranslated(
                author.getDeathDate(),
                author.getDeathCity(),
                multilingualMapper.toMultilingualDeathCountry(author)
            ), // TimePlaceTranslated death;
            multilingualMapper.toMultilingualListCitizenships(author), // MultilingualList citizenships;
            multilingualMapper.toMultilingualListOccupations(author), // MultilingualList occupations;
            multilingualMapper.toMultilingualListLanguages(author), // MultilingualList languages;
            multilingualMapper.toMultilingualWikipediaLink(author), // Multilingual wikipediaLink;
            toDTOsWithoutAuthor(author.getBooks()) // List<BookDTO> books;
        );
    }

    // public void updateEntity(AuthorEntity author, AuthorCreateRequest request, String country) {
    //     if (request == null || author == null) return;
    //     author.setName(request.getName());
    //     author.setCountry(country);
    //     if (request.getDate() != null) {
    //         author.setBirthDate(request.getDate().getBirth());
    //         author.setDeathDate(request.getDate().getDeath());
    //     }
    //     multilingualMapper.applyMultilingualDescription(request.getDescription(), author);
    //     author.setWikipediaLink(request.getWikipediaLink());
    // }

    private List<BookDTO> toDTOsWithoutAuthor(List<BookEntity> books) {
        if (books == null) return Collections.emptyList();
        return books.stream().map(this::toDTOWithoutAuthor).collect(Collectors.toList());
    }

    private BookDTO toDTOWithoutAuthor(BookEntity book) {
        if (book == null) return null;
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                book.getLanguage(),
                multilingualMapper.toMultilingualTitle(book),
                null,
                book.getAuthorOLKey(),
                calculateAuthorAgeAtPublication(book),
                book.getPublicationYear(),
                book.getCoverUrl(),
                book.getNumberOfPages(),
                multilingualMapper.toMultilingualDescription(book),
                multilingualMapper.toMultilingualListTags(book),
                multilingualMapper.toMultilingualWikipediaLink(book),
                book.getPersonalNotes(),
                book.getStatus(),
                book.getFavorite()
        );
    }
}
