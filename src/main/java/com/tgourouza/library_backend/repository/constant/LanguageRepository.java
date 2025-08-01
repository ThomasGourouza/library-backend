package com.tgourouza.library_backend.repository.constant;

import com.tgourouza.library_backend.constant.Language;
import com.tgourouza.library_backend.entity.constant.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {
    Optional<LanguageEntity> findByName(Language name);
}
