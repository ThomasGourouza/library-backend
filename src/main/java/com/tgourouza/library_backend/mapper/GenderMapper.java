package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.constant.GenderCreateRequest;
import com.tgourouza.library_backend.dto.constant.GenderDTO;
import com.tgourouza.library_backend.entity.GenderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenderMapper {
    GenderDTO toDTO(GenderEntity entity);

    GenderEntity toEntity(GenderCreateRequest request);
}
