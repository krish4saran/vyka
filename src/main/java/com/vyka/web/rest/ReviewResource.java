package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.ReviewService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.web.rest.util.PaginationUtil;
import com.vyka.service.dto.ReviewDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Review.
 */
@RestController
@RequestMapping("/api")
public class ReviewResource {

    private final Logger log = LoggerFactory.getLogger(ReviewResource.class);

    private static final String ENTITY_NAME = "review";

    private final ReviewService reviewService;

    public ReviewResource(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * POST  /reviews : Create a new review.
     *
     * @param reviewDTO the reviewDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reviewDTO, or with status 400 (Bad Request) if the review has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reviews")
    @Timed
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) throws URISyntaxException {
        log.debug("REST request to save Review : {}", reviewDTO);
        if (reviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new review cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewDTO result = reviewService.save(reviewDTO);
        return ResponseEntity.created(new URI("/api/reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reviews : Updates an existing review.
     *
     * @param reviewDTO the reviewDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reviewDTO,
     * or with status 400 (Bad Request) if the reviewDTO is not valid,
     * or with status 500 (Internal Server Error) if the reviewDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reviews")
    @Timed
    public ResponseEntity<ReviewDTO> updateReview(@Valid @RequestBody ReviewDTO reviewDTO) throws URISyntaxException {
        log.debug("REST request to update Review : {}", reviewDTO);
        if (reviewDTO.getId() == null) {
            return createReview(reviewDTO);
        }
        ReviewDTO result = reviewService.save(reviewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reviews : get all the reviews.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reviews in body
     */
    @GetMapping("/reviews")
    @Timed
    public ResponseEntity<List<ReviewDTO>> getAllReviews(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Reviews");
        Page<ReviewDTO> page = reviewService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reviews");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reviews/:id : get the "id" review.
     *
     * @param id the id of the reviewDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reviewDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reviews/{id}")
    @Timed
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        log.debug("REST request to get Review : {}", id);
        ReviewDTO reviewDTO = reviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reviewDTO));
    }

    /**
     * DELETE  /reviews/:id : delete the "id" review.
     *
     * @param id the id of the reviewDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reviews/{id}")
    @Timed
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        log.debug("REST request to delete Review : {}", id);
        reviewService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reviews?query=:query : search for the review corresponding
     * to the query.
     *
     * @param query the query of the review search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/reviews")
    @Timed
    public ResponseEntity<List<ReviewDTO>> searchReviews(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Reviews for query {}", query);
        Page<ReviewDTO> page = reviewService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/reviews");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
