package com.vyka.service;

import com.vyka.service.dto.EducationDTO;
import java.util.List;

/**
 * Service Interface for managing Education.
 */
public interface EducationService {

    /**
     * Save a education.
     *
     * @param educationDTO the entity to save
     * @return the persisted entity
     */
    EducationDTO save(EducationDTO educationDTO);

    /**
     *  Get all the educations.
     *
     *  @return the list of entities
     */
    List<EducationDTO> findAll();

    /**
     *  Get the "id" education.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EducationDTO findOne(Long id);

    /**
     *  Delete the "id" education.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the education corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<EducationDTO> search(String query);
}
