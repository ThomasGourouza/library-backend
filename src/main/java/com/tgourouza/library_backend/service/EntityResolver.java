package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.constant.*;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.entity.constant.*;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.repository.*;
import com.tgourouza.library_backend.repository.constant.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EntityResolver {

    private final LanguageRepository languageRepository;
    private final TypeRepository typeRepository;
    private final CategoryRepository categoryRepository;
    private final AudienceRepository audienceRepository;
    private final StatusRepository statusRepository;
    private final CountryRepository countryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public EntityResolver(
            LanguageRepository languageRepository,
            TypeRepository typeRepository,
            CategoryRepository categoryRepository,
            AudienceRepository audienceRepository,
            StatusRepository statusRepository,
            CountryRepository countryRepository,
            AuthorRepository authorRepository, BookRepository bookRepository
    ) {
        this.languageRepository = languageRepository;
        this.typeRepository = typeRepository;
        this.categoryRepository = categoryRepository;
        this.audienceRepository = audienceRepository;
        this.statusRepository = statusRepository;
        this.countryRepository = countryRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public LanguageEntity getLanguageEntity(Language language) {
        return languageRepository.findByName(language)
                .orElseThrow(() -> new DataNotFoundException("Language", language.name()));
    }

    public TypeEntity getTypeEntity(Type type) {
        return typeRepository.findByName(type)
                .orElseThrow(() -> new DataNotFoundException("Type", type.name()));
    }

    public CategoryEntity getCategoryEntity(Category category) {
        return categoryRepository.findByName(category)
                .orElseThrow(() -> new DataNotFoundException("Category", category.name()));
    }

    public AudienceEntity getAudienceEntity(Audience audience) {
        return audienceRepository.findByName(audience)
                .orElseThrow(() -> new DataNotFoundException("Audience", audience.name()));
    }

    public StatusEntity getStatusEntity(Status status) {
        return statusRepository.findByName(status)
                .orElseThrow(() -> new DataNotFoundException("Status", status.name()));
    }

    public CountryEntity getCountryEntity(Country country) {
        return countryRepository.findByName(country)
                .orElseThrow(() -> new DataNotFoundException("Country", country.name()));
    }

    public AuthorEntity getAuthorEntity(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(authorId)));
    }

    public BookEntity getBookEntity(UUID bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book", String.valueOf(bookId)));
    }
}
