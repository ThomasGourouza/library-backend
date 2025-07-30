package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.constant.Gender;
import com.tgourouza.library_backend.entity.GenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<GenderEntity, Long> {
    boolean existsByName(Gender name);
}
