package com.tgourouza.library_backend.constant;

public enum Tag {
    LITERATURE("literature"),
    PHILOSOPHY("philosophy"),
    RELIGION("religion"),
    HISTORY("history"),
    GEOGRAPHY("geography"),
    SOCIAL("social"),
    CULTURE("culture"),
    SOCIOLOGY("sociology"),
    POLITICS("politics"),
    MYTHOLOGY("mythology"),
    LEGEND("legend"),
    PSYCHOLOGY("psychology"),
    TECHNOLOGY("technology"),
    SCIENCE("science"),
    ECONOMICS("economics"),
    ART("art"),
    MUSIC("music"),
    EDUCATION("education"),
    ROMAN("roman"),
    ESSAY("essay"),
    POETRY("poetry"),
    THEATER("theater"),
    BIOGRAPHY("biography"),
    LETTERS("letters"),
    TALE("tale"),
    ENCYCLOPEDIA("encyclopedia"),
    SCIENCE_FICTION("science fiction"),
    CHILDREN("children"),
    CLASSIC("classic");

    private final String value;

    Tag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
