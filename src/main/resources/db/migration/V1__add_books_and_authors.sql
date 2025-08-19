CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE author (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    country VARCHAR(255),
    birth_date DATE,
    death_date DATE,
    wikipedia_link TEXT,

    description_french TEXT,
    description_spanish TEXT,
    description_italian TEXT,
    description_portuguese TEXT,
    description_english TEXT,
    description_german TEXT,
    description_russian TEXT,
    description_japanese TEXT
);

CREATE TABLE book (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    original_title VARCHAR(255) NOT NULL,

    author_id UUID NOT NULL,
    publication_date DATE,

    language VARCHAR(255),
    genre VARCHAR(255),
    subject VARCHAR(255),
    audience VARCHAR(255),

    wikipedia_link TEXT,
    status VARCHAR(255),
    favorite BOOLEAN,
    personal_notes TEXT,

    title_french TEXT,
    title_spanish TEXT,
    title_italian TEXT,
    title_portuguese TEXT,
    title_english TEXT,
    title_german TEXT,
    title_russian TEXT,
    title_japanese TEXT,

    description_french TEXT,
    description_spanish TEXT,
    description_italian TEXT,
    description_portuguese TEXT,
    description_english TEXT,
    description_german TEXT,
    description_russian TEXT,
    description_japanese TEXT
);
