package com.vyka.service.impl;

import com.vyka.service.PaypalPaymentService;
import com.vyka.domain.PaypalPayment;
import com.vyka.repository.PaypalPaymentRepository;
import com.vyka.repository.search.PaypalPaymentSearchRepository;
import com.vyka.service.dto.PaypalPaymentDTO;
import com.vyka.service.mapper.PaypalPaymentMapper;
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
 * Service Implementation for managing PaypalPayment.
 */
@Service
@Transactional
public class PaypalPaymentServiceImpl implements PaypalPaymentService{

    private final Logger log = LoggerFactory.getLogger(PaypalPaymentServiceImpl.class);

    private final PaypalPaymentRepository paypalPaymentRepository;

    private final PaypalPaymentMapper paypalPaymentMapper;

    private final PaypalPaymentSearchRepository paypalPaymentSearchRepository;

    public PaypalPaymentServiceImpl(PaypalPaymentRepository paypalPaymentRepository, PaypalPaymentMapper paypalPaymentMapper, PaypalPaymentSearchRepository paypalPaymentSearchRepository) {
        this.paypalPaymentRepository = paypalPaymentRepository;
        this.paypalPaymentMapper = paypalPaymentMapper;
        this.paypalPaymentSearchRepository = paypalPaymentSearchRepository;
    }

    /**
     * Save a paypalPayment.
     *
     * @param paypalPaymentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaypalPaymentDTO save(PaypalPaymentDTO paypalPaymentDTO) {
        log.debug("Request to save PaypalPayment : {}", paypalPaymentDTO);
        PaypalPayment paypalPayment = paypalPaymentMapper.toEntity(paypalPaymentDTO);
        paypalPayment = paypalPaymentRepository.save(paypalPayment);
        PaypalPaymentDTO result = paypalPaymentMapper.toDto(paypalPayment);
        paypalPaymentSearchRepository.save(paypalPayment);
        return result;
    }

    /**
     *  Get all the paypalPayments.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaypalPaymentDTO> findAll() {
        log.debug("Request to get all PaypalPayments");
        return paypalPaymentRepository.findAll().stream()
            .map(paypalPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one paypalPayment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaypalPaymentDTO findOne(Long id) {
        log.debug("Request to get PaypalPayment : {}", id);
        PaypalPayment paypalPayment = paypalPaymentRepository.findOne(id);
        return paypalPaymentMapper.toDto(paypalPayment);
    }

    /**
     *  Delete the  paypalPayment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaypalPayment : {}", id);
        paypalPaymentRepository.delete(id);
        paypalPaymentSearchRepository.delete(id);
    }

    /**
     * Search for the paypalPayment corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaypalPaymentDTO> search(String query) {
        log.debug("Request to search PaypalPayments for query {}", query);
        return StreamSupport
            .stream(paypalPaymentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(paypalPaymentMapper::toDto)
            .collect(Collectors.toList());
    }
}
