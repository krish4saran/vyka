package com.vyka.service;

import com.vyka.service.dto.ChaptersDTO;
import java.util.List;

/**
 * Service Interface for managing Chapters.
 */
public interface ChaptersService {

    /**
     * Save a chapters.
     *
     * @param chaptersDTO the entity to save
     * @return the persisted entity
     */
    ChaptersDTO save(ChaptersDTO chaptersDTO);

    /**
     *  Get all the chapters.
     *
     *  @return the list of entities
     */
    List<ChaptersDTO> findAll();

    /**
     *  Get the "id" chapters.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChaptersDTO findOne(Long id);

    /**
     *  Delete the "id" chapters.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chapters corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ChaptersDTO> search(String query);
}
