AuthorEntity author = new AuthorEntity();
author.setName("Victor Hugo");

BookEntity book = new BookEntity();
book.setOriginalTitle("Les Misérables");
book.setAuthor(author);

author.getBooks().add(book); // bidirectionnel

authorRepository.save(author); // cascade va aussi sauver le livre

---

// Supposons que tu reçoives l'ID de l'auteur (UUID)
UUID authorId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

// 1. Récupérer l'auteur existant depuis la base
AuthorEntity author = authorRepository.findById(authorId)
    .orElseThrow(() -> new RuntimeException("Author not found"));

// 2. Créer un nouveau livre
BookEntity book = new BookEntity();
book.setOriginalTitle("L'Homme qui rit");
book.setPublicationDate("1869");
// ... autres setters
book.setAuthor(author); // 3. Associer l’auteur

// 4. Sauvegarder le livre
bookRepository.save(book);

---

public BookDTO createBook(BookCreateRequest request) {
    UUID authorId = request.getAuthorId();

    AuthorEntity author = authorRepository.findById(authorId)
        .orElseThrow(() -> new RuntimeException("Author not found"));

    BookEntity book = new BookEntity();
    book.setOriginalTitle(request.getOriginalTitle());
    book.setAuthor(author);

    return toDTO(bookRepository.save(book));
}
