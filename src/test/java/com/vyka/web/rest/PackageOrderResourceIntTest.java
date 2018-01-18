package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.PackageOrder;
import com.vyka.repository.PackageOrderRepository;
import com.vyka.service.PackageOrderService;
import com.vyka.repository.search.PackageOrderSearchRepository;
import com.vyka.service.dto.PackageOrderDTO;
import com.vyka.service.mapper.PackageOrderMapper;
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

import com.vyka.domain.enumeration.OrderStatus;
/**
 * Test class for the PackageOrderResource REST controller.
 *
 * @see PackageOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class PackageOrderResourceIntTest {

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Integer DEFAULT_PROFILE_SUBJECT_ID = 1;
    private static final Integer UPDATED_PROFILE_SUBJECT_ID = 2;

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.OPEN;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.CANCELED;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_STUDENT_ID = 1;
    private static final Integer UPDATED_STUDENT_ID = 2;

    @Autowired
    private PackageOrderRepository packageOrderRepository;

    @Autowired
    private PackageOrderMapper packageOrderMapper;

    @Autowired
    private PackageOrderService packageOrderService;

    @Autowired
    private PackageOrderSearchRepository packageOrderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPackageOrderMockMvc;

    private PackageOrder packageOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PackageOrderResource packageOrderResource = new PackageOrderResource(packageOrderService);
        this.restPackageOrderMockMvc = MockMvcBuilders.standaloneSetup(packageOrderResource)
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
    public static PackageOrder createEntity(EntityManager em) {
        PackageOrder packageOrder = new PackageOrder()
            .rate(DEFAULT_RATE)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .quantity(DEFAULT_QUANTITY)
            .profileSubjectId(DEFAULT_PROFILE_SUBJECT_ID)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .studentId(DEFAULT_STUDENT_ID);
        return packageOrder;
    }

    @Before
    public void initTest() {
        packageOrderSearchRepository.deleteAll();
        packageOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPackageOrder() throws Exception {
        int databaseSizeBeforeCreate = packageOrderRepository.findAll().size();

        // Create the PackageOrder
        PackageOrderDTO packageOrderDTO = packageOrderMapper.toDto(packageOrder);
        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the PackageOrder in the database
        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeCreate + 1);
        PackageOrder testPackageOrder = packageOrderList.get(packageOrderList.size() - 1);
        assertThat(testPackageOrder.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testPackageOrder.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testPackageOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPackageOrder.getProfileSubjectId()).isEqualTo(DEFAULT_PROFILE_SUBJECT_ID);
        assertThat(testPackageOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPackageOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPackageOrder.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testPackageOrder.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);

        // Validate the PackageOrder in Elasticsearch
        PackageOrder packageOrderEs = packageOrderSearchRepository.findOne(testPackageOrder.getId());
        assertThat(packageOrderEs).isEqualToComparingFieldByField(testPackageOrder);
    }

    @Test
    @Transactional
    public void createPackageOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = packageOrderRepository.findAll().size();

        // Create the PackageOrder with an existing ID
        packageOrder.setId(1L);
        PackageOrderDTO packageOrderDTO = packageOrderMapper.toDto(packageOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PackageOrder in the database
        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProfileSubjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageOrderRepository.findAll().size();
        // set the field null
        packageOrder.setProfileSubjectId(null);

        // Create the PackageOrder, which fails.
        PackageOrderDTO packageOrderDTO = packageOrderMapper.toDto(packageOrder);

        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrderDTO)))
            .andExpect(status().isBadRequest());

        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageOrderRepository.findAll().size();
        // set the field null
        packageOrder.setCreatedDate(null);

        // Create the PackageOrder, which fails.
        PackageOrderDTO packageOrderDTO = packageOrderMapper.toDto(packageOrder);

        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrderDTO)))
            .andExpect(status().isBadRequest());

        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStudentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageOrderRepository.findAll().size();
        // set the field null
        packageOrder.setStudentId(null);

        // Create the PackageOrder, which fails.
        PackageOrderDTO packageOrderDTO = packageOrderMapper.toDto(packageOrder);

        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrderDTO)))
            .andExpect(status().isBadRequest());

        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPackageOrders() throws Exception {
        // Initialize the database
        packageOrderRepository.saveAndFlush(packageOrder);

        // Get all the packageOrderList
        restPackageOrderMockMvc.perform(get("/api/package-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].profileSubjectId").value(hasItem(DEFAULT_PROFILE_SUBJECT_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID)));
    }

    @Test
    @Transactional
    public void getPackageOrder() throws Exception {
        // Initialize the database
        packageOrderRepository.saveAndFlush(packageOrder);

        // Get the packageOrder
        restPackageOrderMockMvc.perform(get("/api/package-orders/{id}", packageOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(packageOrder.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.profileSubjectId").value(DEFAULT_PROFILE_SUBJECT_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID));
    }

    @Test
    @Transactional
    public void getNonExistingPackageOrder() throws Exception {
        // Get the packageOrder
        restPackageOrderMockMvc.perform(get("/api/package-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackageOrder() throws Exception {
        // Initialize the database
        packageOrderRepository.saveAndFlush(packageOrder);
        packageOrderSearchRepository.save(packageOrder);
        int databaseSizeBeforeUpdate = packageOrderRepository.findAll().size();

        // Update the packageOrder
        PackageOrder updatedPackageOrder = packageOrderRepository.findOne(packageOrder.getId());
        updatedPackageOrder
            .rate(UPDATED_RATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .quantity(UPDATED_QUANTITY)
            .profileSubjectId(UPDATED_PROFILE_SUBJECT_ID)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .studentId(UPDATED_STUDENT_ID);
        PackageOrderDTO packageOrderDTO = packageOrderMapper.toDto(updatedPackageOrder);

        restPackageOrderMockMvc.perform(put("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrderDTO)))
            .andExpect(status().isOk());

        // Validate the PackageOrder in the database
        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeUpdate);
        PackageOrder testPackageOrder = packageOrderList.get(packageOrderList.size() - 1);
        assertThat(testPackageOrder.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testPackageOrder.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testPackageOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPackageOrder.getProfileSubjectId()).isEqualTo(UPDATED_PROFILE_SUBJECT_ID);
        assertThat(testPackageOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPackageOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPackageOrder.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testPackageOrder.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);

        // Validate the PackageOrder in Elasticsearch
        PackageOrder packageOrderEs = packageOrderSearchRepository.findOne(testPackageOrder.getId());
        assertThat(packageOrderEs).isEqualToComparingFieldByField(testPackageOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingPackageOrder() throws Exception {
        int databaseSizeBeforeUpdate = packageOrderRepository.findAll().size();

        // Create the PackageOrder
        PackageOrderDTO packageOrderDTO = packageOrderMapper.toDto(packageOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPackageOrderMockMvc.perform(put("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the PackageOrder in the database
        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePackageOrder() throws Exception {
        // Initialize the database
        packageOrderRepository.saveAndFlush(packageOrder);
        packageOrderSearchRepository.save(packageOrder);
        int databaseSizeBeforeDelete = packageOrderRepository.findAll().size();

        // Get the packageOrder
        restPackageOrderMockMvc.perform(delete("/api/package-orders/{id}", packageOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean packageOrderExistsInEs = packageOrderSearchRepository.exists(packageOrder.getId());
        assertThat(packageOrderExistsInEs).isFalse();

        // Validate the database is empty
        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPackageOrder() throws Exception {
        // Initialize the database
        packageOrderRepository.saveAndFlush(packageOrder);
        packageOrderSearchRepository.save(packageOrder);

        // Search the packageOrder
        restPackageOrderMockMvc.perform(get("/api/_search/package-orders?query=id:" + packageOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].profileSubjectId").value(hasItem(DEFAULT_PROFILE_SUBJECT_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageOrder.class);
        PackageOrder packageOrder1 = new PackageOrder();
        packageOrder1.setId(1L);
        PackageOrder packageOrder2 = new PackageOrder();
        packageOrder2.setId(packageOrder1.getId());
        assertThat(packageOrder1).isEqualTo(packageOrder2);
        packageOrder2.setId(2L);
        assertThat(packageOrder1).isNotEqualTo(packageOrder2);
        packageOrder1.setId(null);
        assertThat(packageOrder1).isNotEqualTo(packageOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageOrderDTO.class);
        PackageOrderDTO packageOrderDTO1 = new PackageOrderDTO();
        packageOrderDTO1.setId(1L);
        PackageOrderDTO packageOrderDTO2 = new PackageOrderDTO();
        assertThat(packageOrderDTO1).isNotEqualTo(packageOrderDTO2);
        packageOrderDTO2.setId(packageOrderDTO1.getId());
        assertThat(packageOrderDTO1).isEqualTo(packageOrderDTO2);
        packageOrderDTO2.setId(2L);
        assertThat(packageOrderDTO1).isNotEqualTo(packageOrderDTO2);
        packageOrderDTO1.setId(null);
        assertThat(packageOrderDTO1).isNotEqualTo(packageOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(packageOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(packageOrderMapper.fromId(null)).isNull();
    }
}
