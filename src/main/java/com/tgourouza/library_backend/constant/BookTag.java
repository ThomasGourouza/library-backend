package com.tgourouza.library_backend.constant;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@RequiredArgsConstructor
public enum BookTag {
    LITERATURE("Literature"),
    PHILOSOPHY("Philosophy"),
    RELIGION("Religion"),
    HISTORY("History"),
    GEOGRAPHY("Geography"),
    SOCIAL("Social"),
    CULTURE("Culture"),
    SOCIOLOGY("Sociology"),
    POLITICS("Politics"),
    MYTHOLOGY("Mythology"),
    LEGEND("Legend"),
    PSYCHOLOGY("Psychology"),
    TECHNOLOGY("Technology"),
    SCIENCE("Science"),
    ECONOMICS("Economics"),
    ART("Art"),
    MUSIC("Music"),
    EDUCATION("Education"),
    ROMAN("Novel"),
    ESSAY("Essay"),
    POETRY("Poetry"),
    THEATER("Theater"),
    BIOGRAPHY("Biography"),
    LETTERS("Letters"),
    TALE("Tale"),
    ENCYCLOPEDIA("Encyclopedia"),
    SCIENCE_FICTION("Science Fiction"),
    CHILDREN("Children"),
    CLASSIC("Classic");

    @Getter
    private final String value;
}
