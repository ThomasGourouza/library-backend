package com.tgourouza.library_backend.util;

import com.tgourouza.library_backend.entity.BookEntity;
import java.time.Period;

public class BookUtils {

    private BookUtils() {}

    public static Integer calculateAuthorAgeAtPublication(BookEntity book) {
        if (book == null || book.getAuthor() == null
                || book.getAuthor().getBirthDate() == null
                || book.getPublicationDate() == null) {
            return null;
        }
        return Period.between(book.getAuthor().getBirthDate(), book.getPublicationDate()).getYears();
    }
}
