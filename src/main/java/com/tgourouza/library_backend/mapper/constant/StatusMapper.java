package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.constant.StatusCreateRequest;
import com.tgourouza.library_backend.dto.constant.StatusDTO;
import com.tgourouza.library_backend.entity.constant.StatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    StatusDTO toDTO(StatusEntity entity);

    StatusEntity toEntity(StatusCreateRequest request);
}
