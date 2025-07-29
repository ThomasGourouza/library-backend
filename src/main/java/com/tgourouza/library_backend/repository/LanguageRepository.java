package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, UUID> {
}
