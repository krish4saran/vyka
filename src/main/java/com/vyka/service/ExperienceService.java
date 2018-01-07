package com.vyka.service;

import com.vyka.service.dto.ExperienceDTO;
import java.util.List;

/**
 * Service Interface for managing Experience.
 */
public interface ExperienceService {

    /**
     * Save a experience.
     *
     * @param experienceDTO the entity to save
     * @return the persisted entity
     */
    ExperienceDTO save(ExperienceDTO experienceDTO);

    /**
     *  Get all the experiences.
     *
     *  @return the list of entities
     */
    List<ExperienceDTO> findAll();

    /**
     *  Get the "id" experience.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ExperienceDTO findOne(Long id);

    /**
     *  Delete the "id" experience.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the experience corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ExperienceDTO> search(String query);
}
