package com.tgourouza.library_backend.exception;

import java.util.UUID;

public class AuthorNotFoundException extends RuntimeException {
  public AuthorNotFoundException(UUID id) {
    super("Author with id " + id + " not found");
  }
}
