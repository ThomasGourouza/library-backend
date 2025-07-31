package com.tgourouza.library_backend.mapper.constant;

import com.tgourouza.library_backend.dto.constant.TypeCreateRequest;
import com.tgourouza.library_backend.dto.constant.TypeDTO;
import com.tgourouza.library_backend.entity.constant.TypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    TypeDTO toDTO(TypeEntity entity);

    TypeEntity toEntity(TypeCreateRequest request);
}
