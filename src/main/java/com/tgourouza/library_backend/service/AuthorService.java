package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.constant.Country;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.constant.CountryEntity;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.AuthorMapper;
import com.tgourouza.library_backend.repository.AuthorRepository;
import com.tgourouza.library_backend.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(
            AuthorRepository authorRepository,
            CountryRepository countryRepository,
            AuthorMapper authorMapper
    ) {
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .toList();
    }

    public AuthorDTO getById(UUID authorId) {
        AuthorEntity author = findAuthor(authorId);
        return authorMapper.toDTO(author);
    }

    public AuthorDTO create(AuthorCreateRequest request) {
        AuthorEntity author = new AuthorEntity();
        CountryEntity country = resolveCountry(request.getCountry());
        return updateEntityAndSave(request, author, country);
    }

    public AuthorDTO update(UUID authorId, AuthorCreateRequest request) {
        AuthorEntity author = findAuthor(authorId);
        CountryEntity country = resolveCountry(request.getCountry());
        return updateEntityAndSave(request, author, country);
    }

    public void delete(UUID authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new DataNotFoundException("Author", String.valueOf(authorId));
        }
        authorRepository.deleteById(authorId);
    }

    private CountryEntity resolveCountry(Country countryEnum) {
        return countryRepository.findByName(countryEnum)
                .orElseThrow(() -> new DataNotFoundException("Country", countryEnum.name()));
    }

    private AuthorDTO updateEntityAndSave(AuthorCreateRequest request, AuthorEntity author, CountryEntity country) {
        authorMapper.updateEntity(author, request, country);
        return authorMapper.toDTO(authorRepository.save(author));
    }

    private AuthorEntity findAuthor(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(authorId)));
    }
}
