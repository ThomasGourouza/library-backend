package com.tgourouza.library_backend.mapper;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.dto.TimePlaceTranslated;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtPublication;

@Component
public class BookMapper {

    private final MultilingualMapper multilingualMapper;

    public BookMapper(
            MultilingualMapper multilingualMapper) {
        this.multilingualMapper = multilingualMapper;
    }

    public BookDTO toDTO(BookEntity book) {
        if (book == null)
            return null;
        AuthorDTO authorDto = toDTOWithoutBooks(book.getAuthor());
        return new BookDTO(
                book.getId(),
                book.getOriginalTitle(),
                book.getLanguage(),
                multilingualMapper.toMultilingualTitle(book),
                authorDto,
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
                book.getFavorite());
    }

    // public void updateEntity(
    // BookEntity book,
    // BookCreateRequest request,
    // AuthorEntity author,
    // String language,
    // String tags,
    // Status status
    // ) {
    // if (request == null || book == null) return;
    // book.setOriginalTitle(request.getOriginalTitle());
    // book.setAuthor(author);
    // book.setPublicationDate(request.getPublicationDate());
    // book.setLanguage(language);
    // book.setTags(tags);
    // book.setStatus(status);
    // book.setWikipediaLink(request.getWikipediaLink());
    // book.setFavorite(request.getFavorite());
    // book.setPersonalNotes(request.getPersonalNotes());
    // multilingualMapper.applyMultilingualTitle(request.getTitle(), book);
    // multilingualMapper.applyMultilingualDescription(request.getDescription(),
    // book);
    // }

    private AuthorDTO toDTOWithoutBooks(AuthorEntity author) {
        if (author == null)
            return null;
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
                        multilingualMapper.toMultilingualBirthCountry(author)), // TimePlaceTranslated birth;
                new TimePlaceTranslated(
                        author.getDeathDate(),
                        author.getDeathCity(),
                        multilingualMapper.toMultilingualDeathCountry(author)), // TimePlaceTranslated death;
                multilingualMapper.toMultilingualListCitizenships(author), // MultilingualList citizenships;
                multilingualMapper.toMultilingualListOccupations(author), // MultilingualList occupations;
                multilingualMapper.toMultilingualListLanguages(author), // MultilingualList languages;
                multilingualMapper.toMultilingualWikipediaLink(author), // Multilingual wikipediaLink;
                null // List<BookDTO> books;
        );
    }
}
