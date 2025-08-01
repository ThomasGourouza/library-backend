package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.*;
import com.tgourouza.library_backend.entity.constant.*;
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
    private final TypeRepository typeRepository;
    private final AudienceRepository audienceRepository;
    private final BookMapper bookMapper;

    public BookService(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            StatusRepository statusRepository,
            LanguageRepository languageRepository,
            TypeRepository typeRepository,
            AudienceRepository audienceRepository,
            BookMapper bookMapper
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.statusRepository = statusRepository;
        this.languageRepository = languageRepository;
        this.typeRepository = typeRepository;
        this.audienceRepository = audienceRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public BookDTO getBookById(UUID bookId) {
        BookEntity book = findBook(bookId);
        return bookMapper.toDTO(book);
    }

    public BookDTO createBook(BookCreateRequest request) {
        BookEntity book = new BookEntity();
        return updateEntityAndSave(request, book);
    }

    public BookDTO updateBook(UUID bookId, BookCreateRequest request) {
        BookEntity book = findBook(bookId);
        return updateEntityAndSave(request, book);
    }

    public void deleteBook(UUID bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new DataNotFoundException("Book", String.valueOf(bookId));
        }
        bookRepository.deleteById(bookId);
    }

    public BookDTO updateStatus(UUID bookId, Long statusId) {
        BookEntity book = findBook(bookId);
        StatusEntity status = findStatus(statusId);
        book.setStatus(status);
        return bookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO updateFavorite(UUID bookId, Boolean favorite) {
        BookEntity book = findBook(bookId);
        book.setFavorite(favorite);
        return bookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO updatePersonalNotes(UUID bookId, String notes) {
        BookEntity book = findBook(bookId);
        book.setPersonalNotes(notes);
        return bookMapper.toDTO(bookRepository.save(book));
    }

    private BookEntity findBook(UUID bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book", String.valueOf(bookId)));
    }

    private StatusEntity findStatus(Long statusId) {
        return statusRepository.findById(statusId)
                .orElseThrow(() -> new DataNotFoundException("Status", String.valueOf(statusId)));
    }

    private BookDTO updateEntityAndSave(BookCreateRequest request, BookEntity book) {
        bookMapper.updateEntity(
                book,
                request,
                authorRepository.findById(request.getAuthorId())
                        .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(request.getAuthorId()))),
                languageRepository.findById(request.getLanguageId())
                        .orElseThrow(() -> new DataNotFoundException("Language", String.valueOf(request.getLanguageId()))),
                typeRepository.findById(request.getTypeId())
                        .orElseThrow(() -> new DataNotFoundException("Type", String.valueOf(request.getTypeId()))),
                categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new DataNotFoundException("Category", String.valueOf(request.getCategoryId()))),
                audienceRepository.findById(request.getAudienceId())
                        .orElseThrow(() -> new DataNotFoundException("Audience", String.valueOf(request.getAudienceId()))),
                findStatus(request.getStatusId())
        );
        return bookMapper.toDTO(bookRepository.save(book));
    }
}
