package com.tgourouza.library_backend.exception;

public class OpenLibraryUpstreamException extends RuntimeException {
  public OpenLibraryUpstreamException(String message) {
    super(message);
  }
  public OpenLibraryUpstreamException(String method, String uri, Integer status) {
    super("OpenLibrary upstream failed: %s %s (HTTP %s)".formatted(
            method, uri, status == null ? "n/a" : status));
  }
  public OpenLibraryUpstreamException(String method, String uri, Throwable cause) {
    super("OpenLibrary upstream failed: %s %s".formatted(method, uri), cause);
  }
}