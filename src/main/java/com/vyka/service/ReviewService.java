package com.vyka.service;

import com.vyka.service.dto.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Review.
 */
public interface ReviewService {

    /**
     * Save a review.
     *
     * @param reviewDTO the entity to save
     * @return the persisted entity
     */
    ReviewDTO save(ReviewDTO reviewDTO);

    /**
     *  Get all the reviews.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReviewDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" review.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReviewDTO findOne(Long id);

    /**
     *  Delete the "id" review.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the review corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReviewDTO> search(String query, Pageable pageable);
}
