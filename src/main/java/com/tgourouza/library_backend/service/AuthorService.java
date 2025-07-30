package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.CountryEntity;
import com.tgourouza.library_backend.entity.GenderEntity;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.AuthorMapper;
import com.tgourouza.library_backend.repository.AuthorRepository;
import com.tgourouza.library_backend.repository.CountryRepository;
import com.tgourouza.library_backend.repository.GenderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;
    private final GenderRepository genderRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(
            AuthorRepository authorRepository,
            CountryRepository countryRepository,
            GenderRepository genderRepository,
            AuthorMapper authorMapper
    ) {
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
        this.genderRepository = genderRepository;
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
        CountryEntity country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new DataNotFoundException("Country", String.valueOf(request.getCountryId())));
        GenderEntity gender = genderRepository.findById(request.getGenderId())
                .orElseThrow(() -> new DataNotFoundException("Gender", String.valueOf(request.getGenderId())));

        AuthorEntity entity = authorMapper.toEntity(request, country, gender);
        AuthorEntity saved = authorRepository.save(entity);
        return authorMapper.toDTO(saved);
    }

    public AuthorDTO update(UUID id, AuthorCreateRequest request) {
        AuthorEntity existing = authorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(id)));

        CountryEntity country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new DataNotFoundException("Country", String.valueOf(request.getCountryId())));
        GenderEntity gender = genderRepository.findById(request.getGenderId())
                .orElseThrow(() -> new DataNotFoundException("Gender", String.valueOf(request.getGenderId())));

        authorMapper.updateAuthorFromRequest(request, country, gender, existing);

        return authorMapper.toDTO(authorRepository.save(existing));
    }

    public void delete(UUID id) {
        if (!authorRepository.existsById(id)) {
            throw new DataNotFoundException("Author", String.valueOf(id));
        }
        authorRepository.deleteById(id);
    }
}
