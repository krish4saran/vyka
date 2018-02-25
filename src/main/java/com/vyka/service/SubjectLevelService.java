package com.vyka.service;

import com.vyka.service.dto.SubjectLevelDTO;
import java.util.List;

/**
 * Service Interface for managing SubjectLevel.
 */
public interface SubjectLevelService {

    /**
     * Save a subjectLevel.
     *
     * @param subjectLevelDTO the entity to save
     * @return the persisted entity
     */
    SubjectLevelDTO save(SubjectLevelDTO subjectLevelDTO);

    /**
     *  Get all the subjectLevels.
     *
     *  @return the list of entities
     */
    List<SubjectLevelDTO> findAll();

    /**
     *  Get the "id" subjectLevel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SubjectLevelDTO findOne(Long id);

    /**
     *  Delete the "id" subjectLevel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the subjectLevel corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<SubjectLevelDTO> search(String query);
}
