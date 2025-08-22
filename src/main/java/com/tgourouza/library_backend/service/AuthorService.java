package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.AuthorMapper;
import com.tgourouza.library_backend.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(
            AuthorRepository authorRepository,
            AuthorMapper authorMapper
    ) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .toList();
    }

    public AuthorDTO getById(UUID authorId) {
        return authorMapper.toDTO(getAuthorEntity(authorId));
    }

    public AuthorDTO create(AuthorCreateRequest request) {
        return updateEntityAndSave(
                request,
                new AuthorEntity(),
                request.getCountry()
        );
    }

    public AuthorDTO update(UUID authorId, AuthorCreateRequest request) {
        return updateEntityAndSave(
                request,
                getAuthorEntity(authorId),
                request.getCountry()
        );
    }

    public void delete(UUID authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new DataNotFoundException("Author", String.valueOf(authorId));
        }
        authorRepository.deleteById(authorId);
    }

    private AuthorDTO updateEntityAndSave(AuthorCreateRequest request, AuthorEntity author, String country) {
        authorMapper.updateEntity(author, request, country);
        return authorMapper.toDTO(authorRepository.save(author));
    }

    private AuthorEntity getAuthorEntity(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(authorId)));
    }
}
