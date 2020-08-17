package com.asaltech.taskmanagement.repository;

import com.asaltech.taskmanagement.domain.Release;
import com.asaltech.taskmanagement.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Task entity.
 */
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    @Query("{}")
    Page<Task> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Task> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Task> findOneWithEagerRelationships(String id);

    List<Task> findAllByReleaseEquals(Release release);
}
