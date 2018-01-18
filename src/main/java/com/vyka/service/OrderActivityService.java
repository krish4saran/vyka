package com.vyka.service;

import com.vyka.service.dto.OrderActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing OrderActivity.
 */
public interface OrderActivityService {

    /**
     * Save a orderActivity.
     *
     * @param orderActivityDTO the entity to save
     * @return the persisted entity
     */
    OrderActivityDTO save(OrderActivityDTO orderActivityDTO);

    /**
     *  Get all the orderActivities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrderActivityDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" orderActivity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrderActivityDTO findOne(Long id);

    /**
     *  Delete the "id" orderActivity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orderActivity corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrderActivityDTO> search(String query, Pageable pageable);
}
