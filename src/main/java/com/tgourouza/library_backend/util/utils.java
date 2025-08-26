package com.tgourouza.library_backend.util;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.BookEntity;

public class utils {

    public static Integer calculateAuthorAgeAtPublication(BookEntity book) {
        if (book == null || book.getAuthor() == null
                || book.getAuthor().getBirthDate() == null
                || book.getPublicationYear() == null) {
            return null;
        }
        return book.getPublicationYear() - book.getAuthor().getBirthDate().getYear();
    }

    public static Integer calculateAuthorAgeAtDeathOrCurrent(AuthorEntity author) {
        if (author == null || author.getBirthDate() == null) {
            return null;
        }
        var maxDate = (author.getDeathDate() != null) ? author.getDeathDate() : java.time.LocalDate.now();
        return Period.between(author.getBirthDate(), maxDate).getYears();
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

    public static String toCsv(List<String> fields) {
        return fields.stream()
                .map(utils::escape)
                .collect(Collectors.joining(","));
    }

    private static String escape(String s) {
        if (s == null) {
            return ""; // null -> empty field

                }boolean needsQuote
                = s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r")
                || s.startsWith(" ") || s.endsWith(" ");
        String escaped = s.replace("\"", "\"\""); // double quotes inside
        return needsQuote ? "\"" + escaped + "\"" : escaped;
    }

    public static List<String> toList(String line) {
        if (line == null) {
            return List.of();
        }

        List<String> out = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;
        int len = line.length();

        for (int i = 0; i < len; i++) {
            char c = line.charAt(i);

            if (inQuotes) {
                if (c == '"') {
                    // Escaped quote?
                    if (i + 1 < len && line.charAt(i + 1) == '"') {
                        field.append('"');
                        i++; // skip the second quote
                    } else {
                        inQuotes = false; // closing quote
                    }
                } else {
                    field.append(c);
                }
            } else {
                if (c == ',') {
                    out.add(field.toString().trim());
                    field.setLength(0);
                } else if (c == '"') {
                    inQuotes = true; // start quoted field
                } else if (c == '\n') {
                    break; // end of record
                } else if (c == '\r') {
                    // ignore CR (handles CRLF)
                } else {
                    field.append(c);
                }
            }
        }

        out.add(field.toString().trim()); // last field
        return out;
    }
}
