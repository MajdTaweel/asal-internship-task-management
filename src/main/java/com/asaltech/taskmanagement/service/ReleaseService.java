package com.asaltech.taskmanagement.service;

import com.asaltech.taskmanagement.service.dto.ReleaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.asaltech.taskmanagement.domain.Release}.
 */
public interface ReleaseService {

    /**
     * Save a release.
     *
     * @param releaseDTO the entity to save.
     * @return the persisted entity.
     */
    ReleaseDTO save(ReleaseDTO releaseDTO);

    /**
     * Get all the releases.
     *
     * @return the list of entities.
     */
    List<ReleaseDTO> findAll();

    /**
     * Get all the releases with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ReleaseDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" release.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReleaseDTO> findOne(String id);

    /**
     * Delete the "id" release.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
