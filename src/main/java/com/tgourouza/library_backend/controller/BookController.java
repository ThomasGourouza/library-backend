package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.BookDTO;
import com.tgourouza.library_backend.dto.BookCreateRequest;
import com.tgourouza.library_backend.entity.BookEntity;
import com.tgourouza.library_backend.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookEntity> getBooks() {
        return service.getAllBooks();
    }

//    @PostMapping
//    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookCreateRequest request) {
//        // ton code ici
//    }

}
