package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.constant.LiteraryMovementCreateRequest;
import com.tgourouza.library_backend.dto.constant.LiteraryMovementDTO;
import com.tgourouza.library_backend.entity.LiteraryMovementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LiteraryMovementMapper {
    LiteraryMovementDTO toDTO(LiteraryMovementEntity entity);

    LiteraryMovementEntity toEntity(LiteraryMovementCreateRequest request);
}
