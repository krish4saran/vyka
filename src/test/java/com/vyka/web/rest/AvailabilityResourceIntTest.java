package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.Availability;
import com.vyka.repository.AvailabilityRepository;
import com.vyka.service.AvailabilityService;
import com.vyka.repository.search.AvailabilitySearchRepository;
import com.vyka.service.dto.AvailabilityDTO;
import com.vyka.service.mapper.AvailabilityMapper;
import com.vyka.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Ignore;
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

import com.vyka.domain.enumeration.DayOfWeek;
import com.vyka.domain.enumeration.TimeZones;
/**
 * Test class for the AvailabilityResource REST controller.
 *
 * @see AvailabilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
@Ignore
public class AvailabilityResourceIntTest {

    private static final DayOfWeek DEFAULT_DAY_OF_WEEK = DayOfWeek.SUN;
    private static final DayOfWeek UPDATED_DAY_OF_WEEK = DayOfWeek.MON;

    private static final Boolean DEFAULT_AVAILABILE = false;
    private static final Boolean UPDATED_AVAILABILE = true;

    private static final TimeZones DEFAULT_TIME_ZONE = TimeZones.IST;
    private static final TimeZones UPDATED_TIME_ZONE = TimeZones.CST;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private AvailabilityMapper availabilityMapper;

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private AvailabilitySearchRepository availabilitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAvailabilityMockMvc;

    private Availability availability;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvailabilityResource availabilityResource = new AvailabilityResource(availabilityService);
        this.restAvailabilityMockMvc = MockMvcBuilders.standaloneSetup(availabilityResource)
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
    public static Availability createEntity(EntityManager em) {
        Availability availability = new Availability()
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .availabile(DEFAULT_AVAILABILE)
            .timeZone(DEFAULT_TIME_ZONE);
        return availability;
    }

    @Before
    public void initTest() {
        availabilitySearchRepository.deleteAll();
        availability = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvailability() throws Exception {
        int databaseSizeBeforeCreate = availabilityRepository.findAll().size();

        // Create the Availability
        AvailabilityDTO availabilityDTO = availabilityMapper.toDto(availability);
        restAvailabilityMockMvc.perform(post("/api/availabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Availability in the database
        List<Availability> availabilityList = availabilityRepository.findAll();
        assertThat(availabilityList).hasSize(databaseSizeBeforeCreate + 1);
        Availability testAvailability = availabilityList.get(availabilityList.size() - 1);
        assertThat(testAvailability.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testAvailability.isAvailabile()).isEqualTo(DEFAULT_AVAILABILE);
        assertThat(testAvailability.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);

        // Validate the Availability in Elasticsearch
        Availability availabilityEs = availabilitySearchRepository.findOne(testAvailability.getId());
        assertThat(availabilityEs).isEqualToComparingFieldByField(testAvailability);
    }

    @Test
    @Transactional
    public void createAvailabilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = availabilityRepository.findAll().size();

        // Create the Availability with an existing ID
        availability.setId(1L);
        AvailabilityDTO availabilityDTO = availabilityMapper.toDto(availability);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvailabilityMockMvc.perform(post("/api/availabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Availability in the database
        List<Availability> availabilityList = availabilityRepository.findAll();
        assertThat(availabilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDayOfWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = availabilityRepository.findAll().size();
        // set the field null
        availability.setDayOfWeek(null);

        // Create the Availability, which fails.
        AvailabilityDTO availabilityDTO = availabilityMapper.toDto(availability);

        restAvailabilityMockMvc.perform(post("/api/availabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilityDTO)))
            .andExpect(status().isBadRequest());

        List<Availability> availabilityList = availabilityRepository.findAll();
        assertThat(availabilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeZoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = availabilityRepository.findAll().size();
        // set the field null
        availability.setTimeZone(null);

        // Create the Availability, which fails.
        AvailabilityDTO availabilityDTO = availabilityMapper.toDto(availability);

        restAvailabilityMockMvc.perform(post("/api/availabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilityDTO)))
            .andExpect(status().isBadRequest());

        List<Availability> availabilityList = availabilityRepository.findAll();
        assertThat(availabilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvailabilities() throws Exception {
        // Initialize the database
        availabilityRepository.saveAndFlush(availability);

        // Get all the availabilityList
        restAvailabilityMockMvc.perform(get("/api/availabilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(availability.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK.toString())))
            .andExpect(jsonPath("$.[*].availabile").value(hasItem(DEFAULT_AVAILABILE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())));
    }

    @Test
    @Transactional
    public void getAvailability() throws Exception {
        // Initialize the database
        availabilityRepository.saveAndFlush(availability);

        // Get the availability
        restAvailabilityMockMvc.perform(get("/api/availabilities/{id}", availability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(availability.getId().intValue()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK.toString()))
            .andExpect(jsonPath("$.availabile").value(DEFAULT_AVAILABILE.booleanValue()))
            .andExpect(jsonPath("$.timeZone").value(DEFAULT_TIME_ZONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAvailability() throws Exception {
        // Get the availability
        restAvailabilityMockMvc.perform(get("/api/availabilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvailability() throws Exception {
        // Initialize the database
        availabilityRepository.saveAndFlush(availability);
        availabilitySearchRepository.save(availability);
        int databaseSizeBeforeUpdate = availabilityRepository.findAll().size();

        // Update the availability
        Availability updatedAvailability = availabilityRepository.findOne(availability.getId());
        updatedAvailability
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .availabile(UPDATED_AVAILABILE)
            .timeZone(UPDATED_TIME_ZONE);
        AvailabilityDTO availabilityDTO = availabilityMapper.toDto(updatedAvailability);

        restAvailabilityMockMvc.perform(put("/api/availabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilityDTO)))
            .andExpect(status().isOk());

        // Validate the Availability in the database
        List<Availability> availabilityList = availabilityRepository.findAll();
        assertThat(availabilityList).hasSize(databaseSizeBeforeUpdate);
        Availability testAvailability = availabilityList.get(availabilityList.size() - 1);
        assertThat(testAvailability.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testAvailability.isAvailabile()).isEqualTo(UPDATED_AVAILABILE);
        assertThat(testAvailability.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);

        // Validate the Availability in Elasticsearch
        Availability availabilityEs = availabilitySearchRepository.findOne(testAvailability.getId());
        assertThat(availabilityEs).isEqualToComparingFieldByField(testAvailability);
    }

    @Test
    @Transactional
    public void updateNonExistingAvailability() throws Exception {
        int databaseSizeBeforeUpdate = availabilityRepository.findAll().size();

        // Create the Availability
        AvailabilityDTO availabilityDTO = availabilityMapper.toDto(availability);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvailabilityMockMvc.perform(put("/api/availabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(availabilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Availability in the database
        List<Availability> availabilityList = availabilityRepository.findAll();
        assertThat(availabilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvailability() throws Exception {
        // Initialize the database
        availabilityRepository.saveAndFlush(availability);
        availabilitySearchRepository.save(availability);
        int databaseSizeBeforeDelete = availabilityRepository.findAll().size();

        // Get the availability
        restAvailabilityMockMvc.perform(delete("/api/availabilities/{id}", availability.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean availabilityExistsInEs = availabilitySearchRepository.exists(availability.getId());
        assertThat(availabilityExistsInEs).isFalse();

        // Validate the database is empty
        List<Availability> availabilityList = availabilityRepository.findAll();
        assertThat(availabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAvailability() throws Exception {
        // Initialize the database
        availabilityRepository.saveAndFlush(availability);
        availabilitySearchRepository.save(availability);

        // Search the availability
        restAvailabilityMockMvc.perform(get("/api/_search/availabilities?query=id:" + availability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(availability.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK.toString())))
            .andExpect(jsonPath("$.[*].availabile").value(hasItem(DEFAULT_AVAILABILE.booleanValue())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Availability.class);
        Availability availability1 = new Availability();
        availability1.setId(1L);
        Availability availability2 = new Availability();
        availability2.setId(availability1.getId());
        assertThat(availability1).isEqualTo(availability2);
        availability2.setId(2L);
        assertThat(availability1).isNotEqualTo(availability2);
        availability1.setId(null);
        assertThat(availability1).isNotEqualTo(availability2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvailabilityDTO.class);
        AvailabilityDTO availabilityDTO1 = new AvailabilityDTO();
        availabilityDTO1.setId(1L);
        AvailabilityDTO availabilityDTO2 = new AvailabilityDTO();
        assertThat(availabilityDTO1).isNotEqualTo(availabilityDTO2);
        availabilityDTO2.setId(availabilityDTO1.getId());
        assertThat(availabilityDTO1).isEqualTo(availabilityDTO2);
        availabilityDTO2.setId(2L);
        assertThat(availabilityDTO1).isNotEqualTo(availabilityDTO2);
        availabilityDTO1.setId(null);
        assertThat(availabilityDTO1).isNotEqualTo(availabilityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(availabilityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(availabilityMapper.fromId(null)).isNull();
    }
}
