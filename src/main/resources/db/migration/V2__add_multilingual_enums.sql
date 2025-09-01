-- ---------- CITY ----------
CREATE TABLE IF NOT EXISTS city (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    english     TEXT NOT NULL,
    french      TEXT,
    spanish     TEXT,
    italian     TEXT,
    german      TEXT,
    portuguese  TEXT,
    russian     TEXT,
    japanese    TEXT
);

-- ---------- COUNTRY ----------
CREATE TABLE IF NOT EXISTS country (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    english     TEXT NOT NULL,
    french      TEXT,
    spanish     TEXT,
    italian     TEXT,
    german      TEXT,
    portuguese  TEXT,
    russian     TEXT,
    japanese    TEXT
);

-- ---------- LANGUAGE ----------
CREATE TABLE IF NOT EXISTS language (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    english     TEXT NOT NULL,
    french      TEXT,
    spanish     TEXT,
    italian     TEXT,
    german      TEXT,
    portuguese  TEXT,
    russian     TEXT,
    japanese    TEXT
);

-- ---------- BOOK_TAG ----------
CREATE TABLE IF NOT EXISTS book_tag (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    english     TEXT NOT NULL,
    french      TEXT,
    spanish     TEXT,
    italian     TEXT,
    german      TEXT,
    portuguese  TEXT,
    russian     TEXT,
    japanese    TEXT
);

-- ---------- AUTHOR_TAG ----------
CREATE TABLE IF NOT EXISTS author_tag (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    english     TEXT NOT NULL,
    french      TEXT,
    spanish     TEXT,
    italian     TEXT,
    german      TEXT,
    portuguese  TEXT,
    russian     TEXT,
    japanese    TEXT
);

-- ---------- STATUS ----------
CREATE TABLE IF NOT EXISTS status (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    english     TEXT NOT NULL,
    french      TEXT,
    spanish     TEXT,
    italian     TEXT,
    german      TEXT,
    portuguese  TEXT,
    russian     TEXT,
    japanese    TEXT
);
