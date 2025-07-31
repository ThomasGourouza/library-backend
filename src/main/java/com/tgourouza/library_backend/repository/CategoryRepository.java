package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.constant.Category;
import com.tgourouza.library_backend.entity.constant.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(Category name);
}
