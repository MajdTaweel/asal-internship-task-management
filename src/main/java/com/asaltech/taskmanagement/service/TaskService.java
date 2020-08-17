package com.asaltech.taskmanagement.service;

import com.asaltech.taskmanagement.service.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.asaltech.taskmanagement.domain.Task}.
 */
public interface TaskService {

    /**
     * Save a task.
     *
     * @param taskDTO the entity to save.
     * @return the persisted entity.
     */
    TaskDTO save(TaskDTO taskDTO);

    /**
     * Get all the tasks.
     *
     * @return the list of entities.
     */
    List<TaskDTO> findAll();

    /**
     * Get all the tasks with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TaskDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" task.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskDTO> findOne(String id);

    /**
     * Delete the "id" task.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    List<TaskDTO> findAllByReleaseEquals(String releaseId);
}
