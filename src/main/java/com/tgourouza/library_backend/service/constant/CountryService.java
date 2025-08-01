package com.tgourouza.library_backend.service.constant;

import com.tgourouza.library_backend.dto.constant.CountryCreateRequest;
import com.tgourouza.library_backend.dto.constant.CountryDTO;
import com.tgourouza.library_backend.entity.constant.CountryEntity;
import com.tgourouza.library_backend.exception.AlreadyExistsException;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.constant.CountryMapper;
import com.tgourouza.library_backend.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    public List<CountryDTO> getAll() {
        return countryRepository.findAll()
                .stream()
                .map(countryMapper::toDTO)
                .toList();
    }

    public CountryDTO getById(Long id) {
        return countryRepository.findById(id)
                .map(countryMapper::toDTO)
                .orElseThrow(() -> new DataNotFoundException("Country", String.valueOf(id)));
    }

    public CountryDTO save(CountryCreateRequest request) {
        CountryEntity entity = countryMapper.toEntity(request);
        if (countryRepository.existsByName(entity.getName())) {
            throw new AlreadyExistsException("Country", entity.getName().name());
        }
        CountryEntity saved = countryRepository.save(entity);
        return countryMapper.toDTO(saved);
    }

    public List<CountryDTO> saveAll(List<CountryCreateRequest> requests) {
        List<CountryEntity> entities = requests.stream()
                .map(countryMapper::toEntity)
                .peek(entity -> {
                    if (countryRepository.existsByName(entity.getName())) {
                        throw new AlreadyExistsException("Country", entity.getName().name());
                    }
                })
                .toList();
        return countryRepository.saveAll(entities)
                .stream()
                .map(countryMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new DataNotFoundException("Country", String.valueOf(id));
        }
        countryRepository.deleteById(id);
    }
}
