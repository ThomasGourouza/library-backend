package com.tgourouza.library_backend.mapper;

import static com.tgourouza.library_backend.util.openLibraryUtils.text;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tgourouza.library_backend.constant.Audience;
import com.tgourouza.library_backend.constant.Form;
import com.tgourouza.library_backend.constant.Genre;
import com.tgourouza.library_backend.constant.Subject;
import com.tgourouza.library_backend.dto.Category;
import com.tgourouza.library_backend.service.NllbService;

@Component
public class CategoryMapper {

    private final NllbService nllbService;

    public CategoryMapper(NllbService nllbService) {
        this.nllbService = nllbService;
    }

    public Category fromTags(Set<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return new Category(Set.of(), Set.of(), Set.of(), Set.of());
        }

        Set<String> normalized = tags.stream()
                .map(t -> toEnglish(t.trim().toLowerCase()))
                .collect(Collectors.toSet());

        Set<Audience> audiences = matchEnums(normalized, Audience.values(), Audience.UNKNOWN);
        Set<Genre> genres = matchEnums(normalized, Genre.values(), Genre.UNKNOWN);
        Set<Form> forms = matchEnums(normalized, Form.values(), Form.UNKNOWN);
        Set<Subject> subjects = matchEnums(normalized, Subject.values(), Subject.UNKNOWN);

        return new Category(audiences, genres, subjects, forms);
    }

    private <E extends Enum<E>> Set<E> matchEnums(Set<String> normalizedTags, E[] values, E unknown) {
        var set = Arrays.stream(values)
                .filter(e -> e != unknown)
                .filter(e -> {
                    String enumName = e.name().toLowerCase().replace("_", " ");
                    return normalizedTags.stream().anyMatch(tag -> tag.contains(enumName));
                })
                .collect(Collectors.toSet());
        return set.isEmpty() ? Set.of(unknown) : set;
    }

    private String toEnglish(String text) {
        try {
            if (text == null || text.isBlank()) {
                return text;
            }
            return nllbService.translateToEnglish(text).trim().toLowerCase();
        } catch (Exception e) {
            return text;
        }
    }
}
