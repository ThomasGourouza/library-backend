package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.constant.CountryCreateRequest;
import com.tgourouza.library_backend.dto.constant.CountryDTO;
import com.tgourouza.library_backend.entity.constant.CountryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDTO toDTO(CountryEntity entity);

    CountryEntity toEntity(CountryCreateRequest request);
}
