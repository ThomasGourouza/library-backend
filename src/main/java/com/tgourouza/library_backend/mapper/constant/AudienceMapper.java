package com.tgourouza.library_backend.mapper.constant;

import com.tgourouza.library_backend.dto.constant.AudienceCreateRequest;
import com.tgourouza.library_backend.dto.constant.AudienceDTO;
import com.tgourouza.library_backend.entity.constant.AudienceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AudienceMapper {
    AudienceDTO toDTO(AudienceEntity entity);

    AudienceEntity toEntity(AudienceCreateRequest request);
}
