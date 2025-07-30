package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.constant.LiteraryGenreCreateRequest;
import com.tgourouza.library_backend.dto.constant.LiteraryGenreDTO;
import com.tgourouza.library_backend.entity.LiteraryGenreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LiteraryGenreMapper {
    LiteraryGenreDTO toDTO(LiteraryGenreEntity entity);

    LiteraryGenreEntity toEntity(LiteraryGenreCreateRequest request);
}
