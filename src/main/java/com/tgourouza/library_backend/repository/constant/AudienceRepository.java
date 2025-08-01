package com.tgourouza.library_backend.repository.constant;

import com.tgourouza.library_backend.constant.Audience;
import com.tgourouza.library_backend.entity.constant.AudienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AudienceRepository extends JpaRepository<AudienceEntity, Long> {
    Optional<AudienceEntity> findByName(Audience name);
}
