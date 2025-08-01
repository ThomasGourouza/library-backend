package com.tgourouza.library_backend.repository.constant;

import com.tgourouza.library_backend.constant.Category;
import com.tgourouza.library_backend.entity.constant.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(Category name);
}
