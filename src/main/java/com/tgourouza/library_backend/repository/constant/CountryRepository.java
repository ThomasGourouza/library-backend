package com.tgourouza.library_backend.repository.constant;

import com.tgourouza.library_backend.constant.Country;
import com.tgourouza.library_backend.entity.constant.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    Optional<CountryEntity> findByName(Country name);
}
