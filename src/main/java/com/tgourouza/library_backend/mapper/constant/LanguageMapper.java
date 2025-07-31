package com.tgourouza.library_backend.mapper.constant;

import com.tgourouza.library_backend.dto.constant.LanguageCreateRequest;
import com.tgourouza.library_backend.dto.constant.LanguageDTO;
import com.tgourouza.library_backend.entity.constant.LanguageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    LanguageDTO toDTO(LanguageEntity entity);

    LanguageEntity toEntity(LanguageCreateRequest request);
}
