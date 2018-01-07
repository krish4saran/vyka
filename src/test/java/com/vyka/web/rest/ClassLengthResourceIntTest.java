package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.ClassLength;
import com.vyka.repository.ClassLengthRepository;
import com.vyka.service.ClassLengthService;
import com.vyka.repository.search.ClassLengthSearchRepository;
import com.vyka.service.dto.ClassLengthDTO;
import com.vyka.service.mapper.ClassLengthMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vyka.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClassLengthResource REST controller.
 *
 * @see ClassLengthResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class ClassLengthResourceIntTest {

    private static final Integer DEFAULT_CLASS_LENGTH = 30;
    private static final Integer UPDATED_CLASS_LENGTH = 31;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ClassLengthRepository classLengthRepository;

    @Autowired
    private ClassLengthMapper classLengthMapper;

    @Autowired
    private ClassLengthService classLengthService;

    @Autowired
    private ClassLengthSearchRepository classLengthSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClassLengthMockMvc;

    private ClassLength classLength;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassLengthResource classLengthResource = new ClassLengthResource(classLengthService);
        this.restClassLengthMockMvc = MockMvcBuilders.standaloneSetup(classLengthResource)
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
    public static ClassLength createEntity(EntityManager em) {
        ClassLength classLength = new ClassLength()
            .classLength(DEFAULT_CLASS_LENGTH)
            .active(DEFAULT_ACTIVE)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return classLength;
    }

    @Before
    public void initTest() {
        classLengthSearchRepository.deleteAll();
        classLength = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassLength() throws Exception {
        int databaseSizeBeforeCreate = classLengthRepository.findAll().size();

        // Create the ClassLength
        ClassLengthDTO classLengthDTO = classLengthMapper.toDto(classLength);
        restClassLengthMockMvc.perform(post("/api/class-lengths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classLengthDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassLength in the database
        List<ClassLength> classLengthList = classLengthRepository.findAll();
        assertThat(classLengthList).hasSize(databaseSizeBeforeCreate + 1);
        ClassLength testClassLength = classLengthList.get(classLengthList.size() - 1);
        assertThat(testClassLength.getClassLength()).isEqualTo(DEFAULT_CLASS_LENGTH);
        assertThat(testClassLength.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testClassLength.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testClassLength.getUpdated()).isEqualTo(DEFAULT_UPDATED);

        // Validate the ClassLength in Elasticsearch
        ClassLength classLengthEs = classLengthSearchRepository.findOne(testClassLength.getId());
        assertThat(classLengthEs).isEqualToComparingFieldByField(testClassLength);
    }

    @Test
    @Transactional
    public void createClassLengthWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classLengthRepository.findAll().size();

        // Create the ClassLength with an existing ID
        classLength.setId(1L);
        ClassLengthDTO classLengthDTO = classLengthMapper.toDto(classLength);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassLengthMockMvc.perform(post("/api/class-lengths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classLengthDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassLength in the database
        List<ClassLength> classLengthList = classLengthRepository.findAll();
        assertThat(classLengthList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClassLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = classLengthRepository.findAll().size();
        // set the field null
        classLength.setClassLength(null);

        // Create the ClassLength, which fails.
        ClassLengthDTO classLengthDTO = classLengthMapper.toDto(classLength);

        restClassLengthMockMvc.perform(post("/api/class-lengths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classLengthDTO)))
            .andExpect(status().isBadRequest());

        List<ClassLength> classLengthList = classLengthRepository.findAll();
        assertThat(classLengthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = classLengthRepository.findAll().size();
        // set the field null
        classLength.setActive(null);

        // Create the ClassLength, which fails.
        ClassLengthDTO classLengthDTO = classLengthMapper.toDto(classLength);

        restClassLengthMockMvc.perform(post("/api/class-lengths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classLengthDTO)))
            .andExpect(status().isBadRequest());

        List<ClassLength> classLengthList = classLengthRepository.findAll();
        assertThat(classLengthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = classLengthRepository.findAll().size();
        // set the field null
        classLength.setCreated(null);

        // Create the ClassLength, which fails.
        ClassLengthDTO classLengthDTO = classLengthMapper.toDto(classLength);

        restClassLengthMockMvc.perform(post("/api/class-lengths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classLengthDTO)))
            .andExpect(status().isBadRequest());

        List<ClassLength> classLengthList = classLengthRepository.findAll();
        assertThat(classLengthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassLengths() throws Exception {
        // Initialize the database
        classLengthRepository.saveAndFlush(classLength);

        // Get all the classLengthList
        restClassLengthMockMvc.perform(get("/api/class-lengths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classLength.getId().intValue())))
            .andExpect(jsonPath("$.[*].classLength").value(hasItem(DEFAULT_CLASS_LENGTH)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }

    @Test
    @Transactional
    public void getClassLength() throws Exception {
        // Initialize the database
        classLengthRepository.saveAndFlush(classLength);

        // Get the classLength
        restClassLengthMockMvc.perform(get("/api/class-lengths/{id}", classLength.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classLength.getId().intValue()))
            .andExpect(jsonPath("$.classLength").value(DEFAULT_CLASS_LENGTH))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassLength() throws Exception {
        // Get the classLength
        restClassLengthMockMvc.perform(get("/api/class-lengths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassLength() throws Exception {
        // Initialize the database
        classLengthRepository.saveAndFlush(classLength);
        classLengthSearchRepository.save(classLength);
        int databaseSizeBeforeUpdate = classLengthRepository.findAll().size();

        // Update the classLength
        ClassLength updatedClassLength = classLengthRepository.findOne(classLength.getId());
        updatedClassLength
            .classLength(UPDATED_CLASS_LENGTH)
            .active(UPDATED_ACTIVE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        ClassLengthDTO classLengthDTO = classLengthMapper.toDto(updatedClassLength);

        restClassLengthMockMvc.perform(put("/api/class-lengths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classLengthDTO)))
            .andExpect(status().isOk());

        // Validate the ClassLength in the database
        List<ClassLength> classLengthList = classLengthRepository.findAll();
        assertThat(classLengthList).hasSize(databaseSizeBeforeUpdate);
        ClassLength testClassLength = classLengthList.get(classLengthList.size() - 1);
        assertThat(testClassLength.getClassLength()).isEqualTo(UPDATED_CLASS_LENGTH);
        assertThat(testClassLength.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testClassLength.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testClassLength.getUpdated()).isEqualTo(UPDATED_UPDATED);

        // Validate the ClassLength in Elasticsearch
        ClassLength classLengthEs = classLengthSearchRepository.findOne(testClassLength.getId());
        assertThat(classLengthEs).isEqualToComparingFieldByField(testClassLength);
    }

    @Test
    @Transactional
    public void updateNonExistingClassLength() throws Exception {
        int databaseSizeBeforeUpdate = classLengthRepository.findAll().size();

        // Create the ClassLength
        ClassLengthDTO classLengthDTO = classLengthMapper.toDto(classLength);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClassLengthMockMvc.perform(put("/api/class-lengths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classLengthDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassLength in the database
        List<ClassLength> classLengthList = classLengthRepository.findAll();
        assertThat(classLengthList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClassLength() throws Exception {
        // Initialize the database
        classLengthRepository.saveAndFlush(classLength);
        classLengthSearchRepository.save(classLength);
        int databaseSizeBeforeDelete = classLengthRepository.findAll().size();

        // Get the classLength
        restClassLengthMockMvc.perform(delete("/api/class-lengths/{id}", classLength.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean classLengthExistsInEs = classLengthSearchRepository.exists(classLength.getId());
        assertThat(classLengthExistsInEs).isFalse();

        // Validate the database is empty
        List<ClassLength> classLengthList = classLengthRepository.findAll();
        assertThat(classLengthList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchClassLength() throws Exception {
        // Initialize the database
        classLengthRepository.saveAndFlush(classLength);
        classLengthSearchRepository.save(classLength);

        // Search the classLength
        restClassLengthMockMvc.perform(get("/api/_search/class-lengths?query=id:" + classLength.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classLength.getId().intValue())))
            .andExpect(jsonPath("$.[*].classLength").value(hasItem(DEFAULT_CLASS_LENGTH)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassLength.class);
        ClassLength classLength1 = new ClassLength();
        classLength1.setId(1L);
        ClassLength classLength2 = new ClassLength();
        classLength2.setId(classLength1.getId());
        assertThat(classLength1).isEqualTo(classLength2);
        classLength2.setId(2L);
        assertThat(classLength1).isNotEqualTo(classLength2);
        classLength1.setId(null);
        assertThat(classLength1).isNotEqualTo(classLength2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassLengthDTO.class);
        ClassLengthDTO classLengthDTO1 = new ClassLengthDTO();
        classLengthDTO1.setId(1L);
        ClassLengthDTO classLengthDTO2 = new ClassLengthDTO();
        assertThat(classLengthDTO1).isNotEqualTo(classLengthDTO2);
        classLengthDTO2.setId(classLengthDTO1.getId());
        assertThat(classLengthDTO1).isEqualTo(classLengthDTO2);
        classLengthDTO2.setId(2L);
        assertThat(classLengthDTO1).isNotEqualTo(classLengthDTO2);
        classLengthDTO1.setId(null);
        assertThat(classLengthDTO1).isNotEqualTo(classLengthDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(classLengthMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(classLengthMapper.fromId(null)).isNull();
    }
}
