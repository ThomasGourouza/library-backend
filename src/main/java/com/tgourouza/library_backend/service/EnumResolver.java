package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.constant.*;
import com.tgourouza.library_backend.exception.InvalidRequestException;
import org.springframework.stereotype.Component;

@Component
public class EnumResolver {
    public Status getStatus(String input) {
        return resolveEnum(Status.class, "Status", input);
    }

    public Audience getAudience(String input) {
        return resolveEnum(Audience.class, "Audience", input);
    }

    public Category getCategory(String input) {
        return resolveEnum(Category.class, "Category", input);
    }

    public Country getCountry(String input) {
        return resolveEnum(Country.class, "Country", input);
    }

    public Language getLanguage(String input) {
        return resolveEnum(Language.class, "Language", input);
    }

    public Type getType(String input) {
        return resolveEnum(Type.class, "Type", input);
    }

    private <E extends Enum<E>> E resolveEnum(Class<E> enumClass, String fieldName, String input) {
        try {
            return Enum.valueOf(enumClass, input.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new InvalidRequestException(fieldName, input);
        }
    }
}
