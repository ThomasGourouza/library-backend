package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.BookCreateRequest;
import com.tgourouza.library_backend.dto.BookDTO;
import com.tgourouza.library_backend.entity.*;
import com.tgourouza.library_backend.mapper.BookMapper;
import com.tgourouza.library_backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
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

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public BookDTO getBookById(UUID id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    public BookDTO createBook(BookCreateRequest request) {
        BookEntity entity = bookMapper.toEntity(
                request,
                authorRepository.findById(request.getAuthorId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author not found")),
                languageRepository.findById(request.getLanguageId()).orElseThrow(),
                movementRepository.findById(request.getLiteraryMovementId()).orElseThrow(),
                genreRepository.findById(request.getLiteraryGenreId()).orElseThrow(),
                categoryRepository.findById(request.getCategoryId()).orElseThrow()
        );

        BookEntity savedEntity = bookRepository.save(entity);
        return bookMapper.toDTO(savedEntity);
    }

    public BookDTO updateBook(
            UUID id,
            BookCreateRequest request
    ) {
        BookEntity existing = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        existing.setOriginalTitle(request.getOriginalTitle());
        existing.setFrenchTitle(request.getFrenchTitle());
        existing.setPublicationDate(request.getPublicationDate());
        existing.setPopularityEurope(request.getPopularityEurope());
        existing.setAuthor(authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author not found")));
        existing.setLanguage(languageRepository.findById(request.getLanguageId()).orElseThrow());
        existing.setLiteraryMovement(movementRepository.findById(request.getLiteraryMovementId()).orElseThrow());
        existing.setLiteraryGenre(genreRepository.findById(request.getLiteraryGenreId()).orElseThrow());
        existing.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow());

        return bookMapper.toDTO(bookRepository.save(existing));
    }

    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
    }

    public BookDTO updatePopularity(UUID id, Integer popularityEurope) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        if (popularityEurope == null || popularityEurope < 0 || popularityEurope > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid popularity value");
        }

        book.setPopularityEurope(popularityEurope);
        return bookMapper.toDTO(bookRepository.save(book));
    }

}
