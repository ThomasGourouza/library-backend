package com.tgourouza.library_backend.repository;

import com.tgourouza.library_backend.constant.Country;
import com.tgourouza.library_backend.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    boolean existsByName(Country name);
}
