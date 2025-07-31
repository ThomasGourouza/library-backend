package com.tgourouza.library_backend.service.constant;

import com.tgourouza.library_backend.dto.constant.StatusDTO;
import com.tgourouza.library_backend.exception.DataNotFoundException;
import com.tgourouza.library_backend.mapper.constant.StatusMapper;
import com.tgourouza.library_backend.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    public StatusService(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    public List<StatusDTO> getAll() {
        return statusRepository.findAll()
                .stream()
                .map(statusMapper::toDTO)
                .toList();
    }

    public StatusDTO getById(Long id) {
        return statusRepository.findById(id)
                .map(statusMapper::toDTO)
                .orElseThrow(() -> new DataNotFoundException("Status", String.valueOf(id)));
    }

//    public StatusDTO save(StatusCreateRequest request) {
//        StatusEntity entity = statusMapper.toEntity(request);
//
//        if (statusRepository.existsByName(entity.getName())) {
//            throw new AlreadyExistsException("Status", entity.getName().name());
//        }
//
//        StatusEntity saved = statusRepository.save(entity);
//        return statusMapper.toDTO(saved);
//    }
//
//    public List<StatusDTO> saveAll(List<StatusCreateRequest> requests) {
//        List<StatusEntity> entities = requests.stream()
//                .map(statusMapper::toEntity)
//                .peek(entity -> {
//                    if (statusRepository.existsByName(entity.getName())) {
//                        throw new AlreadyExistsException("Status", entity.getName().name());
//                    }
//                })
//                .toList();
//
//        return statusRepository.saveAll(entities)
//                .stream()
//                .map(statusMapper::toDTO)
//                .toList();
//    }
//
//    public void deleteById(Long id) {
//        if (!statusRepository.existsById(id)) {
//            throw new DataNotFoundException("Status", String.valueOf(id));
//        }
//        statusRepository.deleteById(id);
//    }
}
