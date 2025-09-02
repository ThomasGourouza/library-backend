package com.tgourouza.library_backend.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.service.MultilingualService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/multilingual")
public class MultilingualController {

    private final MultilingualService multilingualService;

    public MultilingualController(MultilingualService multilingualService) {
        this.multilingualService = multilingualService;
    }

    @PostMapping("/cities")
    public ResponseEntity<Multilingual> createCity(@Valid @RequestBody Multilingual request) {
        Multilingual created = multilingualService.createCity(request);
        return ResponseEntity
                .created(URI.create("/multilingual/cities"))
                .body(created);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<Multilingual>> getAllCities() {
        return ResponseEntity.ok(multilingualService.getAllCities());
    }

    @GetMapping("/city")
    public ResponseEntity<Multilingual> findByEnglishQuery(@RequestParam(required = false) String english) {
        return multilingualService.findByEnglishIgnoreCase(english)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/city/translate")
    public ResponseEntity<String> translate(
            @RequestParam("en") String englishValue,
            @RequestParam("target") String targetLanguage) {
        return multilingualService.translateFromEnglish(englishValue, targetLanguage)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cities")
    public ResponseEntity<Void> deleteByEnglishQuery(@RequestParam(required = false) String english) {
        long deleted = multilingualService.deleteCityByEnglishIgnoreCase(english);
        return (deleted > 0)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
