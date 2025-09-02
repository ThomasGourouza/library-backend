package com.tgourouza.library_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgourouza.library_backend.dto.Multilingual;
import com.tgourouza.library_backend.entity.CityEntity;
import com.tgourouza.library_backend.repository.CityRepository;

@Service
@Transactional
public class MultilingualService {

    private static final Set<String> SUPPORTED = Set.of("en","fr","es","it","de","pt","ru","ja");

    private final CityRepository cityRepository;

    public MultilingualService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Multilingual createCity(Multilingual request) {
        CityEntity entity = CityEntity.of(request);
        return cityRepository.save(entity).toDto();
    }

    @Transactional(readOnly = true)
    public List<Multilingual> getAllCities() {
        return cityRepository.findAll().stream()
                .map(CityEntity::toDto)
                .toList();
    }

    public Optional<Multilingual> findByEnglishIgnoreCase(String english) {
        if (english == null || english.isBlank()) {
            throw new IllegalArgumentException("english must not be blank");
        }
        return cityRepository.findByEnglishIgnoreCase(english.trim())
                .map(CityEntity::toDto);
    }

    public Optional<String> translateFromEnglish(String englishValue, String isoTargetLanguage) {
        if (englishValue == null || englishValue.isBlank() || isoTargetLanguage == null || isoTargetLanguage.isBlank()) {
            throw new IllegalArgumentException("englishValue and isoTargetLanguage must not be blank");
        }
        if (!SUPPORTED.contains(isoTargetLanguage)) {
            throw new IllegalArgumentException("Unsupported targetLanguage: " + isoTargetLanguage);
        }
        return cityRepository.translate(englishValue.trim(), isoTargetLanguage);
    }

    @Transactional
    public long deleteCityByEnglishIgnoreCase(String english) {
        if (english == null || english.isBlank()) {
            throw new IllegalArgumentException("english must not be blank");
        }
        return cityRepository.deleteByEnglishIgnoreCase(english.trim());
    }
}
