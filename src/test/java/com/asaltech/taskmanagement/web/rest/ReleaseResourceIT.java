package com.asaltech.taskmanagement.web.rest;

import com.asaltech.taskmanagement.TaskmanagementApp;
import com.asaltech.taskmanagement.domain.Release;
import com.asaltech.taskmanagement.domain.enumeration.ReleaseStatus;
import com.asaltech.taskmanagement.repository.ReleaseRepository;
import com.asaltech.taskmanagement.service.ReleaseService;
import com.asaltech.taskmanagement.service.dto.ReleaseDTO;
import com.asaltech.taskmanagement.service.mapper.ReleaseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link ReleaseResource} REST controller.
 */
@SpringBootTest(classes = TaskmanagementApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReleaseResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final ReleaseStatus DEFAULT_STATUS = ReleaseStatus.NEW;
    private static final ReleaseStatus UPDATED_STATUS = ReleaseStatus.IN_PROGRESS;

    private static final Instant DEFAULT_DEADLINE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEADLINE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ReleaseRepository releaseRepository;

    @Mock
    private ReleaseRepository releaseRepositoryMock;

    @Autowired
    private ReleaseMapper releaseMapper;

    @Mock
    private ReleaseService releaseServiceMock;

    @Autowired
    private ReleaseService releaseService;

    @Autowired
    private MockMvc restReleaseMockMvc;

    private Release release;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Release createEntity() {
        Release release = new Release()
            .title(DEFAULT_TITLE)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .deadline(DEFAULT_DEADLINE);
        return release;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Release createUpdatedEntity() {
        Release release = new Release()
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .deadline(UPDATED_DEADLINE);
        return release;
    }

    @BeforeEach
    public void initTest() {
        releaseRepository.deleteAll();
        release = createEntity();
    }

    @Test
    public void createRelease() throws Exception {
        int databaseSizeBeforeCreate = releaseRepository.findAll().size();
        // Create the Release
        ReleaseDTO releaseDTO = releaseMapper.toDto(release);
        restReleaseMockMvc.perform(post("/api/releases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(releaseDTO)))
            .andExpect(status().isCreated());

        // Validate the Release in the database
        List<Release> releaseList = releaseRepository.findAll();
        assertThat(releaseList).hasSize(databaseSizeBeforeCreate + 1);
        Release testRelease = releaseList.get(releaseList.size() - 1);
        assertThat(testRelease.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRelease.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRelease.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRelease.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
    }

    @Test
    public void createReleaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = releaseRepository.findAll().size();

        // Create the Release with an existing ID
        release.setId("existing_id");
        ReleaseDTO releaseDTO = releaseMapper.toDto(release);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReleaseMockMvc.perform(post("/api/releases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(releaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Release in the database
        List<Release> releaseList = releaseRepository.findAll();
        assertThat(releaseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = releaseRepository.findAll().size();
        // set the field null
        release.setTitle(null);

        // Create the Release, which fails.
        ReleaseDTO releaseDTO = releaseMapper.toDto(release);


        restReleaseMockMvc.perform(post("/api/releases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(releaseDTO)))
            .andExpect(status().isBadRequest());

        List<Release> releaseList = releaseRepository.findAll();
        assertThat(releaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = releaseRepository.findAll().size();
        // set the field null
        release.setType(null);

        // Create the Release, which fails.
        ReleaseDTO releaseDTO = releaseMapper.toDto(release);


        restReleaseMockMvc.perform(post("/api/releases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(releaseDTO)))
            .andExpect(status().isBadRequest());

        List<Release> releaseList = releaseRepository.findAll();
        assertThat(releaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = releaseRepository.findAll().size();
        // set the field null
        release.setStatus(null);

        // Create the Release, which fails.
        ReleaseDTO releaseDTO = releaseMapper.toDto(release);


        restReleaseMockMvc.perform(post("/api/releases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(releaseDTO)))
            .andExpect(status().isBadRequest());

        List<Release> releaseList = releaseRepository.findAll();
        assertThat(releaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllReleases() throws Exception {
        // Initialize the database
        releaseRepository.save(release);

        // Get all the releaseList
        restReleaseMockMvc.perform(get("/api/releases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(release.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllReleasesWithEagerRelationshipsIsEnabled() throws Exception {
        when(releaseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReleaseMockMvc.perform(get("/api/releases?eagerload=true"))
            .andExpect(status().isOk());

        verify(releaseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllReleasesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(releaseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReleaseMockMvc.perform(get("/api/releases?eagerload=true"))
            .andExpect(status().isOk());

        verify(releaseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getRelease() throws Exception {
        // Initialize the database
        releaseRepository.save(release);

        // Get the release
        restReleaseMockMvc.perform(get("/api/releases/{id}", release.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(release.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.deadline").value(DEFAULT_DEADLINE.toString()));
    }
    @Test
    public void getNonExistingRelease() throws Exception {
        // Get the release
        restReleaseMockMvc.perform(get("/api/releases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRelease() throws Exception {
        // Initialize the database
        releaseRepository.save(release);

        int databaseSizeBeforeUpdate = releaseRepository.findAll().size();

        // Update the release
        Release updatedRelease = releaseRepository.findById(release.getId()).get();
        updatedRelease
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .deadline(UPDATED_DEADLINE);
        ReleaseDTO releaseDTO = releaseMapper.toDto(updatedRelease);

        restReleaseMockMvc.perform(put("/api/releases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(releaseDTO)))
            .andExpect(status().isOk());

        // Validate the Release in the database
        List<Release> releaseList = releaseRepository.findAll();
        assertThat(releaseList).hasSize(databaseSizeBeforeUpdate);
        Release testRelease = releaseList.get(releaseList.size() - 1);
        assertThat(testRelease.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRelease.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRelease.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRelease.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    public void updateNonExistingRelease() throws Exception {
        int databaseSizeBeforeUpdate = releaseRepository.findAll().size();

        // Create the Release
        ReleaseDTO releaseDTO = releaseMapper.toDto(release);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReleaseMockMvc.perform(put("/api/releases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(releaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Release in the database
        List<Release> releaseList = releaseRepository.findAll();
        assertThat(releaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteRelease() throws Exception {
        // Initialize the database
        releaseRepository.save(release);

        int databaseSizeBeforeDelete = releaseRepository.findAll().size();

        // Delete the release
        restReleaseMockMvc.perform(delete("/api/releases/{id}", release.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Release> releaseList = releaseRepository.findAll();
        assertThat(releaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
