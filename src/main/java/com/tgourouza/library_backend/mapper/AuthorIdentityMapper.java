package com.tgourouza.library_backend.mapper;

import com.tgourouza.library_backend.dto.author.AuthorIdentityCreateRequest;
import com.tgourouza.library_backend.dto.author.AuthorIdentityDTO;
import com.tgourouza.library_backend.entity.AuthorEntity;
import com.tgourouza.library_backend.entity.CountryEntity;
import com.tgourouza.library_backend.entity.GenderEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorIdentityMapper {

    public AuthorIdentityDTO toDTO(AuthorEntity entity) {
        if (entity == null) return null;

        return new AuthorIdentityDTO(
                entity.getFirstName(),
                entity.getName(),
                entity.getGender() != null ? entity.getGender().getName() : null,
                entity.getCountry() != null ? entity.getCountry().getName() : null,
                entity.getBirthDate(),
                entity.getDeathDate()
        );
    }

    public void applyToEntity(AuthorEntity entity, AuthorIdentityCreateRequest request, GenderEntity gender, CountryEntity country) {
        if (entity == null || request == null) return;

        entity.setFirstName(request.getFirstName());
        entity.setName(request.getName());
        entity.setGender(gender);
        entity.setCountry(country);
        entity.setBirthDate(request.getBirthDate());
        entity.setDeathDate(request.getDeathDate());
    }
}
