package com.tgourouza.library_backend.repository.constant;

import com.tgourouza.library_backend.constant.Status;
import com.tgourouza.library_backend.entity.constant.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    Optional<StatusEntity> findByName(Status name);
}
