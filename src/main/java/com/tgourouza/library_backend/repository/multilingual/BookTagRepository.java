package com.tgourouza.library_backend.repository.multilingual;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tgourouza.library_backend.entity.multilingual.BookTagEntity;

public interface BookTagRepository extends JpaRepository<BookTagEntity, UUID> {

    Optional<BookTagEntity> findByEnglishIgnoreCase(String english);

    long deleteByEnglishIgnoreCase(String english);

    @Query("""
        select
          case lower(:targetLang)
            when 'en' then c.english
            when 'fr' then c.french
            when 'es' then c.spanish
            when 'it' then c.italian
            when 'de' then c.german
            when 'pt' then c.portuguese
            when 'ru' then c.russian
            when 'ja' then c.japanese
            else null
          end
        from BookTagEntity c
        where
          lower(c.english) = lower(:englishValue)
        """)
    Optional<String> translate(
        @Param("englishValue") String englishValue,
        @Param("targetLang") String targetLanguage
    );
}
