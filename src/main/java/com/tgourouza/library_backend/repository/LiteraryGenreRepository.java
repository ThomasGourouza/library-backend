package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.constant.LiteraryGenre;
import com.tgourouza.library_backend.entity.LiteraryGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiteraryGenreRepository extends JpaRepository<LiteraryGenreEntity, Long> {
    boolean existsByName(LiteraryGenre name);
}
