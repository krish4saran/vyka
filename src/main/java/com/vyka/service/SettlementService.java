package com.vyka.service;

import com.vyka.service.dto.SettlementDTO;
import java.util.List;

/**
 * Service Interface for managing Settlement.
 */
public interface SettlementService {

    /**
     * Save a settlement.
     *
     * @param settlementDTO the entity to save
     * @return the persisted entity
     */
    SettlementDTO save(SettlementDTO settlementDTO);

    /**
     *  Get all the settlements.
     *
     *  @return the list of entities
     */
    List<SettlementDTO> findAll();

    /**
     *  Get the "id" settlement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SettlementDTO findOne(Long id);

    /**
     *  Delete the "id" settlement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the settlement corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<SettlementDTO> search(String query);
}
