package com.tgourouza.library_backend.service.constant;

import com.tgourouza.library_backend.dto.constant.TypeCreateRequest;
import com.tgourouza.library_backend.dto.constant.TypeDTO;
import com.tgourouza.library_backend.entity.constant.TypeEntity;
import com.tgourouza.library_backend.exception.AlreadyExistsException;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.constant.TypeMapper;
import com.tgourouza.library_backend.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    private final TypeRepository typeRepository;
    private final TypeMapper literaryGenreMapper;

    public TypeService(TypeRepository typeRepository, TypeMapper literaryGenreMapper) {
        this.typeRepository = typeRepository;
        this.literaryGenreMapper = literaryGenreMapper;
    }

    public List<TypeDTO> getAll() {
        return typeRepository.findAll()
                .stream()
                .map(literaryGenreMapper::toDTO)
                .toList();
    }

    public TypeDTO getById(Long id) {
        return typeRepository.findById(id)
                .map(literaryGenreMapper::toDTO)
                .orElseThrow(() -> new DataNotFoundException("LiteraryGenre", String.valueOf(id)));
    }

    public TypeDTO save(TypeCreateRequest request) {
        TypeEntity entity = literaryGenreMapper.toEntity(request);

        if (typeRepository.existsByName(entity.getName())) {
            throw new AlreadyExistsException("LiteraryGenre", entity.getName().name());
        }

        TypeEntity saved = typeRepository.save(entity);
        return literaryGenreMapper.toDTO(saved);
    }

    public List<TypeDTO> saveAll(List<TypeCreateRequest> requests) {
        List<TypeEntity> entities = requests.stream()
                .map(literaryGenreMapper::toEntity)
                .peek(entity -> {
                    if (typeRepository.existsByName(entity.getName())) {
                        throw new AlreadyExistsException("LiteraryGenre", entity.getName().name());
                    }
                })
                .toList();

        return typeRepository.saveAll(entities)
                .stream()
                .map(literaryGenreMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        if (!typeRepository.existsById(id)) {
            throw new DataNotFoundException("LiteraryGenre", String.valueOf(id));
        }
        typeRepository.deleteById(id);
    }
}
