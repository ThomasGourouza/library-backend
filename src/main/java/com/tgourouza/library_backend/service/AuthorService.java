package com.tgourouza.library_backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tgourouza.library_backend.constant.DataLanguage;
import com.tgourouza.library_backend.dto.author.AuthorCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.exception.DuplicateAuthorException;
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

    public List<AuthorDTO> getAll(DataLanguage dataLanguage) {
        return authorRepository.findAll()
                .stream()
                .map(author -> authorMapper.toDTO(author, dataLanguage))
                .toList();
    }

    public AuthorDTO getById(UUID authorId, DataLanguage dataLanguage) {
        return authorMapper.toDTO(getAuthorEntity(authorId), dataLanguage);
    }

    public AuthorDTO create(AuthorCreateRequest request, DataLanguage dataLanguage) {
        return updateEntityAndSave(
                request,
                new AuthorEntity(),
                dataLanguage
        );
    }

    public void delete(UUID authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new DataNotFoundException("Author", String.valueOf(authorId));
        }
        authorRepository.deleteById(authorId);
    }

    private AuthorDTO updateEntityAndSave(AuthorCreateRequest request, AuthorEntity author, DataLanguage dataLanguage) {
        authorMapper.updateEntity(author, request);
        if (authorRepository.existsByOlKey(author.getOLKey())) {
            throw new DuplicateAuthorException("Author with ol_key " + author.getOLKey() + " already exists");
        }
        return authorMapper.toDTO(authorRepository.save(author), dataLanguage);
    }

    private AuthorEntity getAuthorEntity(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new DataNotFoundException("Author", String.valueOf(authorId)));
    }

    public UUID getAuthorEntityId(String authorOLKey) {
        return authorRepository.findByoLKey(authorOLKey).map(AuthorEntity::getId).orElse(null);
    }
}
