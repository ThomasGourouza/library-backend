package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.BookCreateRequest;
import com.tgourouza.library_backend.dto.BookDTO;
import com.tgourouza.library_backend.entity.*;
import com.tgourouza.library_backend.exception.DataNotFoundException;
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
                .orElseThrow(() -> new DataNotFoundException("Book", String.valueOf(id)));
    }

    public BookDTO createBook(BookCreateRequest request) {
        BookEntity entity = bookMapper.toEntity(
                request,
                authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(request.getAuthorId()))),
                findByIdOrThrow(languageRepository, request.getLanguageId(), () -> new DataNotFoundException("Language", String.valueOf(request.getLanguageId()))),
                findByIdOrThrow(movementRepository, request.getLiteraryMovementId(), () -> new DataNotFoundException("Literary movement", String.valueOf(request.getLiteraryMovementId()))),
                findByIdOrThrow(genreRepository, request.getLiteraryGenreId(), () -> new DataNotFoundException("Literary genre", String.valueOf(request.getLiteraryGenreId()))),
                findByIdOrThrow(categoryRepository, request.getCategoryId(), () -> new DataNotFoundException("Category", String.valueOf(request.getCategoryId()))),
                findByIdOrThrow(statusRepository, request.getStatusId(), () -> new DataNotFoundException("Status", String.valueOf(request.getStatusId())))
        );

        return bookMapper.toDTO(bookRepository.save(entity));
    }

    public BookDTO updateBook(UUID id, BookCreateRequest request) {
        BookEntity existing = bookRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Book", String.valueOf(id)));

        existing.setOriginalTitle(request.getOriginalTitle());
        existing.setFrenchTitle(request.getFrenchTitle());
        existing.setPublicationDate(request.getPublicationDate());
        existing.setPopularityEurope(request.getPopularityEurope());
        existing.setAuthor(
                authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(request.getAuthorId())))
        );
        existing.setLanguage(
                findByIdOrThrow(languageRepository, request.getLanguageId(), () -> new DataNotFoundException("Language", String.valueOf(request.getLanguageId())))
        );
        existing.setLiteraryMovement(
                findByIdOrThrow(movementRepository, request.getLiteraryMovementId(), () -> new DataNotFoundException("Literary movement", String.valueOf(request.getLiteraryMovementId())))
        );
        existing.setLiteraryGenre(
                findByIdOrThrow(genreRepository, request.getLiteraryGenreId(), () -> new DataNotFoundException("Literary genre", String.valueOf(request.getLiteraryGenreId())))
        );
        existing.setCategory(
                findByIdOrThrow(categoryRepository, request.getCategoryId(), () -> new DataNotFoundException("Category", String.valueOf(request.getCategoryId())))
        );
        existing.setStatus(
                findByIdOrThrow(statusRepository, request.getStatusId(), () -> new DataNotFoundException("Status", String.valueOf(request.getStatusId())))
        );

        return bookMapper.toDTO(bookRepository.save(existing));
    }

    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new DataNotFoundException("Book", String.valueOf(id));
        }
        bookRepository.deleteById(id);
    }

    public BookDTO updateStatus(UUID bookId, Long statusId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book", String.valueOf(bookId)));

        StatusEntity status = statusRepository.findById(statusId)
                .orElseThrow(() -> new DataNotFoundException("Status", String.valueOf(statusId)));

        book.setStatus(status);
        return bookMapper.toDTO(bookRepository.save(book));
    }


    private <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, Supplier<RuntimeException> exceptionSupplier) {
        return repository.findById(id).orElseThrow(exceptionSupplier);
    }
}
