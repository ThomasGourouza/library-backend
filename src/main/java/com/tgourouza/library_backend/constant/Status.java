package com.tgourouza.library_backend.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    UNREAD("Unread"),
    TO_READ("To read"),
    READING("Reading"),
    READ("Read");

    @Getter
    private final String value;
}
