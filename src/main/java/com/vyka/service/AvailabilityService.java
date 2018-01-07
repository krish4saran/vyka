package com.vyka.service;

import com.vyka.service.dto.AvailabilityDTO;
import java.util.List;

/**
 * Service Interface for managing Availability.
 */
public interface AvailabilityService {

    /**
     * Save a availability.
     *
     * @param availabilityDTO the entity to save
     * @return the persisted entity
     */
    AvailabilityDTO save(AvailabilityDTO availabilityDTO);

    /**
     *  Get all the availabilities.
     *
     *  @return the list of entities
     */
    List<AvailabilityDTO> findAll();

    /**
     *  Get the "id" availability.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AvailabilityDTO findOne(Long id);

    /**
     *  Delete the "id" availability.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the availability corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<AvailabilityDTO> search(String query);
}
