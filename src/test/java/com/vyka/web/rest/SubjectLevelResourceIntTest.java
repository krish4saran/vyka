package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.SubjectLevel;
import com.vyka.repository.SubjectLevelRepository;
import com.vyka.service.SubjectLevelService;
import com.vyka.repository.search.SubjectLevelSearchRepository;
import com.vyka.service.dto.SubjectLevelDTO;
import com.vyka.service.mapper.SubjectLevelMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.vyka.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vyka.domain.enumeration.LevelValue;
/**
 * Test class for the SubjectLevelResource REST controller.
 *
 * @see SubjectLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class SubjectLevelResourceIntTest {

    private static final LevelValue DEFAULT_LEVEL = LevelValue.BEGINNER;
    private static final LevelValue UPDATED_LEVEL = LevelValue.INTERMEDIATE;

    private static final byte[] DEFAULT_DESCRIPTION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DESCRIPTION = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DESCRIPTION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DESCRIPTION_CONTENT_TYPE = "image/png";

    @Autowired
    private SubjectLevelRepository subjectLevelRepository;

    @Autowired
    private SubjectLevelMapper subjectLevelMapper;

    @Autowired
    private SubjectLevelService subjectLevelService;

    @Autowired
    private SubjectLevelSearchRepository subjectLevelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubjectLevelMockMvc;

    private SubjectLevel subjectLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubjectLevelResource subjectLevelResource = new SubjectLevelResource(subjectLevelService);
        this.restSubjectLevelMockMvc = MockMvcBuilders.standaloneSetup(subjectLevelResource)
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
    public static SubjectLevel createEntity(EntityManager em) {
        SubjectLevel subjectLevel = new SubjectLevel()
            .level(DEFAULT_LEVEL)
            .description(DEFAULT_DESCRIPTION)
            .descriptionContentType(DEFAULT_DESCRIPTION_CONTENT_TYPE);
        return subjectLevel;
    }

    @Before
    public void initTest() {
        subjectLevelSearchRepository.deleteAll();
        subjectLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubjectLevel() throws Exception {
        int databaseSizeBeforeCreate = subjectLevelRepository.findAll().size();

        // Create the SubjectLevel
        SubjectLevelDTO subjectLevelDTO = subjectLevelMapper.toDto(subjectLevel);
        restSubjectLevelMockMvc.perform(post("/api/subject-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectLevelDTO)))
            .andExpect(status().isCreated());

        // Validate the SubjectLevel in the database
        List<SubjectLevel> subjectLevelList = subjectLevelRepository.findAll();
        assertThat(subjectLevelList).hasSize(databaseSizeBeforeCreate + 1);
        SubjectLevel testSubjectLevel = subjectLevelList.get(subjectLevelList.size() - 1);
        assertThat(testSubjectLevel.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testSubjectLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSubjectLevel.getDescriptionContentType()).isEqualTo(DEFAULT_DESCRIPTION_CONTENT_TYPE);

        // Validate the SubjectLevel in Elasticsearch
        SubjectLevel subjectLevelEs = subjectLevelSearchRepository.findOne(testSubjectLevel.getId());
        assertThat(subjectLevelEs).isEqualToComparingFieldByField(testSubjectLevel);
    }

    @Test
    @Transactional
    public void createSubjectLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subjectLevelRepository.findAll().size();

        // Create the SubjectLevel with an existing ID
        subjectLevel.setId(1L);
        SubjectLevelDTO subjectLevelDTO = subjectLevelMapper.toDto(subjectLevel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectLevelMockMvc.perform(post("/api/subject-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubjectLevel in the database
        List<SubjectLevel> subjectLevelList = subjectLevelRepository.findAll();
        assertThat(subjectLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSubjectLevels() throws Exception {
        // Initialize the database
        subjectLevelRepository.saveAndFlush(subjectLevel);

        // Get all the subjectLevelList
        restSubjectLevelMockMvc.perform(get("/api/subject-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subjectLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].descriptionContentType").value(hasItem(DEFAULT_DESCRIPTION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(Base64Utils.encodeToString(DEFAULT_DESCRIPTION))));
    }

    @Test
    @Transactional
    public void getSubjectLevel() throws Exception {
        // Initialize the database
        subjectLevelRepository.saveAndFlush(subjectLevel);

        // Get the subjectLevel
        restSubjectLevelMockMvc.perform(get("/api/subject-levels/{id}", subjectLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subjectLevel.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.descriptionContentType").value(DEFAULT_DESCRIPTION_CONTENT_TYPE))
            .andExpect(jsonPath("$.description").value(Base64Utils.encodeToString(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getNonExistingSubjectLevel() throws Exception {
        // Get the subjectLevel
        restSubjectLevelMockMvc.perform(get("/api/subject-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubjectLevel() throws Exception {
        // Initialize the database
        subjectLevelRepository.saveAndFlush(subjectLevel);
        subjectLevelSearchRepository.save(subjectLevel);
        int databaseSizeBeforeUpdate = subjectLevelRepository.findAll().size();

        // Update the subjectLevel
        SubjectLevel updatedSubjectLevel = subjectLevelRepository.findOne(subjectLevel.getId());
        updatedSubjectLevel
            .level(UPDATED_LEVEL)
            .description(UPDATED_DESCRIPTION)
            .descriptionContentType(UPDATED_DESCRIPTION_CONTENT_TYPE);
        SubjectLevelDTO subjectLevelDTO = subjectLevelMapper.toDto(updatedSubjectLevel);

        restSubjectLevelMockMvc.perform(put("/api/subject-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectLevelDTO)))
            .andExpect(status().isOk());

        // Validate the SubjectLevel in the database
        List<SubjectLevel> subjectLevelList = subjectLevelRepository.findAll();
        assertThat(subjectLevelList).hasSize(databaseSizeBeforeUpdate);
        SubjectLevel testSubjectLevel = subjectLevelList.get(subjectLevelList.size() - 1);
        assertThat(testSubjectLevel.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testSubjectLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSubjectLevel.getDescriptionContentType()).isEqualTo(UPDATED_DESCRIPTION_CONTENT_TYPE);

        // Validate the SubjectLevel in Elasticsearch
        SubjectLevel subjectLevelEs = subjectLevelSearchRepository.findOne(testSubjectLevel.getId());
        assertThat(subjectLevelEs).isEqualToComparingFieldByField(testSubjectLevel);
    }

    @Test
    @Transactional
    public void updateNonExistingSubjectLevel() throws Exception {
        int databaseSizeBeforeUpdate = subjectLevelRepository.findAll().size();

        // Create the SubjectLevel
        SubjectLevelDTO subjectLevelDTO = subjectLevelMapper.toDto(subjectLevel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubjectLevelMockMvc.perform(put("/api/subject-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectLevelDTO)))
            .andExpect(status().isCreated());

        // Validate the SubjectLevel in the database
        List<SubjectLevel> subjectLevelList = subjectLevelRepository.findAll();
        assertThat(subjectLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubjectLevel() throws Exception {
        // Initialize the database
        subjectLevelRepository.saveAndFlush(subjectLevel);
        subjectLevelSearchRepository.save(subjectLevel);
        int databaseSizeBeforeDelete = subjectLevelRepository.findAll().size();

        // Get the subjectLevel
        restSubjectLevelMockMvc.perform(delete("/api/subject-levels/{id}", subjectLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean subjectLevelExistsInEs = subjectLevelSearchRepository.exists(subjectLevel.getId());
        assertThat(subjectLevelExistsInEs).isFalse();

        // Validate the database is empty
        List<SubjectLevel> subjectLevelList = subjectLevelRepository.findAll();
        assertThat(subjectLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSubjectLevel() throws Exception {
        // Initialize the database
        subjectLevelRepository.saveAndFlush(subjectLevel);
        subjectLevelSearchRepository.save(subjectLevel);

        // Search the subjectLevel
        restSubjectLevelMockMvc.perform(get("/api/_search/subject-levels?query=id:" + subjectLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subjectLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].descriptionContentType").value(hasItem(DEFAULT_DESCRIPTION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(Base64Utils.encodeToString(DEFAULT_DESCRIPTION))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubjectLevel.class);
        SubjectLevel subjectLevel1 = new SubjectLevel();
        subjectLevel1.setId(1L);
        SubjectLevel subjectLevel2 = new SubjectLevel();
        subjectLevel2.setId(subjectLevel1.getId());
        assertThat(subjectLevel1).isEqualTo(subjectLevel2);
        subjectLevel2.setId(2L);
        assertThat(subjectLevel1).isNotEqualTo(subjectLevel2);
        subjectLevel1.setId(null);
        assertThat(subjectLevel1).isNotEqualTo(subjectLevel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubjectLevelDTO.class);
        SubjectLevelDTO subjectLevelDTO1 = new SubjectLevelDTO();
        subjectLevelDTO1.setId(1L);
        SubjectLevelDTO subjectLevelDTO2 = new SubjectLevelDTO();
        assertThat(subjectLevelDTO1).isNotEqualTo(subjectLevelDTO2);
        subjectLevelDTO2.setId(subjectLevelDTO1.getId());
        assertThat(subjectLevelDTO1).isEqualTo(subjectLevelDTO2);
        subjectLevelDTO2.setId(2L);
        assertThat(subjectLevelDTO1).isNotEqualTo(subjectLevelDTO2);
        subjectLevelDTO1.setId(null);
        assertThat(subjectLevelDTO1).isNotEqualTo(subjectLevelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(subjectLevelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(subjectLevelMapper.fromId(null)).isNull();
    }
}
