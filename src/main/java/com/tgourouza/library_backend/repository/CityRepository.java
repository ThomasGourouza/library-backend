package com.tgourouza.library_backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tgourouza.library_backend.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, UUID> {
    // Optional<CityEntity> findByEnglishIgnoreCase(String english);
}
