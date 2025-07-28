package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByOriginalTitleContainingIgnoreCase(String keyword);
}
