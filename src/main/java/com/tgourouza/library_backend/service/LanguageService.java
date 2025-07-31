package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.constant.LanguageCreateRequest;
import com.tgourouza.library_backend.dto.constant.LanguageDTO;
import com.tgourouza.library_backend.entity.constant.LanguageEntity;
import com.tgourouza.library_backend.exception.AlreadyExistsException;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.constant.LanguageMapper;
import com.tgourouza.library_backend.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    public LanguageService(LanguageRepository languageRepository, LanguageMapper languageMapper) {
        this.languageRepository = languageRepository;
        this.languageMapper = languageMapper;
    }

    public List<LanguageDTO> getAll() {
        return languageRepository.findAll()
                .stream()
                .map(languageMapper::toDTO)
                .toList();
    }

    public LanguageDTO getById(Long id) {
        return languageRepository.findById(id)
                .map(languageMapper::toDTO)
                .orElseThrow(() -> new DataNotFoundException("Language", String.valueOf(id)));
    }

    public LanguageDTO save(LanguageCreateRequest request) {
        LanguageEntity entity = languageMapper.toEntity(request);

        if (languageRepository.existsByName(entity.getName())) {
            throw new AlreadyExistsException("Language", entity.getName().name());
        }

        LanguageEntity saved = languageRepository.save(entity);
        return languageMapper.toDTO(saved);
    }

    public List<LanguageDTO> saveAll(List<LanguageCreateRequest> requests) {
        List<LanguageEntity> entities = requests.stream()
                .map(languageMapper::toEntity)
                .peek(entity -> {
                    if (languageRepository.existsByName(entity.getName())) {
                        throw new AlreadyExistsException("Language", entity.getName().name());
                    }
                })
                .toList();

        return languageRepository.saveAll(entities)
                .stream()
                .map(languageMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        if (!languageRepository.existsById(id)) {
            throw new DataNotFoundException("Language", String.valueOf(id));
        }
        languageRepository.deleteById(id);
    }
}
