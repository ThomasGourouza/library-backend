package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.constant.LiteraryGenreCreateRequest;
import com.tgourouza.library_backend.dto.constant.LiteraryGenreDTO;
import com.tgourouza.library_backend.entity.LiteraryGenreEntity;
import com.tgourouza.library_backend.exception.AlreadyExistsException;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.LiteraryGenreMapper;
import com.tgourouza.library_backend.repository.LiteraryGenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiteraryGenreService {
    private final LiteraryGenreRepository literaryGenreRepository;
    private final LiteraryGenreMapper literaryGenreMapper;

    public LiteraryGenreService(LiteraryGenreRepository literaryGenreRepository, LiteraryGenreMapper literaryGenreMapper) {
        this.literaryGenreRepository = literaryGenreRepository;
        this.literaryGenreMapper = literaryGenreMapper;
    }

    public List<LiteraryGenreDTO> getAll() {
        return literaryGenreRepository.findAll()
                .stream()
                .map(literaryGenreMapper::toDTO)
                .toList();
    }

    public LiteraryGenreDTO getById(Long id) {
        return literaryGenreRepository.findById(id)
                .map(literaryGenreMapper::toDTO)
                .orElseThrow(() -> new DataNotFoundException("LiteraryGenre", String.valueOf(id)));
    }

    public LiteraryGenreDTO save(LiteraryGenreCreateRequest request) {
        LiteraryGenreEntity entity = literaryGenreMapper.toEntity(request);

        if (literaryGenreRepository.existsByName(entity.getName())) {
            throw new AlreadyExistsException("LiteraryGenre", entity.getName().name());
        }

        LiteraryGenreEntity saved = literaryGenreRepository.save(entity);
        return literaryGenreMapper.toDTO(saved);
    }

    public List<LiteraryGenreDTO> saveAll(List<LiteraryGenreCreateRequest> requests) {
        List<LiteraryGenreEntity> entities = requests.stream()
                .map(literaryGenreMapper::toEntity)
                .peek(entity -> {
                    if (literaryGenreRepository.existsByName(entity.getName())) {
                        throw new AlreadyExistsException("LiteraryGenre", entity.getName().name());
                    }
                })
                .toList();

        return literaryGenreRepository.saveAll(entities)
                .stream()
                .map(literaryGenreMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        if (!literaryGenreRepository.existsById(id)) {
            throw new DataNotFoundException("LiteraryGenre", String.valueOf(id));
        }
        literaryGenreRepository.deleteById(id);
    }
}
