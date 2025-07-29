package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.BookCreateRequest;
import com.tgourouza.library_backend.dto.BookDTO;
import com.tgourouza.library_backend.entity.*;
import com.tgourouza.library_backend.mapper.BookMapper;
import com.tgourouza.library_backend.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final LanguageRepository languageRepository;
    private final LiteraryGenreRepository genreRepository;
    private final LiteraryMovementRepository movementRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository repository, AuthorRepository authorRepository, CategoryRepository categoryRepository, LanguageRepository languageRepository, LiteraryGenreRepository genreRepository, LiteraryMovementRepository movementRepository, BookMapper bookMapper) {
        this.bookRepository = repository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.languageRepository = languageRepository;
        this.genreRepository = genreRepository;
        this.movementRepository = movementRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookDTO createBook(BookCreateRequest request) {
        AuthorEntity author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Auteur introuvable"));

        LanguageEntity language = getOptional(languageRepository, request.getLanguageId());
        LiteraryMovementEntity movement = getOptional(movementRepository, request.getLiteraryMovementId());
        LiteraryGenreEntity genre = getOptional(genreRepository, request.getLiteraryGenreId());
        CategoryEntity category = getOptional(categoryRepository, request.getCategoryId());

        BookEntity entity = bookMapper.toEntity(request, author, language, movement, genre, category);

        return bookMapper.toDTO(bookRepository.save(entity));
    }

    private <T> T getOptional(JpaRepository<T, UUID> repository, UUID id) {
        return id != null ? repository.findById(id).orElse(null) : null;
    }
}
