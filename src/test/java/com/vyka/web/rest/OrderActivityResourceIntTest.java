package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.OrderActivity;
import com.vyka.repository.OrderActivityRepository;
import com.vyka.service.OrderActivityService;
import com.vyka.repository.search.OrderActivitySearchRepository;
import com.vyka.service.dto.OrderActivityDTO;
import com.vyka.service.mapper.OrderActivityMapper;
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

import com.vyka.domain.enumeration.ActivityType;
/**
 * Test class for the OrderActivityResource REST controller.
 *
 * @see OrderActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class OrderActivityResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final ActivityType DEFAULT_ACTIVITY_TYPE = ActivityType.BOOKED;
    private static final ActivityType UPDATED_ACTIVITY_TYPE = ActivityType.RETURNED;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_AMOUNT_LOCAL_CURRENCY = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_LOCAL_CURRENCY = new BigDecimal(2);

    @Autowired
    private OrderActivityRepository orderActivityRepository;

    @Autowired
    private OrderActivityMapper orderActivityMapper;

    @Autowired
    private OrderActivityService orderActivityService;

    @Autowired
    private OrderActivitySearchRepository orderActivitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderActivityMockMvc;

    private OrderActivity orderActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderActivityResource orderActivityResource = new OrderActivityResource(orderActivityService);
        this.restOrderActivityMockMvc = MockMvcBuilders.standaloneSetup(orderActivityResource)
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
    public static OrderActivity createEntity(EntityManager em) {
        OrderActivity orderActivity = new OrderActivity()
            .amount(DEFAULT_AMOUNT)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .activityType(DEFAULT_ACTIVITY_TYPE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .amountLocalCurrency(DEFAULT_AMOUNT_LOCAL_CURRENCY);
        return orderActivity;
    }

    @Before
    public void initTest() {
        orderActivitySearchRepository.deleteAll();
        orderActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderActivity() throws Exception {
        int databaseSizeBeforeCreate = orderActivityRepository.findAll().size();

        // Create the OrderActivity
        OrderActivityDTO orderActivityDTO = orderActivityMapper.toDto(orderActivity);
        restOrderActivityMockMvc.perform(post("/api/order-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderActivity in the database
        List<OrderActivity> orderActivityList = orderActivityRepository.findAll();
        assertThat(orderActivityList).hasSize(databaseSizeBeforeCreate + 1);
        OrderActivity testOrderActivity = orderActivityList.get(orderActivityList.size() - 1);
        assertThat(testOrderActivity.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testOrderActivity.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testOrderActivity.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
        assertThat(testOrderActivity.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrderActivity.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testOrderActivity.getAmountLocalCurrency()).isEqualTo(DEFAULT_AMOUNT_LOCAL_CURRENCY);

        // Validate the OrderActivity in Elasticsearch
        OrderActivity orderActivityEs = orderActivitySearchRepository.findOne(testOrderActivity.getId());
        assertThat(orderActivityEs).isEqualToComparingFieldByField(testOrderActivity);
    }

    @Test
    @Transactional
    public void createOrderActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderActivityRepository.findAll().size();

        // Create the OrderActivity with an existing ID
        orderActivity.setId(1L);
        OrderActivityDTO orderActivityDTO = orderActivityMapper.toDto(orderActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderActivityMockMvc.perform(post("/api/order-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderActivity in the database
        List<OrderActivity> orderActivityList = orderActivityRepository.findAll();
        assertThat(orderActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderActivityRepository.findAll().size();
        // set the field null
        orderActivity.setAmount(null);

        // Create the OrderActivity, which fails.
        OrderActivityDTO orderActivityDTO = orderActivityMapper.toDto(orderActivity);

        restOrderActivityMockMvc.perform(post("/api/order-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderActivityDTO)))
            .andExpect(status().isBadRequest());

        List<OrderActivity> orderActivityList = orderActivityRepository.findAll();
        assertThat(orderActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderActivityRepository.findAll().size();
        // set the field null
        orderActivity.setActivityType(null);

        // Create the OrderActivity, which fails.
        OrderActivityDTO orderActivityDTO = orderActivityMapper.toDto(orderActivity);

        restOrderActivityMockMvc.perform(post("/api/order-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderActivityDTO)))
            .andExpect(status().isBadRequest());

        List<OrderActivity> orderActivityList = orderActivityRepository.findAll();
        assertThat(orderActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderActivityRepository.findAll().size();
        // set the field null
        orderActivity.setCreatedDate(null);

        // Create the OrderActivity, which fails.
        OrderActivityDTO orderActivityDTO = orderActivityMapper.toDto(orderActivity);

        restOrderActivityMockMvc.perform(post("/api/order-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderActivityDTO)))
            .andExpect(status().isBadRequest());

        List<OrderActivity> orderActivityList = orderActivityRepository.findAll();
        assertThat(orderActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderActivities() throws Exception {
        // Initialize the database
        orderActivityRepository.saveAndFlush(orderActivity);

        // Get all the orderActivityList
        restOrderActivityMockMvc.perform(get("/api/order-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountLocalCurrency").value(hasItem(DEFAULT_AMOUNT_LOCAL_CURRENCY.intValue())));
    }

    @Test
    @Transactional
    public void getOrderActivity() throws Exception {
        // Initialize the database
        orderActivityRepository.saveAndFlush(orderActivity);

        // Get the orderActivity
        restOrderActivityMockMvc.perform(get("/api/order-activities/{id}", orderActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderActivity.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.activityType").value(DEFAULT_ACTIVITY_TYPE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.amountLocalCurrency").value(DEFAULT_AMOUNT_LOCAL_CURRENCY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderActivity() throws Exception {
        // Get the orderActivity
        restOrderActivityMockMvc.perform(get("/api/order-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderActivity() throws Exception {
        // Initialize the database
        orderActivityRepository.saveAndFlush(orderActivity);
        orderActivitySearchRepository.save(orderActivity);
        int databaseSizeBeforeUpdate = orderActivityRepository.findAll().size();

        // Update the orderActivity
        OrderActivity updatedOrderActivity = orderActivityRepository.findOne(orderActivity.getId());
        updatedOrderActivity
            .amount(UPDATED_AMOUNT)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .activityType(UPDATED_ACTIVITY_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .amountLocalCurrency(UPDATED_AMOUNT_LOCAL_CURRENCY);
        OrderActivityDTO orderActivityDTO = orderActivityMapper.toDto(updatedOrderActivity);

        restOrderActivityMockMvc.perform(put("/api/order-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderActivityDTO)))
            .andExpect(status().isOk());

        // Validate the OrderActivity in the database
        List<OrderActivity> orderActivityList = orderActivityRepository.findAll();
        assertThat(orderActivityList).hasSize(databaseSizeBeforeUpdate);
        OrderActivity testOrderActivity = orderActivityList.get(orderActivityList.size() - 1);
        assertThat(testOrderActivity.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testOrderActivity.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testOrderActivity.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testOrderActivity.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrderActivity.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testOrderActivity.getAmountLocalCurrency()).isEqualTo(UPDATED_AMOUNT_LOCAL_CURRENCY);

        // Validate the OrderActivity in Elasticsearch
        OrderActivity orderActivityEs = orderActivitySearchRepository.findOne(testOrderActivity.getId());
        assertThat(orderActivityEs).isEqualToComparingFieldByField(testOrderActivity);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderActivity() throws Exception {
        int databaseSizeBeforeUpdate = orderActivityRepository.findAll().size();

        // Create the OrderActivity
        OrderActivityDTO orderActivityDTO = orderActivityMapper.toDto(orderActivity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderActivityMockMvc.perform(put("/api/order-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderActivity in the database
        List<OrderActivity> orderActivityList = orderActivityRepository.findAll();
        assertThat(orderActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderActivity() throws Exception {
        // Initialize the database
        orderActivityRepository.saveAndFlush(orderActivity);
        orderActivitySearchRepository.save(orderActivity);
        int databaseSizeBeforeDelete = orderActivityRepository.findAll().size();

        // Get the orderActivity
        restOrderActivityMockMvc.perform(delete("/api/order-activities/{id}", orderActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean orderActivityExistsInEs = orderActivitySearchRepository.exists(orderActivity.getId());
        assertThat(orderActivityExistsInEs).isFalse();

        // Validate the database is empty
        List<OrderActivity> orderActivityList = orderActivityRepository.findAll();
        assertThat(orderActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrderActivity() throws Exception {
        // Initialize the database
        orderActivityRepository.saveAndFlush(orderActivity);
        orderActivitySearchRepository.save(orderActivity);

        // Search the orderActivity
        restOrderActivityMockMvc.perform(get("/api/_search/order-activities?query=id:" + orderActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountLocalCurrency").value(hasItem(DEFAULT_AMOUNT_LOCAL_CURRENCY.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderActivity.class);
        OrderActivity orderActivity1 = new OrderActivity();
        orderActivity1.setId(1L);
        OrderActivity orderActivity2 = new OrderActivity();
        orderActivity2.setId(orderActivity1.getId());
        assertThat(orderActivity1).isEqualTo(orderActivity2);
        orderActivity2.setId(2L);
        assertThat(orderActivity1).isNotEqualTo(orderActivity2);
        orderActivity1.setId(null);
        assertThat(orderActivity1).isNotEqualTo(orderActivity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderActivityDTO.class);
        OrderActivityDTO orderActivityDTO1 = new OrderActivityDTO();
        orderActivityDTO1.setId(1L);
        OrderActivityDTO orderActivityDTO2 = new OrderActivityDTO();
        assertThat(orderActivityDTO1).isNotEqualTo(orderActivityDTO2);
        orderActivityDTO2.setId(orderActivityDTO1.getId());
        assertThat(orderActivityDTO1).isEqualTo(orderActivityDTO2);
        orderActivityDTO2.setId(2L);
        assertThat(orderActivityDTO1).isNotEqualTo(orderActivityDTO2);
        orderActivityDTO1.setId(null);
        assertThat(orderActivityDTO1).isNotEqualTo(orderActivityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderActivityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderActivityMapper.fromId(null)).isNull();
    }
}
