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
    description_japanese TEXT,

    CONSTRAINT fk_author_country FOREIGN KEY (country) REFERENCES country(name)
);

CREATE TABLE book (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    original_title VARCHAR(255) NOT NULL,

    author_id UUID NOT NULL,
    publication_date DATE,

    language VARCHAR(255),
    type VARCHAR(255),
    category VARCHAR(255),
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
    description_japanese TEXT,

    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author(id),
    CONSTRAINT fk_book_language FOREIGN KEY (language) REFERENCES language(name),
    CONSTRAINT fk_book_type FOREIGN KEY (type) REFERENCES type(name),
    CONSTRAINT fk_book_category FOREIGN KEY (category) REFERENCES category(name),
    CONSTRAINT fk_book_audience FOREIGN KEY (audience) REFERENCES audience(name),
    CONSTRAINT fk_book_status FOREIGN KEY (status) REFERENCES status(name)
);
