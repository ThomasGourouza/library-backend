package com.tgourouza.library_backend.controller;

import com.tgourouza.library_backend.dto.libretranslate.DetectRequest;
import com.tgourouza.library_backend.dto.libretranslate.DetectResult;
import com.tgourouza.library_backend.dto.libretranslate.TranslateRequest;
import com.tgourouza.library_backend.dto.libretranslate.TranslateResponse;
import com.tgourouza.library_backend.service.LibreTranslateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
@RestController
@RequestMapping(path = "/translate", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibreTranslateController {

    private final LibreTranslateService service;

    public LibreTranslateController(LibreTranslateService service) {
        this.service = service;
    }

    @PostMapping(path = "/detect", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetectResult> detect(@RequestBody DetectRequest req) {
        try {
            if (req == null || req.q() == null || req.q().isBlank()) {
                return ResponseEntity.badRequest().build();
            }
            DetectResult result = service.detect(req.q());
            return ResponseEntity.ok(result);
        } catch (RestClientResponseException e) {
            log.error("libretranslate /detect failed: status={} body={} text_snippet={}",
                    e.getRawStatusCode(), e.getResponseBodyAsString(), snippet(req.q(), 200), e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        } catch (RestClientException e) {
            log.error("libretranslate /detect call error: text_snippet={}", snippet(req.q(), 200), e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        } catch (Exception e) {
            log.error("unexpected error /detect: text_snippet={}", snippet(req.q(), 200), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TranslateResponse> translate(@RequestBody TranslateRequest req) {
        try {
            if (req == null || req.q() == null || req.q().isBlank()
                    || req.target() == null || req.target().isBlank()) {
                return ResponseEntity.badRequest().build();
            }
            TranslateResponse resp = service.translate(req.q(), req.source(), req.target());
            return ResponseEntity.ok(resp);
        } catch (RestClientResponseException e) {
            log.error("libretranslate /translate failed: status={} body={} text_snippet={} src={} tgt={}",
                    e.getRawStatusCode(), e.getResponseBodyAsString(),
                    snippet(req.q(), 200), req.source(), req.target(), e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        } catch (RestClientException e) {
            log.error("libretranslate /translate call error: text_snippet={} src={} tgt={}",
                    snippet(req.q(), 200), req.source(), req.target(), e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        } catch (Exception e) {
            log.error("unexpected error /translate: text_snippet={} src={} tgt={}",
                    snippet(req.q(), 200), req.source(), req.target(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private static String snippet(String s, int max) {
        if (s == null) return null;
        String compact = s.replaceAll("\\s+", " ").trim();
        return compact.length() <= max ? compact : compact.substring(0, max) + "…";
    }
}
