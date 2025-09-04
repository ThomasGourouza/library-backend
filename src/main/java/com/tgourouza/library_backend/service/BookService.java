package com.tgourouza.library_backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.dto.book.BookDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.BookMapper;
import com.tgourouza.library_backend.repository.AuthorRepository;
import com.tgourouza.library_backend.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookService(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            BookMapper bookMapper
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> getAllBooks(DataLanguage dataLanguage) {
        return bookRepository.findAll().stream()
                .map(book -> bookMapper.toDTO(book, dataLanguage))
                .toList();
    }

    public BookDTO getBookById(UUID bookId, DataLanguage dataLanguage) {
        return bookMapper.toDTO(getBookEntity(bookId), dataLanguage);
    }

    public BookDTO createBook(BookCreateRequest request, DataLanguage dataLanguage) {
        return updateEntityAndSave(request, new BookEntity(), dataLanguage);
    }

    public void deleteBook(UUID bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new DataNotFoundException("Book", String.valueOf(bookId));
        }
        bookRepository.deleteById(bookId);
    }

    public BookDTO updateStatus(UUID bookId, String status, DataLanguage dataLanguage) {
        BookEntity book = getBookEntity(bookId);
        book.setStatusEnglish(status);
        return bookMapper.toDTO(bookRepository.save(book), dataLanguage);
    }

    public BookDTO updateFavorite(UUID bookId, Boolean favorite, DataLanguage dataLanguage) {
        BookEntity book = getBookEntity(bookId);
        book.setFavorite(favorite);
        return bookMapper.toDTO(bookRepository.save(book), dataLanguage);
    }

    public BookDTO updatePersonalNotes(UUID bookId, String notes, DataLanguage dataLanguage) {
        BookEntity book = getBookEntity(bookId);
        book.setPersonalNotes(notes);
        return bookMapper.toDTO(bookRepository.save(book), dataLanguage);
    }

    private BookDTO updateEntityAndSave(BookCreateRequest request, BookEntity bookEntity, DataLanguage dataLanguage) {
        bookMapper.updateEntity(
                bookEntity,
                request,
                getAuthorEntity(request.authorId())
        );
        return bookMapper.toDTO(bookRepository.save(bookEntity), dataLanguage);
    }

    private BookEntity getBookEntity(UUID bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book", String.valueOf(bookId)));
    }

    private AuthorEntity getAuthorEntity(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(authorId)));
    }
}
