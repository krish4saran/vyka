package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.Payment;
import com.vyka.repository.PaymentRepository;
import com.vyka.service.PaymentService;
import com.vyka.repository.search.PaymentSearchRepository;
import com.vyka.service.dto.PaymentDTO;
import com.vyka.service.mapper.PaymentMapper;
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

import com.vyka.domain.enumeration.PaymentVia;
/**
 * Test class for the PaymentResource REST controller.
 *
 * @see PaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class PaymentResourceIntTest {

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_LOCAL_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_CURRENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SETTLEMENT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SETTLEMENT_CURRENCY_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CAPTURED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CAPTURED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CANCELED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANCELED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_REFUND_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_REFUND_AMOUNT = new BigDecimal(2);

    private static final PaymentVia DEFAULT_PAYMENT_VIA = PaymentVia.CC;
    private static final PaymentVia UPDATED_PAYMENT_VIA = PaymentVia.PP;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentSearchRepository paymentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentMockMvc;

    private Payment payment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentResource paymentResource = new PaymentResource(paymentService);
        this.restPaymentMockMvc = MockMvcBuilders.standaloneSetup(paymentResource)
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
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .localCurrencyCode(DEFAULT_LOCAL_CURRENCY_CODE)
            .settlementCurrencyCode(DEFAULT_SETTLEMENT_CURRENCY_CODE)
            .capturedAmount(DEFAULT_CAPTURED_AMOUNT)
            .canceledAmount(DEFAULT_CANCELED_AMOUNT)
            .refundAmount(DEFAULT_REFUND_AMOUNT)
            .paymentVia(DEFAULT_PAYMENT_VIA)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return payment;
    }

    @Before
    public void initTest() {
        paymentSearchRepository.deleteAll();
        payment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testPayment.getLocalCurrencyCode()).isEqualTo(DEFAULT_LOCAL_CURRENCY_CODE);
        assertThat(testPayment.getSettlementCurrencyCode()).isEqualTo(DEFAULT_SETTLEMENT_CURRENCY_CODE);
        assertThat(testPayment.getCapturedAmount()).isEqualTo(DEFAULT_CAPTURED_AMOUNT);
        assertThat(testPayment.getCanceledAmount()).isEqualTo(DEFAULT_CANCELED_AMOUNT);
        assertThat(testPayment.getRefundAmount()).isEqualTo(DEFAULT_REFUND_AMOUNT);
        assertThat(testPayment.getPaymentVia()).isEqualTo(DEFAULT_PAYMENT_VIA);
        assertThat(testPayment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayment.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);

        // Validate the Payment in Elasticsearch
        Payment paymentEs = paymentSearchRepository.findOne(testPayment.getId());
        assertThat(paymentEs).isEqualToComparingFieldByField(testPayment);
    }

    @Test
    @Transactional
    public void createPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment with an existing ID
        payment.setId(1L);
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setTotalAmount(null);

        // Create the Payment, which fails.
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setCreatedDate(null);

        // Create the Payment, which fails.
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].localCurrencyCode").value(hasItem(DEFAULT_LOCAL_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].settlementCurrencyCode").value(hasItem(DEFAULT_SETTLEMENT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].capturedAmount").value(hasItem(DEFAULT_CAPTURED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].canceledAmount").value(hasItem(DEFAULT_CANCELED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].refundAmount").value(hasItem(DEFAULT_REFUND_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentVia").value(hasItem(DEFAULT_PAYMENT_VIA.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.localCurrencyCode").value(DEFAULT_LOCAL_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.settlementCurrencyCode").value(DEFAULT_SETTLEMENT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.capturedAmount").value(DEFAULT_CAPTURED_AMOUNT.intValue()))
            .andExpect(jsonPath("$.canceledAmount").value(DEFAULT_CANCELED_AMOUNT.intValue()))
            .andExpect(jsonPath("$.refundAmount").value(DEFAULT_REFUND_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentVia").value(DEFAULT_PAYMENT_VIA.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        paymentSearchRepository.save(payment);
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findOne(payment.getId());
        updatedPayment
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .localCurrencyCode(UPDATED_LOCAL_CURRENCY_CODE)
            .settlementCurrencyCode(UPDATED_SETTLEMENT_CURRENCY_CODE)
            .capturedAmount(UPDATED_CAPTURED_AMOUNT)
            .canceledAmount(UPDATED_CANCELED_AMOUNT)
            .refundAmount(UPDATED_REFUND_AMOUNT)
            .paymentVia(UPDATED_PAYMENT_VIA)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        PaymentDTO paymentDTO = paymentMapper.toDto(updatedPayment);

        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testPayment.getLocalCurrencyCode()).isEqualTo(UPDATED_LOCAL_CURRENCY_CODE);
        assertThat(testPayment.getSettlementCurrencyCode()).isEqualTo(UPDATED_SETTLEMENT_CURRENCY_CODE);
        assertThat(testPayment.getCapturedAmount()).isEqualTo(UPDATED_CAPTURED_AMOUNT);
        assertThat(testPayment.getCanceledAmount()).isEqualTo(UPDATED_CANCELED_AMOUNT);
        assertThat(testPayment.getRefundAmount()).isEqualTo(UPDATED_REFUND_AMOUNT);
        assertThat(testPayment.getPaymentVia()).isEqualTo(UPDATED_PAYMENT_VIA);
        assertThat(testPayment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayment.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);

        // Validate the Payment in Elasticsearch
        Payment paymentEs = paymentSearchRepository.findOne(testPayment.getId());
        assertThat(paymentEs).isEqualToComparingFieldByField(testPayment);
    }

    @Test
    @Transactional
    public void updateNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDTO)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        paymentSearchRepository.save(payment);
        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Get the payment
        restPaymentMockMvc.perform(delete("/api/payments/{id}", payment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean paymentExistsInEs = paymentSearchRepository.exists(payment.getId());
        assertThat(paymentExistsInEs).isFalse();

        // Validate the database is empty
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        paymentSearchRepository.save(payment);

        // Search the payment
        restPaymentMockMvc.perform(get("/api/_search/payments?query=id:" + payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].localCurrencyCode").value(hasItem(DEFAULT_LOCAL_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].settlementCurrencyCode").value(hasItem(DEFAULT_SETTLEMENT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].capturedAmount").value(hasItem(DEFAULT_CAPTURED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].canceledAmount").value(hasItem(DEFAULT_CANCELED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].refundAmount").value(hasItem(DEFAULT_REFUND_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentVia").value(hasItem(DEFAULT_PAYMENT_VIA.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = new Payment();
        payment1.setId(1L);
        Payment payment2 = new Payment();
        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);
        payment2.setId(2L);
        assertThat(payment1).isNotEqualTo(payment2);
        payment1.setId(null);
        assertThat(payment1).isNotEqualTo(payment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentDTO.class);
        PaymentDTO paymentDTO1 = new PaymentDTO();
        paymentDTO1.setId(1L);
        PaymentDTO paymentDTO2 = new PaymentDTO();
        assertThat(paymentDTO1).isNotEqualTo(paymentDTO2);
        paymentDTO2.setId(paymentDTO1.getId());
        assertThat(paymentDTO1).isEqualTo(paymentDTO2);
        paymentDTO2.setId(2L);
        assertThat(paymentDTO1).isNotEqualTo(paymentDTO2);
        paymentDTO1.setId(null);
        assertThat(paymentDTO1).isNotEqualTo(paymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paymentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paymentMapper.fromId(null)).isNull();
    }
}
