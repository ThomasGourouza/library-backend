--CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO book (id, title, author) VALUES
  (uuid_generate_v4(), '1984', 'George Orwell'),
  (uuid_generate_v4(), 'Le Petit Prince', 'Antoine de Saint-Exupéry'),
  (uuid_generate_v4(), 'Moby Dick', 'Herman Melville');
