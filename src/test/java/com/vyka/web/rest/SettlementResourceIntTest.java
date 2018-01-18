package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.Settlement;
import com.vyka.repository.SettlementRepository;
import com.vyka.service.SettlementService;
import com.vyka.repository.search.SettlementSearchRepository;
import com.vyka.service.dto.SettlementDTO;
import com.vyka.service.mapper.SettlementMapper;
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

import com.vyka.domain.enumeration.SettlementType;
import com.vyka.domain.enumeration.SettlementStatus;
/**
 * Test class for the SettlementResource REST controller.
 *
 * @see SettlementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class SettlementResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final SettlementType DEFAULT_SETTLEMENT_TYPE = SettlementType.SETTLED;
    private static final SettlementType UPDATED_SETTLEMENT_TYPE = SettlementType.REFUNDED;

    private static final Integer DEFAULT_ATTEMPTS = 3;
    private static final Integer UPDATED_ATTEMPTS = 2;

    private static final SettlementStatus DEFAULT_STATUS = SettlementStatus.NEW;
    private static final SettlementStatus UPDATED_STATUS = SettlementStatus.COMPLETED;

    private static final Instant DEFAULT_SETTLEMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SETTLEMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESSOR_RESPONSE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSOR_RESPONSE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESSOR_RESPONSE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSOR_RESPONSE_TEXT = "BBBBBBBBBB";

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private SettlementMapper settlementMapper;

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private SettlementSearchRepository settlementSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSettlementMockMvc;

    private Settlement settlement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SettlementResource settlementResource = new SettlementResource(settlementService);
        this.restSettlementMockMvc = MockMvcBuilders.standaloneSetup(settlementResource)
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
    public static Settlement createEntity(EntityManager em) {
        Settlement settlement = new Settlement()
            .amount(DEFAULT_AMOUNT)
            .settlementType(DEFAULT_SETTLEMENT_TYPE)
            .attempts(DEFAULT_ATTEMPTS)
            .status(DEFAULT_STATUS)
            .settlementDate(DEFAULT_SETTLEMENT_DATE)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .processorResponseCode(DEFAULT_PROCESSOR_RESPONSE_CODE)
            .processorResponseText(DEFAULT_PROCESSOR_RESPONSE_TEXT);
        return settlement;
    }

    @Before
    public void initTest() {
        settlementSearchRepository.deleteAll();
        settlement = createEntity(em);
    }

    @Test
    @Transactional
    public void createSettlement() throws Exception {
        int databaseSizeBeforeCreate = settlementRepository.findAll().size();

        // Create the Settlement
        SettlementDTO settlementDTO = settlementMapper.toDto(settlement);
        restSettlementMockMvc.perform(post("/api/settlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settlementDTO)))
            .andExpect(status().isCreated());

        // Validate the Settlement in the database
        List<Settlement> settlementList = settlementRepository.findAll();
        assertThat(settlementList).hasSize(databaseSizeBeforeCreate + 1);
        Settlement testSettlement = settlementList.get(settlementList.size() - 1);
        assertThat(testSettlement.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSettlement.getSettlementType()).isEqualTo(DEFAULT_SETTLEMENT_TYPE);
        assertThat(testSettlement.getAttempts()).isEqualTo(DEFAULT_ATTEMPTS);
        assertThat(testSettlement.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSettlement.getSettlementDate()).isEqualTo(DEFAULT_SETTLEMENT_DATE);
        assertThat(testSettlement.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testSettlement.getProcessorResponseCode()).isEqualTo(DEFAULT_PROCESSOR_RESPONSE_CODE);
        assertThat(testSettlement.getProcessorResponseText()).isEqualTo(DEFAULT_PROCESSOR_RESPONSE_TEXT);

        // Validate the Settlement in Elasticsearch
        Settlement settlementEs = settlementSearchRepository.findOne(testSettlement.getId());
        assertThat(settlementEs).isEqualToComparingFieldByField(testSettlement);
    }

    @Test
    @Transactional
    public void createSettlementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = settlementRepository.findAll().size();

        // Create the Settlement with an existing ID
        settlement.setId(1L);
        SettlementDTO settlementDTO = settlementMapper.toDto(settlement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettlementMockMvc.perform(post("/api/settlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settlementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Settlement in the database
        List<Settlement> settlementList = settlementRepository.findAll();
        assertThat(settlementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = settlementRepository.findAll().size();
        // set the field null
        settlement.setAmount(null);

        // Create the Settlement, which fails.
        SettlementDTO settlementDTO = settlementMapper.toDto(settlement);

        restSettlementMockMvc.perform(post("/api/settlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settlementDTO)))
            .andExpect(status().isBadRequest());

        List<Settlement> settlementList = settlementRepository.findAll();
        assertThat(settlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSettlementTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = settlementRepository.findAll().size();
        // set the field null
        settlement.setSettlementType(null);

        // Create the Settlement, which fails.
        SettlementDTO settlementDTO = settlementMapper.toDto(settlement);

        restSettlementMockMvc.perform(post("/api/settlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settlementDTO)))
            .andExpect(status().isBadRequest());

        List<Settlement> settlementList = settlementRepository.findAll();
        assertThat(settlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttemptsIsRequired() throws Exception {
        int databaseSizeBeforeTest = settlementRepository.findAll().size();
        // set the field null
        settlement.setAttempts(null);

        // Create the Settlement, which fails.
        SettlementDTO settlementDTO = settlementMapper.toDto(settlement);

        restSettlementMockMvc.perform(post("/api/settlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settlementDTO)))
            .andExpect(status().isBadRequest());

        List<Settlement> settlementList = settlementRepository.findAll();
        assertThat(settlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = settlementRepository.findAll().size();
        // set the field null
        settlement.setStatus(null);

        // Create the Settlement, which fails.
        SettlementDTO settlementDTO = settlementMapper.toDto(settlement);

        restSettlementMockMvc.perform(post("/api/settlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settlementDTO)))
            .andExpect(status().isBadRequest());

        List<Settlement> settlementList = settlementRepository.findAll();
        assertThat(settlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSettlementDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = settlementRepository.findAll().size();
        // set the field null
        settlement.setSettlementDate(null);

        // Create the Settlement, which fails.
        SettlementDTO settlementDTO = settlementMapper.toDto(settlement);

        restSettlementMockMvc.perform(post("/api/settlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settlementDTO)))
            .andExpect(status().isBadRequest());

        List<Settlement> settlementList = settlementRepository.findAll();
        assertThat(settlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSettlements() throws Exception {
        // Initialize the database
        settlementRepository.saveAndFlush(settlement);

        // Get all the settlementList
        restSettlementMockMvc.perform(get("/api/settlements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(settlement.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].settlementType").value(hasItem(DEFAULT_SETTLEMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].attempts").value(hasItem(DEFAULT_ATTEMPTS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].settlementDate").value(hasItem(DEFAULT_SETTLEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.toString())))
            .andExpect(jsonPath("$.[*].processorResponseCode").value(hasItem(DEFAULT_PROCESSOR_RESPONSE_CODE.toString())))
            .andExpect(jsonPath("$.[*].processorResponseText").value(hasItem(DEFAULT_PROCESSOR_RESPONSE_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getSettlement() throws Exception {
        // Initialize the database
        settlementRepository.saveAndFlush(settlement);

        // Get the settlement
        restSettlementMockMvc.perform(get("/api/settlements/{id}", settlement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(settlement.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.settlementType").value(DEFAULT_SETTLEMENT_TYPE.toString()))
            .andExpect(jsonPath("$.attempts").value(DEFAULT_ATTEMPTS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.settlementDate").value(DEFAULT_SETTLEMENT_DATE.toString()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID.toString()))
            .andExpect(jsonPath("$.processorResponseCode").value(DEFAULT_PROCESSOR_RESPONSE_CODE.toString()))
            .andExpect(jsonPath("$.processorResponseText").value(DEFAULT_PROCESSOR_RESPONSE_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSettlement() throws Exception {
        // Get the settlement
        restSettlementMockMvc.perform(get("/api/settlements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSettlement() throws Exception {
        // Initialize the database
        settlementRepository.saveAndFlush(settlement);
        settlementSearchRepository.save(settlement);
        int databaseSizeBeforeUpdate = settlementRepository.findAll().size();

        // Update the settlement
        Settlement updatedSettlement = settlementRepository.findOne(settlement.getId());
        updatedSettlement
            .amount(UPDATED_AMOUNT)
            .settlementType(UPDATED_SETTLEMENT_TYPE)
            .attempts(UPDATED_ATTEMPTS)
            .status(UPDATED_STATUS)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .processorResponseCode(UPDATED_PROCESSOR_RESPONSE_CODE)
            .processorResponseText(UPDATED_PROCESSOR_RESPONSE_TEXT);
        SettlementDTO settlementDTO = settlementMapper.toDto(updatedSettlement);

        restSettlementMockMvc.perform(put("/api/settlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settlementDTO)))
            .andExpect(status().isOk());

        // Validate the Settlement in the database
        List<Settlement> settlementList = settlementRepository.findAll();
        assertThat(settlementList).hasSize(databaseSizeBeforeUpdate);
        Settlement testSettlement = settlementList.get(settlementList.size() - 1);
        assertThat(testSettlement.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSettlement.getSettlementType()).isEqualTo(UPDATED_SETTLEMENT_TYPE);
        assertThat(testSettlement.getAttempts()).isEqualTo(UPDATED_ATTEMPTS);
        assertThat(testSettlement.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSettlement.getSettlementDate()).isEqualTo(UPDATED_SETTLEMENT_DATE);
        assertThat(testSettlement.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testSettlement.getProcessorResponseCode()).isEqualTo(UPDATED_PROCESSOR_RESPONSE_CODE);
        assertThat(testSettlement.getProcessorResponseText()).isEqualTo(UPDATED_PROCESSOR_RESPONSE_TEXT);

        // Validate the Settlement in Elasticsearch
        Settlement settlementEs = settlementSearchRepository.findOne(testSettlement.getId());
        assertThat(settlementEs).isEqualToComparingFieldByField(testSettlement);
    }

    @Test
    @Transactional
    public void updateNonExistingSettlement() throws Exception {
        int databaseSizeBeforeUpdate = settlementRepository.findAll().size();

        // Create the Settlement
        SettlementDTO settlementDTO = settlementMapper.toDto(settlement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSettlementMockMvc.perform(put("/api/settlements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settlementDTO)))
            .andExpect(status().isCreated());

        // Validate the Settlement in the database
        List<Settlement> settlementList = settlementRepository.findAll();
        assertThat(settlementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSettlement() throws Exception {
        // Initialize the database
        settlementRepository.saveAndFlush(settlement);
        settlementSearchRepository.save(settlement);
        int databaseSizeBeforeDelete = settlementRepository.findAll().size();

        // Get the settlement
        restSettlementMockMvc.perform(delete("/api/settlements/{id}", settlement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean settlementExistsInEs = settlementSearchRepository.exists(settlement.getId());
        assertThat(settlementExistsInEs).isFalse();

        // Validate the database is empty
        List<Settlement> settlementList = settlementRepository.findAll();
        assertThat(settlementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSettlement() throws Exception {
        // Initialize the database
        settlementRepository.saveAndFlush(settlement);
        settlementSearchRepository.save(settlement);

        // Search the settlement
        restSettlementMockMvc.perform(get("/api/_search/settlements?query=id:" + settlement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(settlement.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].settlementType").value(hasItem(DEFAULT_SETTLEMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].attempts").value(hasItem(DEFAULT_ATTEMPTS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].settlementDate").value(hasItem(DEFAULT_SETTLEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.toString())))
            .andExpect(jsonPath("$.[*].processorResponseCode").value(hasItem(DEFAULT_PROCESSOR_RESPONSE_CODE.toString())))
            .andExpect(jsonPath("$.[*].processorResponseText").value(hasItem(DEFAULT_PROCESSOR_RESPONSE_TEXT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Settlement.class);
        Settlement settlement1 = new Settlement();
        settlement1.setId(1L);
        Settlement settlement2 = new Settlement();
        settlement2.setId(settlement1.getId());
        assertThat(settlement1).isEqualTo(settlement2);
        settlement2.setId(2L);
        assertThat(settlement1).isNotEqualTo(settlement2);
        settlement1.setId(null);
        assertThat(settlement1).isNotEqualTo(settlement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettlementDTO.class);
        SettlementDTO settlementDTO1 = new SettlementDTO();
        settlementDTO1.setId(1L);
        SettlementDTO settlementDTO2 = new SettlementDTO();
        assertThat(settlementDTO1).isNotEqualTo(settlementDTO2);
        settlementDTO2.setId(settlementDTO1.getId());
        assertThat(settlementDTO1).isEqualTo(settlementDTO2);
        settlementDTO2.setId(2L);
        assertThat(settlementDTO1).isNotEqualTo(settlementDTO2);
        settlementDTO1.setId(null);
        assertThat(settlementDTO1).isNotEqualTo(settlementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(settlementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(settlementMapper.fromId(null)).isNull();
    }
}
