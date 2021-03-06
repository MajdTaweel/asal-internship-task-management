package com.asaltech.taskmanagement.web.rest;

import com.asaltech.taskmanagement.security.AuthoritiesConstants;
import com.asaltech.taskmanagement.service.ReleaseService;
import com.asaltech.taskmanagement.service.TaskService;
import com.asaltech.taskmanagement.service.dto.ReleaseDTO;
import com.asaltech.taskmanagement.service.dto.TaskDTO;
import com.asaltech.taskmanagement.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link com.asaltech.taskmanagement.domain.Task}.
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class TaskResource {

    private final Logger log = LoggerFactory.getLogger(TaskResource.class);

    private static final String ENTITY_NAME = "task";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskService taskService;

    private final ReleaseService releaseService;

    public TaskResource(TaskService taskService, ReleaseService releaseService) {
        this.taskService = taskService;
        this.releaseService = releaseService;
    }

    /**
     * {@code POST  /tasks} : Create a new task.
     *
     * @param taskDTO the taskDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskDTO, or with status {@code 400 (Bad Request)} if the task has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {
        log.debug("REST request to save Task : {}", taskDTO);
        if (taskDTO.getId() != null) {
            throw new BadRequestAlertException("A new task cannot already have an ID", ENTITY_NAME, "idexists");
        }
        checkReleaseTeamMembership(taskDTO.getReleaseId());
        TaskDTO result = taskService.save(taskDTO);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks} : Updates an existing task.
     *
     * @param taskDTO the taskDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskDTO,
     * or with status {@code 400 (Bad Request)} if the taskDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {
        log.debug("REST request to update Task : {}", taskDTO);
        if (taskDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        checkReleaseTeamMembership(taskDTO.getReleaseId());
        TaskDTO result = taskService.save(taskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /tasks} : get all the tasks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tasks in body.
     */
    @GetMapping("/tasks")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<TaskDTO> getAllTasks(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Tasks");
        return taskService.findAll();
    }

    @GetMapping("/release/{releaseId}/tasks")
    public List<TaskDTO> getAllReleaseTasks(@PathVariable String releaseId) {
        log.debug("REST request to get all Release Tasks");
        checkReleaseTeamMembership(releaseId);
        return taskService.findAllByReleaseEquals(releaseId);
    }

    /**
     * {@code GET  /tasks/:id} : get the "id" task.
     *
     * @param id the id of the taskDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable String id) {
        log.debug("REST request to get Task : {}", id);
        return taskService.findOne(id)
            .map(taskDTO -> {
                checkReleaseTeamMembership(taskDTO.getReleaseId());
                return ResponseEntity.ok().headers((HttpHeaders) null).body(taskDTO);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * {@code DELETE  /tasks/:id} : delete the "id" task.
     *
     * @param id the id of the taskDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable String id) {
        log.debug("REST request to delete Task : {}", id);
        return taskService.findOne(id)
            .map(taskDTO -> {
                checkReleaseTeamMembership(taskDTO.getReleaseId());
                taskService.delete(id);
                return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private void checkReleaseTeamMembership(String releaseId) {
        if (releaseId == null) {
            throw new BadRequestAlertException("A new task should have a release ID", ENTITY_NAME, "releaseidnull");
        }
        ReleaseDTO releaseDTO = releaseService.findOne(releaseId)
            .orElseThrow(() -> {
                throw new BadRequestAlertException("A task should contain a release ID for an existing release", ENTITY_NAME, "releasenotfound");
            });
        if (!taskService.isReleaseTeamMemberOrAdmin(releaseDTO)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You should be a member of the release's team to access/edit a task in this release");
        }
    }
}
