package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.CreditCardPayment;
import com.vyka.repository.CreditCardPaymentRepository;
import com.vyka.service.CreditCardPaymentService;
import com.vyka.repository.search.CreditCardPaymentSearchRepository;
import com.vyka.service.dto.CreditCardPaymentDTO;
import com.vyka.service.mapper.CreditCardPaymentMapper;
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

import com.vyka.domain.enumeration.CreditCardType;
/**
 * Test class for the CreditCardPaymentResource REST controller.
 *
 * @see CreditCardPaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class CreditCardPaymentResourceIntTest {

    private static final CreditCardType DEFAULT_CC_TYPE = CreditCardType.VISA;
    private static final CreditCardType UPDATED_CC_TYPE = CreditCardType.MASTER_CARD;

    private static final String DEFAULT_LAST_FOUR = "AAAAAAAAAA";
    private static final String UPDATED_LAST_FOUR = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private CreditCardPaymentRepository creditCardPaymentRepository;

    @Autowired
    private CreditCardPaymentMapper creditCardPaymentMapper;

    @Autowired
    private CreditCardPaymentService creditCardPaymentService;

    @Autowired
    private CreditCardPaymentSearchRepository creditCardPaymentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCreditCardPaymentMockMvc;

    private CreditCardPayment creditCardPayment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditCardPaymentResource creditCardPaymentResource = new CreditCardPaymentResource(creditCardPaymentService);
        this.restCreditCardPaymentMockMvc = MockMvcBuilders.standaloneSetup(creditCardPaymentResource)
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
    public static CreditCardPayment createEntity(EntityManager em) {
        CreditCardPayment creditCardPayment = new CreditCardPayment()
            .ccType(DEFAULT_CC_TYPE)
            .lastFour(DEFAULT_LAST_FOUR)
            .cardName(DEFAULT_CARD_NAME)
            .paymentNumber(DEFAULT_PAYMENT_NUMBER);
        return creditCardPayment;
    }

    @Before
    public void initTest() {
        creditCardPaymentSearchRepository.deleteAll();
        creditCardPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditCardPayment() throws Exception {
        int databaseSizeBeforeCreate = creditCardPaymentRepository.findAll().size();

        // Create the CreditCardPayment
        CreditCardPaymentDTO creditCardPaymentDTO = creditCardPaymentMapper.toDto(creditCardPayment);
        restCreditCardPaymentMockMvc.perform(post("/api/credit-card-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditCardPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the CreditCardPayment in the database
        List<CreditCardPayment> creditCardPaymentList = creditCardPaymentRepository.findAll();
        assertThat(creditCardPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        CreditCardPayment testCreditCardPayment = creditCardPaymentList.get(creditCardPaymentList.size() - 1);
        assertThat(testCreditCardPayment.getCcType()).isEqualTo(DEFAULT_CC_TYPE);
        assertThat(testCreditCardPayment.getLastFour()).isEqualTo(DEFAULT_LAST_FOUR);
        assertThat(testCreditCardPayment.getCardName()).isEqualTo(DEFAULT_CARD_NAME);
        assertThat(testCreditCardPayment.getPaymentNumber()).isEqualTo(DEFAULT_PAYMENT_NUMBER);

        // Validate the CreditCardPayment in Elasticsearch
        CreditCardPayment creditCardPaymentEs = creditCardPaymentSearchRepository.findOne(testCreditCardPayment.getId());
        assertThat(creditCardPaymentEs).isEqualToComparingFieldByField(testCreditCardPayment);
    }

    @Test
    @Transactional
    public void createCreditCardPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditCardPaymentRepository.findAll().size();

        // Create the CreditCardPayment with an existing ID
        creditCardPayment.setId(1L);
        CreditCardPaymentDTO creditCardPaymentDTO = creditCardPaymentMapper.toDto(creditCardPayment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditCardPaymentMockMvc.perform(post("/api/credit-card-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditCardPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditCardPayment in the database
        List<CreditCardPayment> creditCardPaymentList = creditCardPaymentRepository.findAll();
        assertThat(creditCardPaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCcTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditCardPaymentRepository.findAll().size();
        // set the field null
        creditCardPayment.setCcType(null);

        // Create the CreditCardPayment, which fails.
        CreditCardPaymentDTO creditCardPaymentDTO = creditCardPaymentMapper.toDto(creditCardPayment);

        restCreditCardPaymentMockMvc.perform(post("/api/credit-card-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditCardPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<CreditCardPayment> creditCardPaymentList = creditCardPaymentRepository.findAll();
        assertThat(creditCardPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastFourIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditCardPaymentRepository.findAll().size();
        // set the field null
        creditCardPayment.setLastFour(null);

        // Create the CreditCardPayment, which fails.
        CreditCardPaymentDTO creditCardPaymentDTO = creditCardPaymentMapper.toDto(creditCardPayment);

        restCreditCardPaymentMockMvc.perform(post("/api/credit-card-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditCardPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<CreditCardPayment> creditCardPaymentList = creditCardPaymentRepository.findAll();
        assertThat(creditCardPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCreditCardPayments() throws Exception {
        // Initialize the database
        creditCardPaymentRepository.saveAndFlush(creditCardPayment);

        // Get all the creditCardPaymentList
        restCreditCardPaymentMockMvc.perform(get("/api/credit-card-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCardPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].ccType").value(hasItem(DEFAULT_CC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lastFour").value(hasItem(DEFAULT_LAST_FOUR.toString())))
            .andExpect(jsonPath("$.[*].cardName").value(hasItem(DEFAULT_CARD_NAME.toString())))
            .andExpect(jsonPath("$.[*].paymentNumber").value(hasItem(DEFAULT_PAYMENT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getCreditCardPayment() throws Exception {
        // Initialize the database
        creditCardPaymentRepository.saveAndFlush(creditCardPayment);

        // Get the creditCardPayment
        restCreditCardPaymentMockMvc.perform(get("/api/credit-card-payments/{id}", creditCardPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(creditCardPayment.getId().intValue()))
            .andExpect(jsonPath("$.ccType").value(DEFAULT_CC_TYPE.toString()))
            .andExpect(jsonPath("$.lastFour").value(DEFAULT_LAST_FOUR.toString()))
            .andExpect(jsonPath("$.cardName").value(DEFAULT_CARD_NAME.toString()))
            .andExpect(jsonPath("$.paymentNumber").value(DEFAULT_PAYMENT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCreditCardPayment() throws Exception {
        // Get the creditCardPayment
        restCreditCardPaymentMockMvc.perform(get("/api/credit-card-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditCardPayment() throws Exception {
        // Initialize the database
        creditCardPaymentRepository.saveAndFlush(creditCardPayment);
        creditCardPaymentSearchRepository.save(creditCardPayment);
        int databaseSizeBeforeUpdate = creditCardPaymentRepository.findAll().size();

        // Update the creditCardPayment
        CreditCardPayment updatedCreditCardPayment = creditCardPaymentRepository.findOne(creditCardPayment.getId());
        updatedCreditCardPayment
            .ccType(UPDATED_CC_TYPE)
            .lastFour(UPDATED_LAST_FOUR)
            .cardName(UPDATED_CARD_NAME)
            .paymentNumber(UPDATED_PAYMENT_NUMBER);
        CreditCardPaymentDTO creditCardPaymentDTO = creditCardPaymentMapper.toDto(updatedCreditCardPayment);

        restCreditCardPaymentMockMvc.perform(put("/api/credit-card-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditCardPaymentDTO)))
            .andExpect(status().isOk());

        // Validate the CreditCardPayment in the database
        List<CreditCardPayment> creditCardPaymentList = creditCardPaymentRepository.findAll();
        assertThat(creditCardPaymentList).hasSize(databaseSizeBeforeUpdate);
        CreditCardPayment testCreditCardPayment = creditCardPaymentList.get(creditCardPaymentList.size() - 1);
        assertThat(testCreditCardPayment.getCcType()).isEqualTo(UPDATED_CC_TYPE);
        assertThat(testCreditCardPayment.getLastFour()).isEqualTo(UPDATED_LAST_FOUR);
        assertThat(testCreditCardPayment.getCardName()).isEqualTo(UPDATED_CARD_NAME);
        assertThat(testCreditCardPayment.getPaymentNumber()).isEqualTo(UPDATED_PAYMENT_NUMBER);

        // Validate the CreditCardPayment in Elasticsearch
        CreditCardPayment creditCardPaymentEs = creditCardPaymentSearchRepository.findOne(testCreditCardPayment.getId());
        assertThat(creditCardPaymentEs).isEqualToComparingFieldByField(testCreditCardPayment);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditCardPayment() throws Exception {
        int databaseSizeBeforeUpdate = creditCardPaymentRepository.findAll().size();

        // Create the CreditCardPayment
        CreditCardPaymentDTO creditCardPaymentDTO = creditCardPaymentMapper.toDto(creditCardPayment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCreditCardPaymentMockMvc.perform(put("/api/credit-card-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditCardPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the CreditCardPayment in the database
        List<CreditCardPayment> creditCardPaymentList = creditCardPaymentRepository.findAll();
        assertThat(creditCardPaymentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCreditCardPayment() throws Exception {
        // Initialize the database
        creditCardPaymentRepository.saveAndFlush(creditCardPayment);
        creditCardPaymentSearchRepository.save(creditCardPayment);
        int databaseSizeBeforeDelete = creditCardPaymentRepository.findAll().size();

        // Get the creditCardPayment
        restCreditCardPaymentMockMvc.perform(delete("/api/credit-card-payments/{id}", creditCardPayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean creditCardPaymentExistsInEs = creditCardPaymentSearchRepository.exists(creditCardPayment.getId());
        assertThat(creditCardPaymentExistsInEs).isFalse();

        // Validate the database is empty
        List<CreditCardPayment> creditCardPaymentList = creditCardPaymentRepository.findAll();
        assertThat(creditCardPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCreditCardPayment() throws Exception {
        // Initialize the database
        creditCardPaymentRepository.saveAndFlush(creditCardPayment);
        creditCardPaymentSearchRepository.save(creditCardPayment);

        // Search the creditCardPayment
        restCreditCardPaymentMockMvc.perform(get("/api/_search/credit-card-payments?query=id:" + creditCardPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCardPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].ccType").value(hasItem(DEFAULT_CC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lastFour").value(hasItem(DEFAULT_LAST_FOUR.toString())))
            .andExpect(jsonPath("$.[*].cardName").value(hasItem(DEFAULT_CARD_NAME.toString())))
            .andExpect(jsonPath("$.[*].paymentNumber").value(hasItem(DEFAULT_PAYMENT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardPayment.class);
        CreditCardPayment creditCardPayment1 = new CreditCardPayment();
        creditCardPayment1.setId(1L);
        CreditCardPayment creditCardPayment2 = new CreditCardPayment();
        creditCardPayment2.setId(creditCardPayment1.getId());
        assertThat(creditCardPayment1).isEqualTo(creditCardPayment2);
        creditCardPayment2.setId(2L);
        assertThat(creditCardPayment1).isNotEqualTo(creditCardPayment2);
        creditCardPayment1.setId(null);
        assertThat(creditCardPayment1).isNotEqualTo(creditCardPayment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardPaymentDTO.class);
        CreditCardPaymentDTO creditCardPaymentDTO1 = new CreditCardPaymentDTO();
        creditCardPaymentDTO1.setId(1L);
        CreditCardPaymentDTO creditCardPaymentDTO2 = new CreditCardPaymentDTO();
        assertThat(creditCardPaymentDTO1).isNotEqualTo(creditCardPaymentDTO2);
        creditCardPaymentDTO2.setId(creditCardPaymentDTO1.getId());
        assertThat(creditCardPaymentDTO1).isEqualTo(creditCardPaymentDTO2);
        creditCardPaymentDTO2.setId(2L);
        assertThat(creditCardPaymentDTO1).isNotEqualTo(creditCardPaymentDTO2);
        creditCardPaymentDTO1.setId(null);
        assertThat(creditCardPaymentDTO1).isNotEqualTo(creditCardPaymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditCardPaymentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditCardPaymentMapper.fromId(null)).isNull();
    }
}
