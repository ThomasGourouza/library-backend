package com.tgourouza.library_backend.service;

import com.tgourouza.library_backend.dto.constant.CategoryCreateRequest;
import com.tgourouza.library_backend.dto.constant.CategoryDTO;
import com.tgourouza.library_backend.entity.CategoryEntity;
import com.tgourouza.library_backend.exception.AlreadyExistsException;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.CategoryMapper;
import com.tgourouza.library_backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    public CategoryDTO getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> new DataNotFoundException("Category", String.valueOf(id)));
    }

    public CategoryDTO save(CategoryCreateRequest request) {
        CategoryEntity entity = categoryMapper.toEntity(request);

        if (categoryRepository.existsByName(entity.getName())) {
            throw new AlreadyExistsException("Category", entity.getName().name());
        }

        CategoryEntity saved = categoryRepository.save(entity);
        return categoryMapper.toDTO(saved);
    }

    public List<CategoryDTO> saveAll(List<CategoryCreateRequest> requests) {
        List<CategoryEntity> entities = requests.stream()
                .map(categoryMapper::toEntity)
                .peek(entity -> {
                    if (categoryRepository.existsByName(entity.getName())) {
                        throw new AlreadyExistsException("Category", entity.getName().name());
                    }
                })
                .toList();

        return categoryRepository.saveAll(entities)
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new DataNotFoundException("Category", String.valueOf(id));
        }
        categoryRepository.deleteById(id);
    }
}
