package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.BookCreateRequest;
import com.tgourouza.library_backend.dto.BookDTO;
import com.tgourouza.library_backend.entity.*;
import com.tgourouza.library_backend.exception.AuthorNotFoundException;
import com.tgourouza.library_backend.exception.BookNotFoundException;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.exception.InvalidBookDataException;
import com.tgourouza.library_backend.mapper.BookMapper;
import com.tgourouza.library_backend.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final StatusRepository statusRepository;
    private final LanguageRepository languageRepository;
    private final LiteraryGenreRepository genreRepository;
    private final LiteraryMovementRepository movementRepository;
    private final BookMapper bookMapper;

    public BookService(
            BookRepository repository,
            AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            StatusRepository statusRepository,
            LanguageRepository languageRepository,
            LiteraryGenreRepository genreRepository,
            LiteraryMovementRepository movementRepository,
            BookMapper bookMapper
    ) {
        this.bookRepository = repository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.statusRepository = statusRepository;
        this.languageRepository = languageRepository;
        this.genreRepository = genreRepository;
        this.movementRepository = movementRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public BookDTO getBookById(UUID id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookDTO createBook(BookCreateRequest request) {
        BookEntity entity = bookMapper.toEntity(
                request,
                findByIdOrThrow(authorRepository, request.getAuthorId(), () -> new AuthorNotFoundException(request.getAuthorId())),
                findByIdOrThrow(languageRepository, request.getLanguageId(), () -> new DataNotFoundException("Language not found")),
                findByIdOrThrow(movementRepository, request.getLiteraryMovementId(), () -> new DataNotFoundException("Literary movement not found")),
                findByIdOrThrow(genreRepository, request.getLiteraryGenreId(), () -> new DataNotFoundException("Literary genre not found")),
                findByIdOrThrow(categoryRepository, request.getCategoryId(), () -> new DataNotFoundException("Category not found")),
                findByIdOrThrow(statusRepository, request.getStatusId(), () -> new DataNotFoundException("Status not found"))
        );

        return bookMapper.toDTO(bookRepository.save(entity));
    }

    public BookDTO updateBook(UUID id, BookCreateRequest request) {
        BookEntity existing = findByIdOrThrow(bookRepository, id, () -> new BookNotFoundException(id));

        existing.setOriginalTitle(request.getOriginalTitle());
        existing.setFrenchTitle(request.getFrenchTitle());
        existing.setPublicationDate(request.getPublicationDate());
        existing.setPopularityEurope(request.getPopularityEurope());
        existing.setAuthor(
                findByIdOrThrow(authorRepository, request.getAuthorId(), () -> new AuthorNotFoundException(request.getAuthorId()))
        );
        existing.setLanguage(
                findByIdOrThrow(languageRepository, request.getLanguageId(), () -> new DataNotFoundException("Language not found"))
        );
        existing.setLiteraryMovement(
                findByIdOrThrow(movementRepository, request.getLiteraryMovementId(), () -> new DataNotFoundException("Literary movement not found"))
        );
        existing.setLiteraryGenre(
                findByIdOrThrow(genreRepository, request.getLiteraryGenreId(), () -> new DataNotFoundException("Literary genre not found"))
        );
        existing.setCategory(
                findByIdOrThrow(categoryRepository, request.getCategoryId(), () -> new DataNotFoundException("Category not found"))
        );
        existing.setStatus(
                findByIdOrThrow(statusRepository, request.getStatusId(), () -> new DataNotFoundException("Status not found"))
        );

        return bookMapper.toDTO(bookRepository.save(existing));
    }

    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    public BookDTO updatePopularity(UUID id, Integer popularityEurope) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        if (popularityEurope == null || popularityEurope < 0 || popularityEurope > 100) {
            throw new InvalidBookDataException("Popularity must be between 0 and 100.");
        }

        book.setPopularityEurope(popularityEurope);
        return bookMapper.toDTO(bookRepository.save(book));
    }

    private <T> T findByIdOrThrow(JpaRepository<T, UUID> repository, UUID id, Supplier<RuntimeException> exceptionSupplier) {
        return repository.findById(id).orElseThrow(exceptionSupplier);
    }
}
