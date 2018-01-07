package com.vyka.service;

import com.vyka.service.dto.SubjectDTO;
import java.util.List;

/**
 * Service Interface for managing Subject.
 */
public interface SubjectService {

    /**
     * Save a subject.
     *
     * @param subjectDTO the entity to save
     * @return the persisted entity
     */
    SubjectDTO save(SubjectDTO subjectDTO);

    /**
     *  Get all the subjects.
     *
     *  @return the list of entities
     */
    List<SubjectDTO> findAll();

    /**
     *  Get the "id" subject.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SubjectDTO findOne(Long id);

    /**
     *  Delete the "id" subject.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the subject corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<SubjectDTO> search(String query);
}
