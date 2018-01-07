package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.Chapters;
import com.vyka.repository.ChaptersRepository;
import com.vyka.service.ChaptersService;
import com.vyka.repository.search.ChaptersSearchRepository;
import com.vyka.service.dto.ChaptersDTO;
import com.vyka.service.mapper.ChaptersMapper;
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
 * Test class for the ChaptersResource REST controller.
 *
 * @see ChaptersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class ChaptersResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_CLASSES = 1;
    private static final Integer UPDATED_NUMBER_OF_CLASSES = 2;

    @Autowired
    private ChaptersRepository chaptersRepository;

    @Autowired
    private ChaptersMapper chaptersMapper;

    @Autowired
    private ChaptersService chaptersService;

    @Autowired
    private ChaptersSearchRepository chaptersSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChaptersMockMvc;

    private Chapters chapters;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChaptersResource chaptersResource = new ChaptersResource(chaptersService);
        this.restChaptersMockMvc = MockMvcBuilders.standaloneSetup(chaptersResource)
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
    public static Chapters createEntity(EntityManager em) {
        Chapters chapters = new Chapters()
            .description(DEFAULT_DESCRIPTION)
            .numberOfClasses(DEFAULT_NUMBER_OF_CLASSES);
        return chapters;
    }

    @Before
    public void initTest() {
        chaptersSearchRepository.deleteAll();
        chapters = createEntity(em);
    }

    @Test
    @Transactional
    public void createChapters() throws Exception {
        int databaseSizeBeforeCreate = chaptersRepository.findAll().size();

        // Create the Chapters
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);
        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isCreated());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeCreate + 1);
        Chapters testChapters = chaptersList.get(chaptersList.size() - 1);
        assertThat(testChapters.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testChapters.getNumberOfClasses()).isEqualTo(DEFAULT_NUMBER_OF_CLASSES);

        // Validate the Chapters in Elasticsearch
        Chapters chaptersEs = chaptersSearchRepository.findOne(testChapters.getId());
        assertThat(chaptersEs).isEqualToComparingFieldByField(testChapters);
    }

    @Test
    @Transactional
    public void createChaptersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chaptersRepository.findAll().size();

        // Create the Chapters with an existing ID
        chapters.setId(1L);
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = chaptersRepository.findAll().size();
        // set the field null
        chapters.setDescription(null);

        // Create the Chapters, which fails.
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfClassesIsRequired() throws Exception {
        int databaseSizeBeforeTest = chaptersRepository.findAll().size();
        // set the field null
        chapters.setNumberOfClasses(null);

        // Create the Chapters, which fails.
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);

        // Get all the chaptersList
        restChaptersMockMvc.perform(get("/api/chapters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chapters.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].numberOfClasses").value(hasItem(DEFAULT_NUMBER_OF_CLASSES)));
    }

    @Test
    @Transactional
    public void getChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);

        // Get the chapters
        restChaptersMockMvc.perform(get("/api/chapters/{id}", chapters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chapters.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.numberOfClasses").value(DEFAULT_NUMBER_OF_CLASSES));
    }

    @Test
    @Transactional
    public void getNonExistingChapters() throws Exception {
        // Get the chapters
        restChaptersMockMvc.perform(get("/api/chapters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);
        chaptersSearchRepository.save(chapters);
        int databaseSizeBeforeUpdate = chaptersRepository.findAll().size();

        // Update the chapters
        Chapters updatedChapters = chaptersRepository.findOne(chapters.getId());
        updatedChapters
            .description(UPDATED_DESCRIPTION)
            .numberOfClasses(UPDATED_NUMBER_OF_CLASSES);
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(updatedChapters);

        restChaptersMockMvc.perform(put("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isOk());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeUpdate);
        Chapters testChapters = chaptersList.get(chaptersList.size() - 1);
        assertThat(testChapters.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testChapters.getNumberOfClasses()).isEqualTo(UPDATED_NUMBER_OF_CLASSES);

        // Validate the Chapters in Elasticsearch
        Chapters chaptersEs = chaptersSearchRepository.findOne(testChapters.getId());
        assertThat(chaptersEs).isEqualToComparingFieldByField(testChapters);
    }

    @Test
    @Transactional
    public void updateNonExistingChapters() throws Exception {
        int databaseSizeBeforeUpdate = chaptersRepository.findAll().size();

        // Create the Chapters
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChaptersMockMvc.perform(put("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isCreated());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);
        chaptersSearchRepository.save(chapters);
        int databaseSizeBeforeDelete = chaptersRepository.findAll().size();

        // Get the chapters
        restChaptersMockMvc.perform(delete("/api/chapters/{id}", chapters.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean chaptersExistsInEs = chaptersSearchRepository.exists(chapters.getId());
        assertThat(chaptersExistsInEs).isFalse();

        // Validate the database is empty
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);
        chaptersSearchRepository.save(chapters);

        // Search the chapters
        restChaptersMockMvc.perform(get("/api/_search/chapters?query=id:" + chapters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chapters.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].numberOfClasses").value(hasItem(DEFAULT_NUMBER_OF_CLASSES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chapters.class);
        Chapters chapters1 = new Chapters();
        chapters1.setId(1L);
        Chapters chapters2 = new Chapters();
        chapters2.setId(chapters1.getId());
        assertThat(chapters1).isEqualTo(chapters2);
        chapters2.setId(2L);
        assertThat(chapters1).isNotEqualTo(chapters2);
        chapters1.setId(null);
        assertThat(chapters1).isNotEqualTo(chapters2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChaptersDTO.class);
        ChaptersDTO chaptersDTO1 = new ChaptersDTO();
        chaptersDTO1.setId(1L);
        ChaptersDTO chaptersDTO2 = new ChaptersDTO();
        assertThat(chaptersDTO1).isNotEqualTo(chaptersDTO2);
        chaptersDTO2.setId(chaptersDTO1.getId());
        assertThat(chaptersDTO1).isEqualTo(chaptersDTO2);
        chaptersDTO2.setId(2L);
        assertThat(chaptersDTO1).isNotEqualTo(chaptersDTO2);
        chaptersDTO1.setId(null);
        assertThat(chaptersDTO1).isNotEqualTo(chaptersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(chaptersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(chaptersMapper.fromId(null)).isNull();
    }
}
