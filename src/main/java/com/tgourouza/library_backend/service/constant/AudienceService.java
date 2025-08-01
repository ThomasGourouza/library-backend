package com.tgourouza.library_backend.service.constant;

import com.tgourouza.library_backend.dto.constant.AudienceCreateRequest;
import com.tgourouza.library_backend.dto.constant.AudienceDTO;
import com.tgourouza.library_backend.entity.constant.AudienceEntity;
import com.tgourouza.library_backend.exception.AlreadyExistsException;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.constant.AudienceMapper;
import com.tgourouza.library_backend.repository.AudienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudienceService {
    private final AudienceRepository audienceRepository;
    private final AudienceMapper audienceMapper;

    public AudienceService(AudienceRepository audienceRepository, AudienceMapper audienceMapper) {
        this.audienceRepository = audienceRepository;
        this.audienceMapper = audienceMapper;
    }

    public List<AudienceDTO> getAll() {
        return audienceRepository.findAll()
                .stream()
                .map(audienceMapper::toDTO)
                .toList();
    }

    public AudienceDTO getById(Long id) {
        return audienceRepository.findById(id)
                .map(audienceMapper::toDTO)
                .orElseThrow(() -> new DataNotFoundException("Audience", String.valueOf(id)));
    }

    public AudienceDTO save(AudienceCreateRequest request) {
        AudienceEntity entity = audienceMapper.toEntity(request);
        if (audienceRepository.existsByName(entity.getName())) {
            throw new AlreadyExistsException("Audience", entity.getName().name());
        }
        AudienceEntity saved = audienceRepository.save(entity);
        return audienceMapper.toDTO(saved);
    }

    public List<AudienceDTO> saveAll(List<AudienceCreateRequest> requests) {
        List<AudienceEntity> entities = requests.stream()
                .map(audienceMapper::toEntity)
                .peek(entity -> {
                    if (audienceRepository.existsByName(entity.getName())) {
                        throw new AlreadyExistsException("Audience", entity.getName().name());
                    }
                })
                .toList();
        return audienceRepository.saveAll(entities)
                .stream()
                .map(audienceMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        if (!audienceRepository.existsById(id)) {
            throw new DataNotFoundException("Audience", String.valueOf(id));
        }
        audienceRepository.deleteById(id);
    }
}
