package com.tgourouza.library_backend.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.tgourouza.library_backend.dto.nllb.TranslateResp;
import com.tgourouza.library_backend.dto.openLibrary.AuthorInfo;
import com.tgourouza.library_backend.dto.openLibrary.BookInfo;
import com.tgourouza.library_backend.service.OpenLibraryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/openlibrary")
public class OpenLibraryController {

    private final OpenLibraryService openLibraryService;
    private final RestClient nllbClient;

    public OpenLibraryController(OpenLibraryService openLibraryService,
            @Qualifier("nllbClient") RestClient nllbClient) {
        this.openLibraryService = openLibraryService;
        this.nllbClient = nllbClient;
    }

    @GetMapping("/test")
    public ResponseEntity<TranslateResp> test(@RequestParam String text, @RequestParam String sourceLang,
            @RequestParam String targetLang) {
        var map = java.util.Map.of("text", text, "source", sourceLang, "target", targetLang);

        TranslateResp resp = nllbClient.post()
                .uri("/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(map)
                // .body(new TranslateReq(text, sourceLang, targetLang))
                .retrieve()
                .body(TranslateResp.class);

        return ResponseEntity.ok(resp);
    }

    @GetMapping(value = "/book-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookInfo> getBookInfo(
            @RequestParam String title,
            @RequestParam(required = false) String author) {
        try {
            BookInfo info = openLibraryService.getBookInfo(title, author);
            if (info == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(info);

        } catch (Exception e) {
            log.error("OpenLibrary book-info failed (title='{}', author='{}')",
                    title, author, e);
            return ResponseEntity.status(502).build();
        }
    }

    @GetMapping(value = "/author-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorInfo> getAuthorInfo(@RequestParam("author_key") String authorKey) {
        try {
            AuthorInfo info = openLibraryService.getAuthorInfo(authorKey);
            if (info == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(info);

        } catch (Exception e) {
            log.error("OpenLibrary author-info failed (author_key='{}')", authorKey, e);
            return ResponseEntity.status(502).build();
        }
    }
}
