package com.tgourouza.library_backend.util;

import com.tgourouza.library_backend.constant.*;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.entity.BookEntity;
import java.time.Period;

public class utils {

    private utils() {}

    public static Integer calculateAuthorAgeAtPublication(BookEntity book) {
        if (book == null || book.getAuthor() == null
                || book.getAuthor().getBirthDate() == null
                || book.getPublicationDate() == null) {
            return null;
        }
        return Period.between(book.getAuthor().getBirthDate(), book.getPublicationDate()).getYears();
    }

    public static void applyDefaultValuesOnBookRequestIfNeeded(BookCreateRequest request) {
        if (request.getLanguage() == null) {
            request.setLanguage(Language.UNKNOWN);
        }
        if (request.getType() == null) {
            request.setType(Type.UNKNOWN);
        }
        if (request.getCategory() == null) {
            request.setCategory(Category.UNKNOWN);
        }
        if (request.getAudience() == null) {
            request.setAudience(Audience.UNKNOWN);
        }
        if (request.getStatus() == null) {
            request.setStatus(Status.UNREAD);
        }
        if (request.getFavorite() == null) {
            request.setFavorite(false);
        }
    }

    public static void applyDefaultValuesOnAuthorRequestIfNeeded(AuthorCreateRequest request) {
        if (request.getCountry() == null) {
            request.setCountry(Country.UNKNOWN);
        }
    }
}
