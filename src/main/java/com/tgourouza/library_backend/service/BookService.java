package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookEntity> getAllBooks() {
        return repository.findAll();
    }
}
