package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.constant.CategoryCreateRequest;
import com.tgourouza.library_backend.dto.constant.CategoryDTO;
import com.tgourouza.library_backend.entity.constant.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(CategoryEntity entity);

    CategoryEntity toEntity(CategoryCreateRequest request);
}
