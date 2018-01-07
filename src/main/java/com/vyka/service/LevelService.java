package com.vyka.service;

import com.vyka.service.dto.LevelDTO;
import java.util.List;

/**
 * Service Interface for managing Level.
 */
public interface LevelService {

    /**
     * Save a level.
     *
     * @param levelDTO the entity to save
     * @return the persisted entity
     */
    LevelDTO save(LevelDTO levelDTO);

    /**
     *  Get all the levels.
     *
     *  @return the list of entities
     */
    List<LevelDTO> findAll();

    /**
     *  Get the "id" level.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LevelDTO findOne(Long id);

    /**
     *  Delete the "id" level.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the level corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<LevelDTO> search(String query);
}
