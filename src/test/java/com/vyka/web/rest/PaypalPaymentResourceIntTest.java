package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.PaypalPayment;
import com.vyka.repository.PaypalPaymentRepository;
import com.vyka.service.PaypalPaymentService;
import com.vyka.repository.search.PaypalPaymentSearchRepository;
import com.vyka.service.dto.PaypalPaymentDTO;
import com.vyka.service.mapper.PaypalPaymentMapper;
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
 * Test class for the PaypalPaymentResource REST controller.
 *
 * @see PaypalPaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class PaypalPaymentResourceIntTest {

    private static final String DEFAULT_PAYPAL_PAYER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYPAL_PAYER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYPAL_PAYER_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYPAL_PAYER_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYER_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYER_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PAYER_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYER_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private PaypalPaymentRepository paypalPaymentRepository;

    @Autowired
    private PaypalPaymentMapper paypalPaymentMapper;

    @Autowired
    private PaypalPaymentService paypalPaymentService;

    @Autowired
    private PaypalPaymentSearchRepository paypalPaymentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaypalPaymentMockMvc;

    private PaypalPayment paypalPayment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaypalPaymentResource paypalPaymentResource = new PaypalPaymentResource(paypalPaymentService);
        this.restPaypalPaymentMockMvc = MockMvcBuilders.standaloneSetup(paypalPaymentResource)
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
    public static PaypalPayment createEntity(EntityManager em) {
        PaypalPayment paypalPayment = new PaypalPayment()
            .paypalPayerId(DEFAULT_PAYPAL_PAYER_ID)
            .paypalPayerEmailId(DEFAULT_PAYPAL_PAYER_EMAIL_ID)
            .payerFirstName(DEFAULT_PAYER_FIRST_NAME)
            .payerLastName(DEFAULT_PAYER_LAST_NAME)
            .paymentNumber(DEFAULT_PAYMENT_NUMBER);
        return paypalPayment;
    }

    @Before
    public void initTest() {
        paypalPaymentSearchRepository.deleteAll();
        paypalPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaypalPayment() throws Exception {
        int databaseSizeBeforeCreate = paypalPaymentRepository.findAll().size();

        // Create the PaypalPayment
        PaypalPaymentDTO paypalPaymentDTO = paypalPaymentMapper.toDto(paypalPayment);
        restPaypalPaymentMockMvc.perform(post("/api/paypal-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the PaypalPayment in the database
        List<PaypalPayment> paypalPaymentList = paypalPaymentRepository.findAll();
        assertThat(paypalPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        PaypalPayment testPaypalPayment = paypalPaymentList.get(paypalPaymentList.size() - 1);
        assertThat(testPaypalPayment.getPaypalPayerId()).isEqualTo(DEFAULT_PAYPAL_PAYER_ID);
        assertThat(testPaypalPayment.getPaypalPayerEmailId()).isEqualTo(DEFAULT_PAYPAL_PAYER_EMAIL_ID);
        assertThat(testPaypalPayment.getPayerFirstName()).isEqualTo(DEFAULT_PAYER_FIRST_NAME);
        assertThat(testPaypalPayment.getPayerLastName()).isEqualTo(DEFAULT_PAYER_LAST_NAME);
        assertThat(testPaypalPayment.getPaymentNumber()).isEqualTo(DEFAULT_PAYMENT_NUMBER);

        // Validate the PaypalPayment in Elasticsearch
        PaypalPayment paypalPaymentEs = paypalPaymentSearchRepository.findOne(testPaypalPayment.getId());
        assertThat(paypalPaymentEs).isEqualToComparingFieldByField(testPaypalPayment);
    }

    @Test
    @Transactional
    public void createPaypalPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paypalPaymentRepository.findAll().size();

        // Create the PaypalPayment with an existing ID
        paypalPayment.setId(1L);
        PaypalPaymentDTO paypalPaymentDTO = paypalPaymentMapper.toDto(paypalPayment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaypalPaymentMockMvc.perform(post("/api/paypal-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaypalPayment in the database
        List<PaypalPayment> paypalPaymentList = paypalPaymentRepository.findAll();
        assertThat(paypalPaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPaypalPayerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalPaymentRepository.findAll().size();
        // set the field null
        paypalPayment.setPaypalPayerId(null);

        // Create the PaypalPayment, which fails.
        PaypalPaymentDTO paypalPaymentDTO = paypalPaymentMapper.toDto(paypalPayment);

        restPaypalPaymentMockMvc.perform(post("/api/paypal-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<PaypalPayment> paypalPaymentList = paypalPaymentRepository.findAll();
        assertThat(paypalPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaypalPayerEmailIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalPaymentRepository.findAll().size();
        // set the field null
        paypalPayment.setPaypalPayerEmailId(null);

        // Create the PaypalPayment, which fails.
        PaypalPaymentDTO paypalPaymentDTO = paypalPaymentMapper.toDto(paypalPayment);

        restPaypalPaymentMockMvc.perform(post("/api/paypal-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<PaypalPayment> paypalPaymentList = paypalPaymentRepository.findAll();
        assertThat(paypalPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaypalPayments() throws Exception {
        // Initialize the database
        paypalPaymentRepository.saveAndFlush(paypalPayment);

        // Get all the paypalPaymentList
        restPaypalPaymentMockMvc.perform(get("/api/paypal-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paypalPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paypalPayerId").value(hasItem(DEFAULT_PAYPAL_PAYER_ID.toString())))
            .andExpect(jsonPath("$.[*].paypalPayerEmailId").value(hasItem(DEFAULT_PAYPAL_PAYER_EMAIL_ID.toString())))
            .andExpect(jsonPath("$.[*].payerFirstName").value(hasItem(DEFAULT_PAYER_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].payerLastName").value(hasItem(DEFAULT_PAYER_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].paymentNumber").value(hasItem(DEFAULT_PAYMENT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getPaypalPayment() throws Exception {
        // Initialize the database
        paypalPaymentRepository.saveAndFlush(paypalPayment);

        // Get the paypalPayment
        restPaypalPaymentMockMvc.perform(get("/api/paypal-payments/{id}", paypalPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paypalPayment.getId().intValue()))
            .andExpect(jsonPath("$.paypalPayerId").value(DEFAULT_PAYPAL_PAYER_ID.toString()))
            .andExpect(jsonPath("$.paypalPayerEmailId").value(DEFAULT_PAYPAL_PAYER_EMAIL_ID.toString()))
            .andExpect(jsonPath("$.payerFirstName").value(DEFAULT_PAYER_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.payerLastName").value(DEFAULT_PAYER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.paymentNumber").value(DEFAULT_PAYMENT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaypalPayment() throws Exception {
        // Get the paypalPayment
        restPaypalPaymentMockMvc.perform(get("/api/paypal-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaypalPayment() throws Exception {
        // Initialize the database
        paypalPaymentRepository.saveAndFlush(paypalPayment);
        paypalPaymentSearchRepository.save(paypalPayment);
        int databaseSizeBeforeUpdate = paypalPaymentRepository.findAll().size();

        // Update the paypalPayment
        PaypalPayment updatedPaypalPayment = paypalPaymentRepository.findOne(paypalPayment.getId());
        updatedPaypalPayment
            .paypalPayerId(UPDATED_PAYPAL_PAYER_ID)
            .paypalPayerEmailId(UPDATED_PAYPAL_PAYER_EMAIL_ID)
            .payerFirstName(UPDATED_PAYER_FIRST_NAME)
            .payerLastName(UPDATED_PAYER_LAST_NAME)
            .paymentNumber(UPDATED_PAYMENT_NUMBER);
        PaypalPaymentDTO paypalPaymentDTO = paypalPaymentMapper.toDto(updatedPaypalPayment);

        restPaypalPaymentMockMvc.perform(put("/api/paypal-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalPaymentDTO)))
            .andExpect(status().isOk());

        // Validate the PaypalPayment in the database
        List<PaypalPayment> paypalPaymentList = paypalPaymentRepository.findAll();
        assertThat(paypalPaymentList).hasSize(databaseSizeBeforeUpdate);
        PaypalPayment testPaypalPayment = paypalPaymentList.get(paypalPaymentList.size() - 1);
        assertThat(testPaypalPayment.getPaypalPayerId()).isEqualTo(UPDATED_PAYPAL_PAYER_ID);
        assertThat(testPaypalPayment.getPaypalPayerEmailId()).isEqualTo(UPDATED_PAYPAL_PAYER_EMAIL_ID);
        assertThat(testPaypalPayment.getPayerFirstName()).isEqualTo(UPDATED_PAYER_FIRST_NAME);
        assertThat(testPaypalPayment.getPayerLastName()).isEqualTo(UPDATED_PAYER_LAST_NAME);
        assertThat(testPaypalPayment.getPaymentNumber()).isEqualTo(UPDATED_PAYMENT_NUMBER);

        // Validate the PaypalPayment in Elasticsearch
        PaypalPayment paypalPaymentEs = paypalPaymentSearchRepository.findOne(testPaypalPayment.getId());
        assertThat(paypalPaymentEs).isEqualToComparingFieldByField(testPaypalPayment);
    }

    @Test
    @Transactional
    public void updateNonExistingPaypalPayment() throws Exception {
        int databaseSizeBeforeUpdate = paypalPaymentRepository.findAll().size();

        // Create the PaypalPayment
        PaypalPaymentDTO paypalPaymentDTO = paypalPaymentMapper.toDto(paypalPayment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaypalPaymentMockMvc.perform(put("/api/paypal-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the PaypalPayment in the database
        List<PaypalPayment> paypalPaymentList = paypalPaymentRepository.findAll();
        assertThat(paypalPaymentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaypalPayment() throws Exception {
        // Initialize the database
        paypalPaymentRepository.saveAndFlush(paypalPayment);
        paypalPaymentSearchRepository.save(paypalPayment);
        int databaseSizeBeforeDelete = paypalPaymentRepository.findAll().size();

        // Get the paypalPayment
        restPaypalPaymentMockMvc.perform(delete("/api/paypal-payments/{id}", paypalPayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean paypalPaymentExistsInEs = paypalPaymentSearchRepository.exists(paypalPayment.getId());
        assertThat(paypalPaymentExistsInEs).isFalse();

        // Validate the database is empty
        List<PaypalPayment> paypalPaymentList = paypalPaymentRepository.findAll();
        assertThat(paypalPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPaypalPayment() throws Exception {
        // Initialize the database
        paypalPaymentRepository.saveAndFlush(paypalPayment);
        paypalPaymentSearchRepository.save(paypalPayment);

        // Search the paypalPayment
        restPaypalPaymentMockMvc.perform(get("/api/_search/paypal-payments?query=id:" + paypalPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paypalPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paypalPayerId").value(hasItem(DEFAULT_PAYPAL_PAYER_ID.toString())))
            .andExpect(jsonPath("$.[*].paypalPayerEmailId").value(hasItem(DEFAULT_PAYPAL_PAYER_EMAIL_ID.toString())))
            .andExpect(jsonPath("$.[*].payerFirstName").value(hasItem(DEFAULT_PAYER_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].payerLastName").value(hasItem(DEFAULT_PAYER_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].paymentNumber").value(hasItem(DEFAULT_PAYMENT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaypalPayment.class);
        PaypalPayment paypalPayment1 = new PaypalPayment();
        paypalPayment1.setId(1L);
        PaypalPayment paypalPayment2 = new PaypalPayment();
        paypalPayment2.setId(paypalPayment1.getId());
        assertThat(paypalPayment1).isEqualTo(paypalPayment2);
        paypalPayment2.setId(2L);
        assertThat(paypalPayment1).isNotEqualTo(paypalPayment2);
        paypalPayment1.setId(null);
        assertThat(paypalPayment1).isNotEqualTo(paypalPayment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaypalPaymentDTO.class);
        PaypalPaymentDTO paypalPaymentDTO1 = new PaypalPaymentDTO();
        paypalPaymentDTO1.setId(1L);
        PaypalPaymentDTO paypalPaymentDTO2 = new PaypalPaymentDTO();
        assertThat(paypalPaymentDTO1).isNotEqualTo(paypalPaymentDTO2);
        paypalPaymentDTO2.setId(paypalPaymentDTO1.getId());
        assertThat(paypalPaymentDTO1).isEqualTo(paypalPaymentDTO2);
        paypalPaymentDTO2.setId(2L);
        assertThat(paypalPaymentDTO1).isNotEqualTo(paypalPaymentDTO2);
        paypalPaymentDTO1.setId(null);
        assertThat(paypalPaymentDTO1).isNotEqualTo(paypalPaymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paypalPaymentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paypalPaymentMapper.fromId(null)).isNull();
    }
}
