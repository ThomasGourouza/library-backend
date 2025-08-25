package com.tgourouza.library_backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tgourouza.library_backend.constant.Status;
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
    private final EnumResolver enumResolver;

    public BookService(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            BookMapper bookMapper,
            EnumResolver enumResolver
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
        this.enumResolver = enumResolver;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public BookDTO getBookById(UUID bookId) {
        return bookMapper.toDTO(getBookEntity(bookId));
    }

    public BookDTO createBook(BookCreateRequest request) {
        return updateEntityAndSave(request, new BookEntity());
    }

    public BookDTO updateBook(UUID bookId, BookCreateRequest request) {
        return updateEntityAndSave(request, getBookEntity(bookId));
    }

    public void deleteBook(UUID bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new DataNotFoundException("Book", String.valueOf(bookId));
        }
        bookRepository.deleteById(bookId);
    }

    // public BookDTO updateStatus(UUID bookId, String status) {
    //     BookEntity book = getBookEntity(bookId);
    //     book.setStatus(enumResolver.getStatus(status));
    //     return bookMapper.toDTO(bookRepository.save(book));
    // }

    // public BookDTO updateFavorite(UUID bookId, Boolean favorite) {
    //     BookEntity book = getBookEntity(bookId);
    //     book.setFavorite(favorite);
    //     return bookMapper.toDTO(bookRepository.save(book));
    // }

    // public BookDTO updatePersonalNotes(UUID bookId, String notes) {
    //     BookEntity book = getBookEntity(bookId);
    //     book.setPersonalNotes(notes);
    //     return bookMapper.toDTO(bookRepository.save(book));
    // }

    private BookDTO updateEntityAndSave(BookCreateRequest request, BookEntity bookEntity) {
        if (request.getStatus() == null) {
            request.setStatus(Status.UNREAD);
        }
        if (request.getFavorite() == null) {
            request.setFavorite(false);
        }
        bookMapper.updateEntity(
                bookEntity,
                request,
                getAuthorEntity(request.getAuthorId())
        );
        return bookMapper.toDTO(bookRepository.save(bookEntity));
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
