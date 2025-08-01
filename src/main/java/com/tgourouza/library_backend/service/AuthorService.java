package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.constant.CountryEntity;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.author.AuthorMapper;
import com.tgourouza.library_backend.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.tgourouza.library_backend.util.utils.applyDefaultValuesOnAuthorRequestIfNeeded;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final EntityResolver entityResolver;
    private final AuthorMapper authorMapper;

    public AuthorService(
            AuthorRepository authorRepository, EntityResolver entityResolver,
            AuthorMapper authorMapper
    ) {
        this.authorRepository = authorRepository;
        this.entityResolver = entityResolver;
        this.authorMapper = authorMapper;
    }

    public List<AuthorDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .toList();
    }

    public AuthorDTO getById(UUID authorId) {
        return authorMapper.toDTO(entityResolver.getAuthorEntity(authorId));
    }

    public AuthorDTO create(AuthorCreateRequest request) {
        applyDefaultValuesOnAuthorRequestIfNeeded(request);
        return updateEntityAndSave(
                request,
                new AuthorEntity(),
                entityResolver.getCountryEntity(request.getCountry())
        );
    }

    public AuthorDTO update(UUID authorId, AuthorCreateRequest request) {
        return updateEntityAndSave(
                request,
                entityResolver.getAuthorEntity(authorId),
                entityResolver.getCountryEntity(request.getCountry())
        );
    }

    public void delete(UUID authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new DataNotFoundException("Author", String.valueOf(authorId));
        }
        authorRepository.deleteById(authorId);
    }

    private AuthorDTO updateEntityAndSave(AuthorCreateRequest request, AuthorEntity author, CountryEntity country) {
        authorMapper.updateEntity(author, request, country);
        return authorMapper.toDTO(authorRepository.save(author));
    }
}
