package com.vyka.service;

import com.vyka.service.dto.PackageOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PackageOrder.
 */
public interface PackageOrderService {

    /**
     * Save a packageOrder.
     *
     * @param packageOrderDTO the entity to save
     * @return the persisted entity
     */
    PackageOrderDTO save(PackageOrderDTO packageOrderDTO);

    /**
     *  Get all the packageOrders.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PackageOrderDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" packageOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PackageOrderDTO findOne(Long id);

    /**
     *  Delete the "id" packageOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the packageOrder corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PackageOrderDTO> search(String query, Pageable pageable);
}
