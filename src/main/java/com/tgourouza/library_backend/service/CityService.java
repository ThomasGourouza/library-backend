package com.tgourouza.library_backend.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.entity.CityEntity;
import com.tgourouza.library_backend.mapper.CityMapper;
import com.tgourouza.library_backend.repository.CityRepository;

@Service
@Transactional
public class CityService {

    private final CityRepository repo;
    private final CityMapper mapper;

    public CityService(CityRepository repo, CityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Multilingual create(Multilingual request) {
        CityEntity entity = mapper.toEntity(request);
        return mapper.toMultilingual(repo.save(entity));
    }

    @Transactional(readOnly = true)
    public List<Multilingual> getAll() {
        return repo.findAll().stream()
                .map(mapper::toMultilingual)
                .toList();
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "City not found: " + id);
        }
        repo.deleteById(id);
    }
}
