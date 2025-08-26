package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {

    @Query("select b.id from Book b where b.oLKey = :olKey")
    Optional<UUID> getEntityId(@Param("olKey") String oLKey);
}
