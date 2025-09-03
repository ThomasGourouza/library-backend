package com.tgourouza.library_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgourouza.library_backend.constant.Type;
import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.entity.multilingual.BookTagEntity;
import com.tgourouza.library_backend.entity.multilingual.CityEntity;
import com.tgourouza.library_backend.entity.multilingual.CountryEntity;
import com.tgourouza.library_backend.entity.multilingual.LanguageEntity;
import com.tgourouza.library_backend.entity.multilingual.OccupationEntity;
import com.tgourouza.library_backend.entity.multilingual.StatusEntity;
import com.tgourouza.library_backend.repository.multilingual.BookTagRepository;
import com.tgourouza.library_backend.repository.multilingual.CityRepository;
import com.tgourouza.library_backend.repository.multilingual.CountryRepository;
import com.tgourouza.library_backend.repository.multilingual.LanguageRepository;
import com.tgourouza.library_backend.repository.multilingual.OccupationRepository;
import com.tgourouza.library_backend.repository.multilingual.StatusRepository;

@Service
@Transactional
public class MultilingualService {

    private static final Set<String> SUPPORTED = Set.of("en", "fr", "es", "it", "de", "pt", "ru", "ja");

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;
    private final OccupationRepository occupationRepository;
    private final StatusRepository statusRepository;
    private final BookTagRepository bookTagRepository;

    public MultilingualService(CityRepository cityRepository, CountryRepository countryRepository,
            LanguageRepository languageRepository, OccupationRepository occupationRepository,
            StatusRepository statusRepository, BookTagRepository bookTagRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.languageRepository = languageRepository;
        this.occupationRepository = occupationRepository;
        this.statusRepository = statusRepository;
        this.bookTagRepository = bookTagRepository;
    }

    public Multilingual create(Type type, Multilingual request) {
        return switch (type) {
            case CITY -> cityRepository.save(CityEntity.of(request)).toDto();
            case BOOK_TAG -> bookTagRepository.save(BookTagEntity.of(request)).toDto();
            case OCCUPATION -> occupationRepository.save(OccupationEntity.of(request)).toDto();
            case STATUS -> statusRepository.save(StatusEntity.of(request)).toDto();
            case LANGUAGE -> languageRepository.save(LanguageEntity.of(request)).toDto();
            case COUNTRY -> countryRepository.save(CountryEntity.of(request)).toDto();
        };
    }

    @Transactional(readOnly = true)
    public List<Multilingual> getAll(Type type) {
        return switch (type) {
            case CITY -> cityRepository.findAll().stream().map(CityEntity::toDto).toList();
            case BOOK_TAG -> bookTagRepository.findAll().stream().map(BookTagEntity::toDto).toList();
            case OCCUPATION -> occupationRepository.findAll().stream().map(OccupationEntity::toDto).toList();
            case STATUS -> statusRepository.findAll().stream().map(StatusEntity::toDto).toList();
            case LANGUAGE -> languageRepository.findAll().stream().map(LanguageEntity::toDto).toList();
            case COUNTRY -> countryRepository.findAll().stream().map(CountryEntity::toDto).toList();
        };
    }

    public Optional<Multilingual> findByEnglishIgnoreCase(Type type, String english) {
        checkValue(english);
        String value = english.trim();
        return switch (type) {
            case CITY -> cityRepository.findByEnglishIgnoreCase(value).map(CityEntity::toDto);
            case BOOK_TAG -> bookTagRepository.findByEnglishIgnoreCase(value).map(BookTagEntity::toDto);
            case OCCUPATION -> occupationRepository.findByEnglishIgnoreCase(value).map(OccupationEntity::toDto);
            case STATUS -> statusRepository.findByEnglishIgnoreCase(value).map(StatusEntity::toDto);
            case LANGUAGE -> languageRepository.findByEnglishIgnoreCase(value).map(LanguageEntity::toDto);
            case COUNTRY -> countryRepository.findByEnglishIgnoreCase(value).map(CountryEntity::toDto);
        };
    }

    public Optional<String> translateFromEnglish(Type type, String english, String isoTargetLanguage) {
        checkValues(english, isoTargetLanguage);
        String value = english.trim();
        return switch (type) {
            case CITY -> cityRepository.translate(value, isoTargetLanguage);
            case BOOK_TAG -> bookTagRepository.translate(value, isoTargetLanguage);
            case OCCUPATION -> occupationRepository.translate(value, isoTargetLanguage);
            case STATUS -> statusRepository.translate(value, isoTargetLanguage);
            case LANGUAGE -> languageRepository.translate(value, isoTargetLanguage);
            case COUNTRY -> countryRepository.translate(value, isoTargetLanguage);
        };
    }

    @Transactional
    public long deleteByEnglishIgnoreCase(Type type, String english) {
        checkValue(english);
        String value = english.trim();
        return switch (type) {
            case CITY -> cityRepository.deleteByEnglishIgnoreCase(value);
            case BOOK_TAG -> bookTagRepository.deleteByEnglishIgnoreCase(value);
            case OCCUPATION -> occupationRepository.deleteByEnglishIgnoreCase(value);
            case STATUS -> statusRepository.deleteByEnglishIgnoreCase(value);
            case LANGUAGE -> languageRepository.deleteByEnglishIgnoreCase(value);
            case COUNTRY -> countryRepository.deleteByEnglishIgnoreCase(value);
        };
    }

    private void checkValue(String english) {
        if (english == null || english.isBlank()) {
            throw new IllegalArgumentException("english value must not be blank");
        }
    }

    private void checkValues(String english, String isoTargetLanguage) {
        if (english == null || english.isBlank() || isoTargetLanguage == null
                || isoTargetLanguage.isBlank()) {
            throw new IllegalArgumentException("english value and isoTargetLanguage must not be blank");
        }
        if (!SUPPORTED.contains(isoTargetLanguage)) {
            throw new IllegalArgumentException("Unsupported targetLanguage: " + isoTargetLanguage);
        }
    }
}
