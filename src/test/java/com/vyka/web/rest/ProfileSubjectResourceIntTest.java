package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.ProfileSubject;
import com.vyka.repository.ProfileSubjectRepository;
import com.vyka.service.ProfileSubjectService;
import com.vyka.repository.search.ProfileSubjectSearchRepository;
import com.vyka.service.dto.ProfileSubjectDTO;
import com.vyka.service.mapper.ProfileSubjectMapper;
import com.vyka.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.vyka.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProfileSubjectResource REST controller.
 *
 * @see ProfileSubjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class ProfileSubjectResourceIntTest {

    @Autowired
    private ProfileSubjectRepository profileSubjectRepository;

    @Autowired
    private ProfileSubjectMapper profileSubjectMapper;

    @Autowired
    private ProfileSubjectService profileSubjectService;

    @Autowired
    private ProfileSubjectSearchRepository profileSubjectSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfileSubjectMockMvc;

    private ProfileSubject profileSubject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileSubjectResource profileSubjectResource = new ProfileSubjectResource(profileSubjectService);
        this.restProfileSubjectMockMvc = MockMvcBuilders.standaloneSetup(profileSubjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileSubject createEntity(EntityManager em) {
        ProfileSubject profileSubject = new ProfileSubject();
        return profileSubject;
    }

    @Before
    public void initTest() {
        profileSubjectSearchRepository.deleteAll();
        profileSubject = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfileSubject() throws Exception {
        int databaseSizeBeforeCreate = profileSubjectRepository.findAll().size();

        // Create the ProfileSubject
        ProfileSubjectDTO profileSubjectDTO = profileSubjectMapper.toDto(profileSubject);
        restProfileSubjectMockMvc.perform(post("/api/profile-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSubjectDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileSubject in the database
        List<ProfileSubject> profileSubjectList = profileSubjectRepository.findAll();
        assertThat(profileSubjectList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileSubject testProfileSubject = profileSubjectList.get(profileSubjectList.size() - 1);

        // Validate the ProfileSubject in Elasticsearch
        ProfileSubject profileSubjectEs = profileSubjectSearchRepository.findOne(testProfileSubject.getId());
        assertThat(profileSubjectEs).isEqualToComparingFieldByField(testProfileSubject);
    }

    @Test
    @Transactional
    public void createProfileSubjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileSubjectRepository.findAll().size();

        // Create the ProfileSubject with an existing ID
        profileSubject.setId(1L);
        ProfileSubjectDTO profileSubjectDTO = profileSubjectMapper.toDto(profileSubject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileSubjectMockMvc.perform(post("/api/profile-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSubjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileSubject in the database
        List<ProfileSubject> profileSubjectList = profileSubjectRepository.findAll();
        assertThat(profileSubjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfileSubjects() throws Exception {
        // Initialize the database
        profileSubjectRepository.saveAndFlush(profileSubject);

        // Get all the profileSubjectList
        restProfileSubjectMockMvc.perform(get("/api/profile-subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileSubject.getId().intValue())));
    }

    @Test
    @Transactional
    public void getProfileSubject() throws Exception {
        // Initialize the database
        profileSubjectRepository.saveAndFlush(profileSubject);

        // Get the profileSubject
        restProfileSubjectMockMvc.perform(get("/api/profile-subjects/{id}", profileSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileSubject.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProfileSubject() throws Exception {
        // Get the profileSubject
        restProfileSubjectMockMvc.perform(get("/api/profile-subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileSubject() throws Exception {
        // Initialize the database
        profileSubjectRepository.saveAndFlush(profileSubject);
        profileSubjectSearchRepository.save(profileSubject);
        int databaseSizeBeforeUpdate = profileSubjectRepository.findAll().size();

        // Update the profileSubject
        ProfileSubject updatedProfileSubject = profileSubjectRepository.findOne(profileSubject.getId());
        ProfileSubjectDTO profileSubjectDTO = profileSubjectMapper.toDto(updatedProfileSubject);

        restProfileSubjectMockMvc.perform(put("/api/profile-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSubjectDTO)))
            .andExpect(status().isOk());

        // Validate the ProfileSubject in the database
        List<ProfileSubject> profileSubjectList = profileSubjectRepository.findAll();
        assertThat(profileSubjectList).hasSize(databaseSizeBeforeUpdate);
        ProfileSubject testProfileSubject = profileSubjectList.get(profileSubjectList.size() - 1);

        // Validate the ProfileSubject in Elasticsearch
        ProfileSubject profileSubjectEs = profileSubjectSearchRepository.findOne(testProfileSubject.getId());
        assertThat(profileSubjectEs).isEqualToComparingFieldByField(testProfileSubject);
    }

    @Test
    @Transactional
    public void updateNonExistingProfileSubject() throws Exception {
        int databaseSizeBeforeUpdate = profileSubjectRepository.findAll().size();

        // Create the ProfileSubject
        ProfileSubjectDTO profileSubjectDTO = profileSubjectMapper.toDto(profileSubject);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfileSubjectMockMvc.perform(put("/api/profile-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSubjectDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileSubject in the database
        List<ProfileSubject> profileSubjectList = profileSubjectRepository.findAll();
        assertThat(profileSubjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfileSubject() throws Exception {
        // Initialize the database
        profileSubjectRepository.saveAndFlush(profileSubject);
        profileSubjectSearchRepository.save(profileSubject);
        int databaseSizeBeforeDelete = profileSubjectRepository.findAll().size();

        // Get the profileSubject
        restProfileSubjectMockMvc.perform(delete("/api/profile-subjects/{id}", profileSubject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean profileSubjectExistsInEs = profileSubjectSearchRepository.exists(profileSubject.getId());
        assertThat(profileSubjectExistsInEs).isFalse();

        // Validate the database is empty
        List<ProfileSubject> profileSubjectList = profileSubjectRepository.findAll();
        assertThat(profileSubjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProfileSubject() throws Exception {
        // Initialize the database
        profileSubjectRepository.saveAndFlush(profileSubject);
        profileSubjectSearchRepository.save(profileSubject);

        // Search the profileSubject
        restProfileSubjectMockMvc.perform(get("/api/_search/profile-subjects?query=id:" + profileSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileSubject.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileSubject.class);
        ProfileSubject profileSubject1 = new ProfileSubject();
        profileSubject1.setId(1L);
        ProfileSubject profileSubject2 = new ProfileSubject();
        profileSubject2.setId(profileSubject1.getId());
        assertThat(profileSubject1).isEqualTo(profileSubject2);
        profileSubject2.setId(2L);
        assertThat(profileSubject1).isNotEqualTo(profileSubject2);
        profileSubject1.setId(null);
        assertThat(profileSubject1).isNotEqualTo(profileSubject2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileSubjectDTO.class);
        ProfileSubjectDTO profileSubjectDTO1 = new ProfileSubjectDTO();
        profileSubjectDTO1.setId(1L);
        ProfileSubjectDTO profileSubjectDTO2 = new ProfileSubjectDTO();
        assertThat(profileSubjectDTO1).isNotEqualTo(profileSubjectDTO2);
        profileSubjectDTO2.setId(profileSubjectDTO1.getId());
        assertThat(profileSubjectDTO1).isEqualTo(profileSubjectDTO2);
        profileSubjectDTO2.setId(2L);
        assertThat(profileSubjectDTO1).isNotEqualTo(profileSubjectDTO2);
        profileSubjectDTO1.setId(null);
        assertThat(profileSubjectDTO1).isNotEqualTo(profileSubjectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profileSubjectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profileSubjectMapper.fromId(null)).isNull();
    }
}
