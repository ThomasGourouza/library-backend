package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    Optional<StatusEntity> findByStatus(Status status);
}
