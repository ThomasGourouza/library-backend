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

    public AuthorDTO getById(UUID id) {
        AuthorEntity entity = authorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(id)));
        return authorMapper.toDTO(entity);
    }

    public AuthorDTO create(AuthorCreateRequest request) {
        CountryEntity country = resolveCountry(request.getCountry());

        AuthorEntity entity = authorMapper.toEntity(request, country);
        AuthorEntity saved = authorRepository.save(entity);

        return authorMapper.toDTO(saved);
    }

    public AuthorDTO update(UUID id, AuthorCreateRequest request) {
        AuthorEntity existing = authorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(id)));

        CountryEntity country = resolveCountry(request.getCountry());

        authorMapper.updateEntity(existing, request, country);
        AuthorEntity updated = authorRepository.save(existing);

        return authorMapper.toDTO(updated);
    }

    private CountryEntity resolveCountry(Country countryEnum) {
        return countryRepository.findByName(countryEnum)
                .orElseThrow(() -> new DataNotFoundException("Country", countryEnum.name()));
    }

    public void delete(UUID id) {
        if (!authorRepository.existsById(id)) {
            throw new DataNotFoundException("Author", String.valueOf(id));
        }
        authorRepository.deleteById(id);
    }
}
