package com.vyka.service.impl;

import com.vyka.service.CreditCardPaymentService;
import com.vyka.domain.CreditCardPayment;
import com.vyka.repository.CreditCardPaymentRepository;
import com.vyka.repository.search.CreditCardPaymentSearchRepository;
import com.vyka.service.dto.CreditCardPaymentDTO;
import com.vyka.service.mapper.CreditCardPaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CreditCardPayment.
 */
@Service
@Transactional
public class CreditCardPaymentServiceImpl implements CreditCardPaymentService{

    private final Logger log = LoggerFactory.getLogger(CreditCardPaymentServiceImpl.class);

    private final CreditCardPaymentRepository creditCardPaymentRepository;

    private final CreditCardPaymentMapper creditCardPaymentMapper;

    private final CreditCardPaymentSearchRepository creditCardPaymentSearchRepository;

    public CreditCardPaymentServiceImpl(CreditCardPaymentRepository creditCardPaymentRepository, CreditCardPaymentMapper creditCardPaymentMapper, CreditCardPaymentSearchRepository creditCardPaymentSearchRepository) {
        this.creditCardPaymentRepository = creditCardPaymentRepository;
        this.creditCardPaymentMapper = creditCardPaymentMapper;
        this.creditCardPaymentSearchRepository = creditCardPaymentSearchRepository;
    }

    /**
     * Save a creditCardPayment.
     *
     * @param creditCardPaymentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CreditCardPaymentDTO save(CreditCardPaymentDTO creditCardPaymentDTO) {
        log.debug("Request to save CreditCardPayment : {}", creditCardPaymentDTO);
        CreditCardPayment creditCardPayment = creditCardPaymentMapper.toEntity(creditCardPaymentDTO);
        creditCardPayment = creditCardPaymentRepository.save(creditCardPayment);
        CreditCardPaymentDTO result = creditCardPaymentMapper.toDto(creditCardPayment);
        creditCardPaymentSearchRepository.save(creditCardPayment);
        return result;
    }

    /**
     *  Get all the creditCardPayments.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CreditCardPaymentDTO> findAll() {
        log.debug("Request to get all CreditCardPayments");
        return creditCardPaymentRepository.findAll().stream()
            .map(creditCardPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one creditCardPayment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CreditCardPaymentDTO findOne(Long id) {
        log.debug("Request to get CreditCardPayment : {}", id);
        CreditCardPayment creditCardPayment = creditCardPaymentRepository.findOne(id);
        return creditCardPaymentMapper.toDto(creditCardPayment);
    }

    /**
     *  Delete the  creditCardPayment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditCardPayment : {}", id);
        creditCardPaymentRepository.delete(id);
        creditCardPaymentSearchRepository.delete(id);
    }

    /**
     * Search for the creditCardPayment corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CreditCardPaymentDTO> search(String query) {
        log.debug("Request to search CreditCardPayments for query {}", query);
        return StreamSupport
            .stream(creditCardPaymentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(creditCardPaymentMapper::toDto)
            .collect(Collectors.toList());
    }
}
