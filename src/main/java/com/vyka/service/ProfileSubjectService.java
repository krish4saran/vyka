package com.vyka.service;

import com.vyka.service.dto.ProfileSubjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProfileSubject.
 */
public interface ProfileSubjectService {

    /**
     * Save a profileSubject.
     *
     * @param profileSubjectDTO the entity to save
     * @return the persisted entity
     */
    ProfileSubjectDTO save(ProfileSubjectDTO profileSubjectDTO);

    /**
     *  Get all the profileSubjects.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProfileSubjectDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" profileSubject.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProfileSubjectDTO findOne(Long id);

    /**
     *  Delete the "id" profileSubject.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the profileSubject corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProfileSubjectDTO> search(String query, Pageable pageable);
}
