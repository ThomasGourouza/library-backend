package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.constant.Type;
import com.tgourouza.library_backend.entity.constant.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long> {
    boolean existsByName(Type name);
}
