package com.asaltech.taskmanagement.repository;

import com.asaltech.taskmanagement.domain.Release;
import com.asaltech.taskmanagement.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Release entity.
 */
@Repository
public interface ReleaseRepository extends MongoRepository<Release, String> {

    @Query("{}")
    Page<Release> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Release> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Release> findOneWithEagerRelationships(String id);

    List<Release> findAllByTeamContains(User user);
}
