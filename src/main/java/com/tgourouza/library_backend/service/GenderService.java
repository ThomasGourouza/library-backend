package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.constant.GenderCreateRequest;
import com.tgourouza.library_backend.dto.constant.GenderDTO;
import com.tgourouza.library_backend.entity.GenderEntity;
import com.tgourouza.library_backend.exception.AlreadyExistsException;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.GenderMapper;
import com.tgourouza.library_backend.repository.GenderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {
    private final GenderRepository genderRepository;
    private final GenderMapper genderMapper;

    public GenderService(GenderRepository genderRepository, GenderMapper genderMapper) {
        this.genderRepository = genderRepository;
        this.genderMapper = genderMapper;
    }

    public List<GenderDTO> getAll() {
        return genderRepository.findAll()
                .stream()
                .map(genderMapper::toDTO)
                .toList();
    }

    public GenderDTO getById(Long id) {
        return genderRepository.findById(id)
                .map(genderMapper::toDTO)
                .orElseThrow(() -> new DataNotFoundException("Gender", String.valueOf(id)));
    }

    public GenderDTO save(GenderCreateRequest request) {
        GenderEntity entity = genderMapper.toEntity(request);

        if (genderRepository.existsByName(entity.getName())) {
            throw new AlreadyExistsException("Gender", entity.getName().name());
        }

        GenderEntity saved = genderRepository.save(entity);
        return genderMapper.toDTO(saved);
    }

    public List<GenderDTO> saveAll(List<GenderCreateRequest> requests) {
        List<GenderEntity> entities = requests.stream()
                .map(genderMapper::toEntity)
                .peek(entity -> {
                    if (genderRepository.existsByName(entity.getName())) {
                        throw new AlreadyExistsException("Gender", entity.getName().name());
                    }
                })
                .toList();

        return genderRepository.saveAll(entities)
                .stream()
                .map(genderMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        if (!genderRepository.existsById(id)) {
            throw new DataNotFoundException("Gender", String.valueOf(id));
        }
        genderRepository.deleteById(id);
    }
}
