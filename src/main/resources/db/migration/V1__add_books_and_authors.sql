-- UUIDs via gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ---------- AUTHOR ----------
CREATE TABLE IF NOT EXISTS author (
    id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name                        TEXT        NOT NULL,
    ol_key                     TEXT,
    picture_url                 TEXT,

    description_french          TEXT,
    description_spanish         TEXT,
    description_italian         TEXT,
    description_portuguese      TEXT,
    description_english         TEXT,
    description_german          TEXT,
    description_russian         TEXT,
    description_japanese        TEXT,

    short_description_french    TEXT,
    short_description_spanish   TEXT,
    short_description_italian   TEXT,
    short_description_portuguese TEXT,
    short_description_english   TEXT,
    short_description_german    TEXT,
    short_description_russian   TEXT,
    short_description_japanese  TEXT,

    birth_date                  DATE,
    birth_city_english          TEXT,
    birth_country_english       TEXT,

    death_date                  DATE,
    death_city_english          TEXT,
    death_country_english       TEXT,

    citizenships_english         TEXT,

    occupations_english          TEXT,

    languages_english            TEXT,

    wikipedia_link_french       TEXT,
    wikipedia_link_spanish      TEXT,
    wikipedia_link_italian      TEXT,
    wikipedia_link_portuguese   TEXT,
    wikipedia_link_english      TEXT,
    wikipedia_link_german       TEXT,
    wikipedia_link_russian      TEXT,
    wikipedia_link_japanese     TEXT
);

-- Helpful index for lookups by author ol_key
CREATE INDEX IF NOT EXISTS idx_author_id ON author (id);
CREATE INDEX IF NOT EXISTS idx_author_ol_key ON author (ol_key);

-- ---------- BOOK ----------
CREATE TABLE IF NOT EXISTS book (
    id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    original_title              TEXT       NOT NULL,
    original_title_language_english TEXT,

    author_id                   UUID       NOT NULL REFERENCES author(id) ON UPDATE RESTRICT ON DELETE RESTRICT,

    author_age_at_publication   INTEGER,
    author_ol_key               TEXT,
    publication_year            INTEGER,

    title_french                TEXT,
    title_spanish               TEXT,
    title_italian               TEXT,
    title_portuguese            TEXT,
    title_english               TEXT,
    title_german                TEXT,
    title_russian               TEXT,
    title_japanese              TEXT,

    cover_url                   TEXT,
    number_of_pages             INTEGER,

    description_french          TEXT,
    description_spanish         TEXT,
    description_italian         TEXT,
    description_portuguese      TEXT,
    description_english         TEXT,
    description_german          TEXT,
    description_russian         TEXT,
    description_japanese        TEXT,

    tags_english                TEXT,

    wikipedia_link              TEXT,

    personal_notes              TEXT,
    status_english              TEXT,
    favorite                    BOOLEAN
);

-- Helpful indexes
CREATE INDEX IF NOT EXISTS idx_book_author_id         ON book (author_id);
CREATE INDEX IF NOT EXISTS idx_book_status_english     ON book (status_english);
CREATE INDEX IF NOT EXISTS idx_book_publication_year  ON book (publication_year);
