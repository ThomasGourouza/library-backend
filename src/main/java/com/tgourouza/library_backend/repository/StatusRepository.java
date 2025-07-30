package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    boolean existsByName(Status name);
}
