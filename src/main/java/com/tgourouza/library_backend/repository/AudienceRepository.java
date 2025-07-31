package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.constant.Audience;
import com.tgourouza.library_backend.entity.constant.AudienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienceRepository extends JpaRepository<AudienceEntity, Long> {
    boolean existsByName(Audience name);
}
