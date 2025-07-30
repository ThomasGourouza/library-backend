package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.entity.LiteraryMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiteraryMovementRepository extends JpaRepository<LiteraryMovementEntity, Long> {
}
