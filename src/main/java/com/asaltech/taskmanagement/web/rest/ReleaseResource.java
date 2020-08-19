package com.asaltech.taskmanagement.web.rest;

import com.asaltech.taskmanagement.security.AuthoritiesConstants;
import com.asaltech.taskmanagement.service.ReleaseService;
import com.asaltech.taskmanagement.service.dto.ReleaseDTO;
import com.asaltech.taskmanagement.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.asaltech.taskmanagement.domain.Release}.
 */
@RestController
@RequestMapping("/api")
public class ReleaseResource {

    private final Logger log = LoggerFactory.getLogger(ReleaseResource.class);

    private static final String ENTITY_NAME = "release";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReleaseService releaseService;

    public ReleaseResource(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    /**
     * {@code POST  /releases} : Create a new release.
     *
     * @param releaseDTO the releaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new releaseDTO, or with status {@code 400 (Bad Request)} if the release has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/releases")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.LEAD + "\")")
    public ResponseEntity<ReleaseDTO> createRelease(@Valid @RequestBody ReleaseDTO releaseDTO) throws URISyntaxException {
        log.debug("REST request to save Release : {}", releaseDTO);
        if (releaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new release cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReleaseDTO result = releaseService.save(releaseDTO);
        return ResponseEntity.created(new URI("/api/releases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /releases} : Updates an existing release.
     *
     * @param releaseDTO the releaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated releaseDTO,
     * or with status {@code 400 (Bad Request)} if the releaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the releaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/releases")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.LEAD + "\")")
    public ResponseEntity<?> updateRelease(@Valid @RequestBody ReleaseDTO releaseDTO) throws URISyntaxException {
        log.debug("REST request to update Release : {}", releaseDTO);
        if (releaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        return releaseService.findOne(releaseDTO.getId()).map(persistentReleaseDTO -> {
            if (releaseService.isReleaseOwnerOrAdmin(persistentReleaseDTO)) {
                ReleaseDTO result = releaseService.save(releaseDTO);
                return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, releaseDTO.getId()))
                    .body(result);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /releases} : get all the releases.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of releases in body.
     */
    @GetMapping("/releases")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public List<ReleaseDTO> getAllReleases(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Releases");
        return releaseService.findAuthorized();
    }

    /**
     * {@code GET  /releases/:id} : get the "id" release.
     *
     * @param id the id of the releaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the releaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/releases/{id}")
    public ResponseEntity<ReleaseDTO> getRelease(@PathVariable String id) {
        log.debug("REST request to get Release : {}", id);
        Optional<ReleaseDTO> releaseDTO = releaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(releaseDTO);
    }

    /**
     * {@code DELETE  /releases/:id} : delete the "id" release.
     *
     * @param id the id of the releaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/releases/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.LEAD + "\")")
    public ResponseEntity<Object> deleteRelease(@PathVariable String id) {
        log.debug("REST request to delete Release : {}", id);
        return releaseService.findOne(id).map(releaseDTO -> {
            if (releaseService.isReleaseOwnerOrAdmin(releaseDTO)) {
                releaseService.delete(id);
                return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
