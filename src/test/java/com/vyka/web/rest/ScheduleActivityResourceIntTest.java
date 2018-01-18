package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.ScheduleActivity;
import com.vyka.repository.ScheduleActivityRepository;
import com.vyka.service.ScheduleActivityService;
import com.vyka.repository.search.ScheduleActivitySearchRepository;
import com.vyka.service.dto.ScheduleActivityDTO;
import com.vyka.service.mapper.ScheduleActivityMapper;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vyka.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ScheduleActivityResource REST controller.
 *
 * @see ScheduleActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class ScheduleActivityResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ScheduleActivityRepository scheduleActivityRepository;

    @Autowired
    private ScheduleActivityMapper scheduleActivityMapper;

    @Autowired
    private ScheduleActivityService scheduleActivityService;

    @Autowired
    private ScheduleActivitySearchRepository scheduleActivitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restScheduleActivityMockMvc;

    private ScheduleActivity scheduleActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScheduleActivityResource scheduleActivityResource = new ScheduleActivityResource(scheduleActivityService);
        this.restScheduleActivityMockMvc = MockMvcBuilders.standaloneSetup(scheduleActivityResource)
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
    public static ScheduleActivity createEntity(EntityManager em) {
        ScheduleActivity scheduleActivity = new ScheduleActivity()
            .amount(DEFAULT_AMOUNT)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return scheduleActivity;
    }

    @Before
    public void initTest() {
        scheduleActivitySearchRepository.deleteAll();
        scheduleActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduleActivity() throws Exception {
        int databaseSizeBeforeCreate = scheduleActivityRepository.findAll().size();

        // Create the ScheduleActivity
        ScheduleActivityDTO scheduleActivityDTO = scheduleActivityMapper.toDto(scheduleActivity);
        restScheduleActivityMockMvc.perform(post("/api/schedule-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the ScheduleActivity in the database
        List<ScheduleActivity> scheduleActivityList = scheduleActivityRepository.findAll();
        assertThat(scheduleActivityList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduleActivity testScheduleActivity = scheduleActivityList.get(scheduleActivityList.size() - 1);
        assertThat(testScheduleActivity.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testScheduleActivity.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testScheduleActivity.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);

        // Validate the ScheduleActivity in Elasticsearch
        ScheduleActivity scheduleActivityEs = scheduleActivitySearchRepository.findOne(testScheduleActivity.getId());
        assertThat(scheduleActivityEs).isEqualToComparingFieldByField(testScheduleActivity);
    }

    @Test
    @Transactional
    public void createScheduleActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleActivityRepository.findAll().size();

        // Create the ScheduleActivity with an existing ID
        scheduleActivity.setId(1L);
        ScheduleActivityDTO scheduleActivityDTO = scheduleActivityMapper.toDto(scheduleActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleActivityMockMvc.perform(post("/api/schedule-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleActivity in the database
        List<ScheduleActivity> scheduleActivityList = scheduleActivityRepository.findAll();
        assertThat(scheduleActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleActivityRepository.findAll().size();
        // set the field null
        scheduleActivity.setAmount(null);

        // Create the ScheduleActivity, which fails.
        ScheduleActivityDTO scheduleActivityDTO = scheduleActivityMapper.toDto(scheduleActivity);

        restScheduleActivityMockMvc.perform(post("/api/schedule-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleActivityDTO)))
            .andExpect(status().isBadRequest());

        List<ScheduleActivity> scheduleActivityList = scheduleActivityRepository.findAll();
        assertThat(scheduleActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleActivityRepository.findAll().size();
        // set the field null
        scheduleActivity.setCreatedDate(null);

        // Create the ScheduleActivity, which fails.
        ScheduleActivityDTO scheduleActivityDTO = scheduleActivityMapper.toDto(scheduleActivity);

        restScheduleActivityMockMvc.perform(post("/api/schedule-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleActivityDTO)))
            .andExpect(status().isBadRequest());

        List<ScheduleActivity> scheduleActivityList = scheduleActivityRepository.findAll();
        assertThat(scheduleActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScheduleActivities() throws Exception {
        // Initialize the database
        scheduleActivityRepository.saveAndFlush(scheduleActivity);

        // Get all the scheduleActivityList
        restScheduleActivityMockMvc.perform(get("/api/schedule-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getScheduleActivity() throws Exception {
        // Initialize the database
        scheduleActivityRepository.saveAndFlush(scheduleActivity);

        // Get the scheduleActivity
        restScheduleActivityMockMvc.perform(get("/api/schedule-activities/{id}", scheduleActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scheduleActivity.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScheduleActivity() throws Exception {
        // Get the scheduleActivity
        restScheduleActivityMockMvc.perform(get("/api/schedule-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduleActivity() throws Exception {
        // Initialize the database
        scheduleActivityRepository.saveAndFlush(scheduleActivity);
        scheduleActivitySearchRepository.save(scheduleActivity);
        int databaseSizeBeforeUpdate = scheduleActivityRepository.findAll().size();

        // Update the scheduleActivity
        ScheduleActivity updatedScheduleActivity = scheduleActivityRepository.findOne(scheduleActivity.getId());
        updatedScheduleActivity
            .amount(UPDATED_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        ScheduleActivityDTO scheduleActivityDTO = scheduleActivityMapper.toDto(updatedScheduleActivity);

        restScheduleActivityMockMvc.perform(put("/api/schedule-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleActivityDTO)))
            .andExpect(status().isOk());

        // Validate the ScheduleActivity in the database
        List<ScheduleActivity> scheduleActivityList = scheduleActivityRepository.findAll();
        assertThat(scheduleActivityList).hasSize(databaseSizeBeforeUpdate);
        ScheduleActivity testScheduleActivity = scheduleActivityList.get(scheduleActivityList.size() - 1);
        assertThat(testScheduleActivity.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testScheduleActivity.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testScheduleActivity.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);

        // Validate the ScheduleActivity in Elasticsearch
        ScheduleActivity scheduleActivityEs = scheduleActivitySearchRepository.findOne(testScheduleActivity.getId());
        assertThat(scheduleActivityEs).isEqualToComparingFieldByField(testScheduleActivity);
    }

    @Test
    @Transactional
    public void updateNonExistingScheduleActivity() throws Exception {
        int databaseSizeBeforeUpdate = scheduleActivityRepository.findAll().size();

        // Create the ScheduleActivity
        ScheduleActivityDTO scheduleActivityDTO = scheduleActivityMapper.toDto(scheduleActivity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScheduleActivityMockMvc.perform(put("/api/schedule-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the ScheduleActivity in the database
        List<ScheduleActivity> scheduleActivityList = scheduleActivityRepository.findAll();
        assertThat(scheduleActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteScheduleActivity() throws Exception {
        // Initialize the database
        scheduleActivityRepository.saveAndFlush(scheduleActivity);
        scheduleActivitySearchRepository.save(scheduleActivity);
        int databaseSizeBeforeDelete = scheduleActivityRepository.findAll().size();

        // Get the scheduleActivity
        restScheduleActivityMockMvc.perform(delete("/api/schedule-activities/{id}", scheduleActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean scheduleActivityExistsInEs = scheduleActivitySearchRepository.exists(scheduleActivity.getId());
        assertThat(scheduleActivityExistsInEs).isFalse();

        // Validate the database is empty
        List<ScheduleActivity> scheduleActivityList = scheduleActivityRepository.findAll();
        assertThat(scheduleActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchScheduleActivity() throws Exception {
        // Initialize the database
        scheduleActivityRepository.saveAndFlush(scheduleActivity);
        scheduleActivitySearchRepository.save(scheduleActivity);

        // Search the scheduleActivity
        restScheduleActivityMockMvc.perform(get("/api/_search/schedule-activities?query=id:" + scheduleActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleActivity.class);
        ScheduleActivity scheduleActivity1 = new ScheduleActivity();
        scheduleActivity1.setId(1L);
        ScheduleActivity scheduleActivity2 = new ScheduleActivity();
        scheduleActivity2.setId(scheduleActivity1.getId());
        assertThat(scheduleActivity1).isEqualTo(scheduleActivity2);
        scheduleActivity2.setId(2L);
        assertThat(scheduleActivity1).isNotEqualTo(scheduleActivity2);
        scheduleActivity1.setId(null);
        assertThat(scheduleActivity1).isNotEqualTo(scheduleActivity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleActivityDTO.class);
        ScheduleActivityDTO scheduleActivityDTO1 = new ScheduleActivityDTO();
        scheduleActivityDTO1.setId(1L);
        ScheduleActivityDTO scheduleActivityDTO2 = new ScheduleActivityDTO();
        assertThat(scheduleActivityDTO1).isNotEqualTo(scheduleActivityDTO2);
        scheduleActivityDTO2.setId(scheduleActivityDTO1.getId());
        assertThat(scheduleActivityDTO1).isEqualTo(scheduleActivityDTO2);
        scheduleActivityDTO2.setId(2L);
        assertThat(scheduleActivityDTO1).isNotEqualTo(scheduleActivityDTO2);
        scheduleActivityDTO1.setId(null);
        assertThat(scheduleActivityDTO1).isNotEqualTo(scheduleActivityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(scheduleActivityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(scheduleActivityMapper.fromId(null)).isNull();
    }
}
