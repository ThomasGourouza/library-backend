package com.tgourouza.library_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tgourouza.library_backend.dto.wikidata.AuthorWikidata;
import com.tgourouza.library_backend.service.WikidataService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/wikidata")
public class WikidataController {

    private final WikidataService service;

    public WikidataController(WikidataService service) {
        this.service = service;
    }

    @GetMapping(value = "/authors/{qid}", produces = "application/json")
    public ResponseEntity<AuthorWikidata> getAuthor(@PathVariable String qid) {
            return service.getAuthorByQid(qid)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
