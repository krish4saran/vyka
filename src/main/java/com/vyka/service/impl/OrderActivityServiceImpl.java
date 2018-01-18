package com.vyka.service.impl;

import com.vyka.service.OrderActivityService;
import com.vyka.domain.OrderActivity;
import com.vyka.repository.OrderActivityRepository;
import com.vyka.repository.search.OrderActivitySearchRepository;
import com.vyka.service.dto.OrderActivityDTO;
import com.vyka.service.mapper.OrderActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OrderActivity.
 */
@Service
@Transactional
public class OrderActivityServiceImpl implements OrderActivityService{

    private final Logger log = LoggerFactory.getLogger(OrderActivityServiceImpl.class);

    private final OrderActivityRepository orderActivityRepository;

    private final OrderActivityMapper orderActivityMapper;

    private final OrderActivitySearchRepository orderActivitySearchRepository;

    public OrderActivityServiceImpl(OrderActivityRepository orderActivityRepository, OrderActivityMapper orderActivityMapper, OrderActivitySearchRepository orderActivitySearchRepository) {
        this.orderActivityRepository = orderActivityRepository;
        this.orderActivityMapper = orderActivityMapper;
        this.orderActivitySearchRepository = orderActivitySearchRepository;
    }

    /**
     * Save a orderActivity.
     *
     * @param orderActivityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderActivityDTO save(OrderActivityDTO orderActivityDTO) {
        log.debug("Request to save OrderActivity : {}", orderActivityDTO);
        OrderActivity orderActivity = orderActivityMapper.toEntity(orderActivityDTO);
        orderActivity = orderActivityRepository.save(orderActivity);
        OrderActivityDTO result = orderActivityMapper.toDto(orderActivity);
        orderActivitySearchRepository.save(orderActivity);
        return result;
    }

    /**
     *  Get all the orderActivities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderActivities");
        return orderActivityRepository.findAll(pageable)
            .map(orderActivityMapper::toDto);
    }

    /**
     *  Get one orderActivity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrderActivityDTO findOne(Long id) {
        log.debug("Request to get OrderActivity : {}", id);
        OrderActivity orderActivity = orderActivityRepository.findOne(id);
        return orderActivityMapper.toDto(orderActivity);
    }

    /**
     *  Delete the  orderActivity by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderActivity : {}", id);
        orderActivityRepository.delete(id);
        orderActivitySearchRepository.delete(id);
    }

    /**
     * Search for the orderActivity corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderActivityDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrderActivities for query {}", query);
        Page<OrderActivity> result = orderActivitySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(orderActivityMapper::toDto);
    }
}
