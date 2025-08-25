package com.tgourouza.library_backend.util;

import java.time.Period;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.constant.Tag;
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
        if (request.getTags() == null) {
            request.setTags(null);
        }
        if (request.getStatus() == null) {
            request.setStatus(Status.UNREAD.toString());
        }
        if (request.getFavorite() == null) {
            request.setFavorite(false);
        }
    }

    public static String cleanAndTitleCase(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }
        String cleaned = input.replace("\"", "");
        StringBuilder result = new StringBuilder();
        String[] words = cleaned.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    result.append(word.substring(1));
                }
            }
            if (i < words.length - 1) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    public static Set<Tag> fromCsv(String csv) {
        if (csv == null || csv.isBlank()) {
            return Set.of();
        }
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .map(s -> {
                    try {
                        return Tag.valueOf(s);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
