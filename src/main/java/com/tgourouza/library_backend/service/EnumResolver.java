package com.tgourouza.library_backend.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.tgourouza.library_backend.constant.*;
import com.tgourouza.library_backend.exception.InvalidRequestException;

import org.springframework.stereotype.Component;

@Component
public class EnumResolver {
    public Status getStatus(String input) {
        return resolveEnum(Status.class, "Status", input);
    }

    // TODO: unused
    public Set<Tag> getTags(Set<Tag> input) {
        return resolveTagSet(input);
    }

    private <E extends Enum<E>> E resolveEnum(Class<E> enumClass, String fieldName, String input) {
        try {
            return Enum.valueOf(enumClass, input.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new InvalidRequestException(fieldName, input);
        }
    }

    private Set<Tag> resolveTagSet(Set<Tag> input) {
        if (input == null || input.isEmpty()) {
            return Set.of(Tag.UNKNOWN);
        }
        return input.stream()
                .map(Tag::name)
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .map(s -> {
                    try {
                        return Tag.valueOf(s);
                    } catch (IllegalArgumentException e) {
                        return Tag.UNKNOWN;
                    }
                })
                .collect(Collectors.toSet());
    }
}
