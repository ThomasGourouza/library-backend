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

import com.tgourouza.library_backend.constant.Type;
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

    // Cities
    @PostMapping("/cities")
    public ResponseEntity<Multilingual> createCity(@Valid @RequestBody Multilingual request) {
        Multilingual created = multilingualService.create(Type.CITY, request);
        return ResponseEntity
                .created(URI.create("/multilingual/cities"))
                .body(created);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<Multilingual>> getAllCities() {
        return ResponseEntity.ok(multilingualService.getAll(Type.CITY));
    }

    @GetMapping("/cities")
    public ResponseEntity<Multilingual> findCity(@RequestParam(required = false) String english) {
        return multilingualService.findByEnglishIgnoreCase(Type.CITY, english)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cities/translate")
    public ResponseEntity<String> translateCity(
            @RequestParam("en") String englishValue,
            @RequestParam("target") String targetLanguage) {
        return multilingualService.translateFromEnglish(Type.CITY, englishValue, targetLanguage)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cities")
    public ResponseEntity<Void> deleteCity(@RequestParam(required = false) String english) {
        long deleted = multilingualService.deleteByEnglishIgnoreCase(Type.CITY, english);
        return (deleted > 0)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // Countries
    @PostMapping("/countries")
    public ResponseEntity<Multilingual> createCountry(@Valid @RequestBody Multilingual request) {
        Multilingual created = multilingualService.create(Type.COUNTRY, request);
        return ResponseEntity
                .created(URI.create("/multilingual/countries"))
                .body(created);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<Multilingual>> getAllCountries() {
        return ResponseEntity.ok(multilingualService.getAll(Type.COUNTRY));
    }

    @GetMapping("/countries")
    public ResponseEntity<Multilingual> findCountry(@RequestParam(required = false) String english) {
        return multilingualService.findByEnglishIgnoreCase(Type.COUNTRY, english)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/countries/translate")
    public ResponseEntity<String> translateCountry(
            @RequestParam("en") String englishValue,
            @RequestParam("target") String targetLanguage) {
        return multilingualService.translateFromEnglish(Type.COUNTRY, englishValue, targetLanguage)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/countries")
    public ResponseEntity<Void> deleteCountry(@RequestParam(required = false) String english) {
        long deleted = multilingualService.deleteByEnglishIgnoreCase(Type.COUNTRY, english);
        return (deleted > 0)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // Languages
    @PostMapping("/languages")
    public ResponseEntity<Multilingual> createLanguage(@Valid @RequestBody Multilingual request) {
        Multilingual created = multilingualService.create(Type.LANGUAGE, request);
        return ResponseEntity
                .created(URI.create("/multilingual/languages"))
                .body(created);
    }

    @GetMapping("/languages")
    public ResponseEntity<List<Multilingual>> getAllLanguages() {
        return ResponseEntity.ok(multilingualService.getAll(Type.LANGUAGE));
    }

    @GetMapping("/languages")
    public ResponseEntity<Multilingual> findLanguage(@RequestParam(required = false) String english) {
        return multilingualService.findByEnglishIgnoreCase(Type.LANGUAGE, english)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/languages/translate")
    public ResponseEntity<String> translateLanguage(
            @RequestParam("en") String englishValue,
            @RequestParam("target") String targetLanguage) {
        return multilingualService.translateFromEnglish(Type.LANGUAGE, englishValue, targetLanguage)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/languages")
    public ResponseEntity<Void> deleteLanguage(@RequestParam(required = false) String english) {
        long deleted = multilingualService.deleteByEnglishIgnoreCase(Type.LANGUAGE, english);
        return (deleted > 0)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // Status
    @PostMapping("/status")
    public ResponseEntity<Multilingual> createStatus(@Valid @RequestBody Multilingual request) {
        Multilingual created = multilingualService.create(Type.STATUS, request);
        return ResponseEntity
                .created(URI.create("/multilingual/status"))
                .body(created);
    }

    @GetMapping("/status")
    public ResponseEntity<List<Multilingual>> getAllStatuses() {
        return ResponseEntity.ok(multilingualService.getAll(Type.STATUS));
    }

    @GetMapping("/status")
    public ResponseEntity<Multilingual> findStatus(@RequestParam(required = false) String english) {
        return multilingualService.findByEnglishIgnoreCase(Type.STATUS, english)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/translate")
    public ResponseEntity<String> translateStatus(
            @RequestParam("en") String englishValue,
            @RequestParam("target") String targetLanguage) {
        return multilingualService.translateFromEnglish(Type.STATUS, englishValue, targetLanguage)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/status")
    public ResponseEntity<Void> deleteStatus(@RequestParam(required = false) String english) {
        long deleted = multilingualService.deleteByEnglishIgnoreCase(Type.STATUS, english);
        return (deleted > 0)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // BookTags
    @PostMapping("/book_tags")
    public ResponseEntity<Multilingual> createBookTag(@Valid @RequestBody Multilingual request) {
        Multilingual created = multilingualService.create(Type.BOOK_TAG, request);
        return ResponseEntity
                .created(URI.create("/multilingual/book_tags"))
                .body(created);
    }

    @GetMapping("/book_tags")
    public ResponseEntity<List<Multilingual>> getAllBookTags() {
        return ResponseEntity.ok(multilingualService.getAll(Type.BOOK_TAG));
    }

    @GetMapping("/book-tag")
    public ResponseEntity<Multilingual> findBookTag(@RequestParam(required = false) String english) {
        return multilingualService.findByEnglishIgnoreCase(Type.BOOK_TAG, english)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/book-tag/translate")
    public ResponseEntity<String> translateBookTag(
            @RequestParam("en") String englishValue,
            @RequestParam("target") String targetLanguage) {
        return multilingualService.translateFromEnglish(Type.BOOK_TAG, englishValue, targetLanguage)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/book_tags")
    public ResponseEntity<Void> deleteBookTag(@RequestParam(required = false) String english) {
        long deleted = multilingualService.deleteByEnglishIgnoreCase(Type.BOOK_TAG, english);
        return (deleted > 0)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // Occupations
    @PostMapping("/occupations")
    public ResponseEntity<Multilingual> createOccupation(@Valid @RequestBody Multilingual request) {
        Multilingual created = multilingualService.create(Type.OCCUPATION, request);
        return ResponseEntity
                .created(URI.create("/multilingual/occupations"))
                .body(created);
    }

    @GetMapping("/occupations")
    public ResponseEntity<List<Multilingual>> getAllOccupations() {
        return ResponseEntity.ok(multilingualService.getAll(Type.OCCUPATION));
    }

    @GetMapping("/occupations")
    public ResponseEntity<Multilingual> findOccupation(@RequestParam(required = false) String english) {
        return multilingualService.findByEnglishIgnoreCase(Type.OCCUPATION, english)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/occupations/translate")
    public ResponseEntity<String> translateOccupation(
            @RequestParam("en") String englishValue,
            @RequestParam("target") String targetLanguage) {
        return multilingualService.translateFromEnglish(Type.OCCUPATION, englishValue, targetLanguage)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/occupations")
    public ResponseEntity<Void> deleteOccupation(@RequestParam(required = false) String english) {
        long deleted = multilingualService.deleteByEnglishIgnoreCase(Type.OCCUPATION, english);
        return (deleted > 0)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
