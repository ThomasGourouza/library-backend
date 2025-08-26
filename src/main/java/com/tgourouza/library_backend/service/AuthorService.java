package com.tgourouza.library_backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.AuthorMapper;
import com.tgourouza.library_backend.repository.AuthorRepository;

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
                new AuthorEntity()
        );
    }

    public AuthorDTO update(UUID authorId, AuthorCreateRequest request) {
        return updateEntityAndSave(
                request,
                getAuthorEntity(authorId)
        );
    }

    public void delete(UUID authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new DataNotFoundException("Author", String.valueOf(authorId));
        }
        authorRepository.deleteById(authorId);
    }

    private AuthorDTO updateEntityAndSave(AuthorCreateRequest request, AuthorEntity author) {
        authorMapper.updateEntity(author, request);
        return authorMapper.toDTO(authorRepository.save(author));
    }

    private AuthorEntity getAuthorEntity(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(authorId)));
    }

    public UUID getAuthorEntityId(String authorOLKey) {
        return authorRepository.getEntityId(authorOLKey).orElse(null);
    }
}
