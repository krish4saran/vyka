package com.vyka.service;

import com.vyka.service.dto.ClassLengthDTO;
import java.util.List;

/**
 * Service Interface for managing ClassLength.
 */
public interface ClassLengthService {

    /**
     * Save a classLength.
     *
     * @param classLengthDTO the entity to save
     * @return the persisted entity
     */
    ClassLengthDTO save(ClassLengthDTO classLengthDTO);

    /**
     *  Get all the classLengths.
     *
     *  @return the list of entities
     */
    List<ClassLengthDTO> findAll();

    /**
     *  Get the "id" classLength.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassLengthDTO findOne(Long id);

    /**
     *  Delete the "id" classLength.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the classLength corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ClassLengthDTO> search(String query);
}
