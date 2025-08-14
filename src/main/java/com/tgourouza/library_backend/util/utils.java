package com.tgourouza.library_backend.util;

import java.time.Period;

import com.tgourouza.library_backend.constant.Audience;
import com.tgourouza.library_backend.constant.Category;
import com.tgourouza.library_backend.constant.Country;
import com.tgourouza.library_backend.constant.Language;
import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.constant.Type;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.book.BookCreateRequest;
import com.tgourouza.library_backend.entity.BookEntity;

public class utils {

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
            request.setLanguage(Language.UNKNOWN.toString());
        }
        if (request.getType() == null) {
            request.setType(Type.UNKNOWN.toString());
        }
        if (request.getCategory() == null) {
            request.setCategory(Category.UNKNOWN.toString());
        }
        if (request.getAudience() == null) {
            request.setAudience(Audience.UNKNOWN.toString());
        }
        if (request.getStatus() == null) {
            request.setStatus(Status.UNREAD.toString());
        }
        if (request.getFavorite() == null) {
            request.setFavorite(false);
        }
    }

    public static void applyDefaultValuesOnAuthorRequestIfNeeded(AuthorCreateRequest request) {
        if (request.getCountry() == null) {
            request.setCountry(Country.UNKNOWN.toString());
        }
    }
}
