package com.tgourouza.library_backend.exception;

public class WikidataUpstreamException extends RuntimeException {
    public WikidataUpstreamException(String message) {
        super(message);
    }
    public WikidataUpstreamException(String method, String uri, Integer status) {
        super("Wikidata upstream failed: %s %s (HTTP %s)".formatted(
                method, uri, status == null ? "n/a" : status));
    }
    public WikidataUpstreamException(String method, String uri, Throwable cause) {
        super("Wikidata upstream failed: %s %s".formatted(method, uri), cause);
    }
}