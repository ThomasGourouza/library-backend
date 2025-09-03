package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.utils.calculateAuthorAgeAtDeathOrCurrent;
import static com.tgourouza.library_backend.util.utils.toList;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.dto.TimePlace;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;

@Component
public class AuthorMapperHelper {

    public AuthorMapperHelper() {
    }

    public AuthorDTO toDTO(AuthorEntity author, List<BookDTO> books) {
        if (author == null) {
            return null;
        }
        return new AuthorDTO(
                author.getId(), // UUID id;
                author.getOLKey(), // String oLKey;
                author.getName(), // String name;
                author.getPictureUrl(), // String pictureUrl;
                author.getShortDescriptionEnglish(), // TODO: Implement translation
                author.getDescriptionEnglish(), // TODO: Implement translation
                new TimePlace(
                        author.getBirthDate(),
                        author.getBirthCityEnglish(),
                        author.getBirthCountryEnglish()), // TimePlace birth; TODO: Country
                new TimePlace(
                        author.getDeathDate(),
                        author.getDeathCityEnglish(),
                        author.getDeathCountryEnglish()), // TimePlace death; TODO: Country
                calculateAuthorAgeAtDeathOrCurrent(author), // Integer ageAtDeathOrCurrent;
                toList(author.getCitizenshipsEnglish()), // List<String> citizenships; TODO: List<Country>
                toList(author.getOccupationsEnglish()), // List<String> occupations; TODO: List<AuthorTag>
                toList(author.getLanguagesEnglish()), // List<String> languages; TODO: List<DataLanguage>
                author.getWikipediaLinkEnglish(), // TODO: Implement translation
                books, // List<BookDTO> books;
                DataLanguage.ENGLISH.getValue() // TODO: Implement language
        );
    }
}
