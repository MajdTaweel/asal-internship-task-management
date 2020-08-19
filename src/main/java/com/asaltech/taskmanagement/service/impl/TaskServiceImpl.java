package com.asaltech.taskmanagement.service.impl;

import com.asaltech.taskmanagement.domain.Release;
import com.asaltech.taskmanagement.domain.Task;
import com.asaltech.taskmanagement.repository.ReleaseRepository;
import com.asaltech.taskmanagement.repository.TaskRepository;
import com.asaltech.taskmanagement.security.AuthoritiesConstants;
import com.asaltech.taskmanagement.service.ReleaseService;
import com.asaltech.taskmanagement.service.TaskService;
import com.asaltech.taskmanagement.service.dto.ReleaseDTO;
import com.asaltech.taskmanagement.service.dto.TaskDTO;
import com.asaltech.taskmanagement.service.mapper.ReleaseMapper;
import com.asaltech.taskmanagement.service.mapper.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Task}.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final ReleaseService releaseService;

    private final ReleaseMapper releaseMapper;

    private final ReleaseRepository releaseRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ReleaseService releaseService, ReleaseMapper releaseMapper, ReleaseRepository releaseRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.releaseService = releaseService;
        this.releaseMapper = releaseMapper;
        this.releaseRepository = releaseRepository;
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        log.debug("Request to save Task : {}", taskDTO);
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        Optional<Release> release = releaseRepository.findById(taskDTO.getReleaseId());
        if (release.isPresent()) {
            release.get().addTask(task);
            releaseRepository.save(release.get());
        }
        return taskMapper.toDto(task);
    }

    @Override
    public List<TaskDTO> findAll() {
        log.debug("Request to get all Tasks");
        return taskRepository.findAllWithEagerRelationships().stream()
            .map(taskMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    public Page<TaskDTO> findAllWithEagerRelationships(Pageable pageable) {
        return taskRepository.findAllWithEagerRelationships(pageable).map(taskMapper::toDto);
    }

    @Override
    public Optional<TaskDTO> findOne(String id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findOneWithEagerRelationships(id)
            .map(taskMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTO> findAllByReleaseEquals(String releaseId) {
        return releaseService.findOne(releaseId)
            .map(releaseDTO -> taskRepository.findAllByReleaseEquals(releaseMapper.toEntity(releaseDTO))
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList()))
            .orElse(new ArrayList<>());
    }

    public boolean isReleaseTeamMemberOrAdmin(ReleaseDTO releaseDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN);
        if (userDetails.getAuthorities().contains(adminAuthority)) {
            return true;
        }
        return releaseDTO.getTeam()
            .stream()
            .anyMatch(userDTO -> userDTO.getLogin().equalsIgnoreCase(userDetails.getUsername()));
    }
}
