package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.constant.LiteraryMovementCreateRequest;
import com.tgourouza.library_backend.dto.constant.LiteraryMovementDTO;
import com.tgourouza.library_backend.entity.LiteraryMovementEntity;
import com.tgourouza.library_backend.exception.AlreadyExistsException;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.LiteraryMovementMapper;
import com.tgourouza.library_backend.repository.LiteraryMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiteraryMovementService {
    private final LiteraryMovementRepository literaryMovementRepository;
    private final LiteraryMovementMapper literaryMovementMapper;

    public LiteraryMovementService(LiteraryMovementRepository literaryMovementRepository, LiteraryMovementMapper literaryMovementMapper) {
        this.literaryMovementRepository = literaryMovementRepository;
        this.literaryMovementMapper = literaryMovementMapper;
    }

    public List<LiteraryMovementDTO> getAll() {
        return literaryMovementRepository.findAll()
                .stream()
                .map(literaryMovementMapper::toDTO)
                .toList();
    }

    public LiteraryMovementDTO getById(Long id) {
        return literaryMovementRepository.findById(id)
                .map(literaryMovementMapper::toDTO)
                .orElseThrow(() -> new DataNotFoundException("LiteraryMovement", String.valueOf(id)));
    }

    public LiteraryMovementDTO save(LiteraryMovementCreateRequest request) {
        LiteraryMovementEntity entity = literaryMovementMapper.toEntity(request);

        if (literaryMovementRepository.existsByName(entity.getName())) {
            throw new AlreadyExistsException("LiteraryMovement", entity.getName().name());
        }

        LiteraryMovementEntity saved = literaryMovementRepository.save(entity);
        return literaryMovementMapper.toDTO(saved);
    }

    public List<LiteraryMovementDTO> saveAll(List<LiteraryMovementCreateRequest> requests) {
        List<LiteraryMovementEntity> entities = requests.stream()
                .map(literaryMovementMapper::toEntity)
                .peek(entity -> {
                    if (literaryMovementRepository.existsByName(entity.getName())) {
                        throw new AlreadyExistsException("LiteraryMovement", entity.getName().name());
                    }
                })
                .toList();

        return literaryMovementRepository.saveAll(entities)
                .stream()
                .map(literaryMovementMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        if (!literaryMovementRepository.existsById(id)) {
            throw new DataNotFoundException("LiteraryMovement", String.valueOf(id));
        }
        literaryMovementRepository.deleteById(id);
    }
}
