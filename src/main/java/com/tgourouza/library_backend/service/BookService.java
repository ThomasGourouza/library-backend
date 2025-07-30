package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.BookCreateRequest;
import com.tgourouza.library_backend.dto.BookDTO;
import com.tgourouza.library_backend.entity.*;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.BookMapper;
import com.tgourouza.library_backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
                authorRepository.findById(request.getAuthorId())
                        .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(request.getAuthorId()))),
                languageRepository.findById(request.getLanguageId())
                        .orElseThrow(() -> new DataNotFoundException("Language", String.valueOf(request.getLanguageId()))),
                movementRepository.findById(request.getLiteraryMovementId())
                        .orElseThrow(() -> new DataNotFoundException("Literary movement", String.valueOf(request.getLiteraryMovementId()))),
                genreRepository.findById(request.getLiteraryGenreId())
                        .orElseThrow(() -> new DataNotFoundException("Literary genre", String.valueOf(request.getLiteraryGenreId()))),
                categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new DataNotFoundException("Category", String.valueOf(request.getCategoryId()))),
                statusRepository.findById(request.getStatusId())
                        .orElseThrow(() -> new DataNotFoundException("Status", String.valueOf(request.getStatusId())))
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
                authorRepository.findById(request.getAuthorId())
                        .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(request.getAuthorId())))
        );
        existing.setLanguage(
                languageRepository.findById(request.getLanguageId())
                        .orElseThrow(() -> new DataNotFoundException("Language", String.valueOf(request.getLanguageId())))
        );
        existing.setLiteraryMovement(
                movementRepository.findById(request.getLiteraryMovementId())
                        .orElseThrow(() -> new DataNotFoundException("Literary movement", String.valueOf(request.getLiteraryMovementId())))
        );
        existing.setLiteraryGenre(
                genreRepository.findById(request.getLiteraryGenreId())
                        .orElseThrow(() -> new DataNotFoundException("Literary genre", String.valueOf(request.getLiteraryGenreId())))
        );
        existing.setCategory(
                categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new DataNotFoundException("Category", String.valueOf(request.getCategoryId())))
        );
        existing.setStatus(
                statusRepository.findById(request.getStatusId())
                        .orElseThrow(() -> new DataNotFoundException("Status", String.valueOf(request.getStatusId())))
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

    public BookDTO updateFavorite(UUID bookId, Boolean favorite) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book", String.valueOf(bookId)));
        book.setFavorite(favorite);
        return bookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO updatePersonalNotes(UUID bookId, String notes) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book", String.valueOf(bookId)));
        book.setPersonalNotes(notes);
        return bookMapper.toDTO(bookRepository.save(book));
    }


}
