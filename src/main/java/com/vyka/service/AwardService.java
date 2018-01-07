package com.vyka.service;

import com.vyka.service.dto.AwardDTO;
import java.util.List;

/**
 * Service Interface for managing Award.
 */
public interface AwardService {

    /**
     * Save a award.
     *
     * @param awardDTO the entity to save
     * @return the persisted entity
     */
    AwardDTO save(AwardDTO awardDTO);

    /**
     *  Get all the awards.
     *
     *  @return the list of entities
     */
    List<AwardDTO> findAll();

    /**
     *  Get the "id" award.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AwardDTO findOne(Long id);

    /**
     *  Delete the "id" award.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the award corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<AwardDTO> search(String query);
}
