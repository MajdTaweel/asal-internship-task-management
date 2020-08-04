package com.asaltech.taskmanagement.service.impl;

import com.asaltech.taskmanagement.domain.Release;
import com.asaltech.taskmanagement.repository.ReleaseRepository;
import com.asaltech.taskmanagement.service.ReleaseService;
import com.asaltech.taskmanagement.service.dto.ReleaseDTO;
import com.asaltech.taskmanagement.service.mapper.ReleaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Release}.
 */
@Service
public class ReleaseServiceImpl implements ReleaseService {

    private final Logger log = LoggerFactory.getLogger(ReleaseServiceImpl.class);

    private final ReleaseRepository releaseRepository;

    private final ReleaseMapper releaseMapper;

    public ReleaseServiceImpl(ReleaseRepository releaseRepository, ReleaseMapper releaseMapper) {
        this.releaseRepository = releaseRepository;
        this.releaseMapper = releaseMapper;
    }

    @Override
    public ReleaseDTO save(ReleaseDTO releaseDTO) {
        log.debug("Request to save Release : {}", releaseDTO);
        Release release = releaseMapper.toEntity(releaseDTO);
        release = releaseRepository.save(release);
        return releaseMapper.toDto(release);
    }

    @Override
    public List<ReleaseDTO> findAll() {
        log.debug("Request to get all Releases");
        return releaseRepository.findAllWithEagerRelationships().stream()
            .map(releaseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    public Page<ReleaseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return releaseRepository.findAllWithEagerRelationships(pageable).map(releaseMapper::toDto);
    }

    @Override
    public Optional<ReleaseDTO> findOne(String id) {
        log.debug("Request to get Release : {}", id);
        return releaseRepository.findOneWithEagerRelationships(id)
            .map(releaseMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Release : {}", id);
        releaseRepository.deleteById(id);
    }
}
