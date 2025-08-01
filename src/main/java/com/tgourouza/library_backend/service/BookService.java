package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.*;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.book.BookMapper;
import com.tgourouza.library_backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.tgourouza.library_backend.util.utils.applyDefaultValuesOnBookRequestIfNeeded;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final EntityResolver entityResolver;
    private final BookMapper bookMapper;

    public BookService(
            BookRepository bookRepository,
            EntityResolver entityResolver,
            BookMapper bookMapper
    ) {
        this.bookRepository = bookRepository;
        this.entityResolver = entityResolver;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public BookDTO getBookById(UUID bookId) {
        return bookMapper.toDTO(entityResolver.getBookEntity(bookId));
    }

    public BookDTO createBook(BookCreateRequest request) {
        applyDefaultValuesOnBookRequestIfNeeded(request);
        return updateEntityAndSave(request, new BookEntity());
    }

    public BookDTO updateBook(UUID bookId, BookCreateRequest request) {
        return updateEntityAndSave(request, entityResolver.getBookEntity(bookId));
    }

    public void deleteBook(UUID bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new DataNotFoundException("Book", String.valueOf(bookId));
        }
        bookRepository.deleteById(bookId);
    }

    public BookDTO updateStatus(UUID bookId, Status status) {
        BookEntity book = entityResolver.getBookEntity(bookId);
        book.setStatus(entityResolver.getStatusEntity(status));
        return bookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO updateFavorite(UUID bookId, Boolean favorite) {
        BookEntity book = entityResolver.getBookEntity(bookId);
        book.setFavorite(favorite);
        return bookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO updatePersonalNotes(UUID bookId, String notes) {
        BookEntity book = entityResolver.getBookEntity(bookId);
        book.setPersonalNotes(notes);
        return bookMapper.toDTO(bookRepository.save(book));
    }

    private BookDTO updateEntityAndSave(BookCreateRequest request, BookEntity book) {
        bookMapper.updateEntity(
                book,
                request,
                entityResolver.getAuthorEntity(request.getAuthorId()),
                entityResolver.getLanguageEntity(request.getLanguage()),
                entityResolver.getTypeEntity(request.getType()),
                entityResolver.getCategoryEntity(request.getCategory()),
                entityResolver.getAudienceEntity(request.getAudience()),
                entityResolver.getStatusEntity(request.getStatus())
        );
        return bookMapper.toDTO(bookRepository.save(book));
    }
}
