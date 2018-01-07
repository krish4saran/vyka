package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.AvailabilityService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.service.dto.AvailabilityDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Availability.
 */
@RestController
@RequestMapping("/api")
public class AvailabilityResource {

    private final Logger log = LoggerFactory.getLogger(AvailabilityResource.class);

    private static final String ENTITY_NAME = "availability";

    private final AvailabilityService availabilityService;

    public AvailabilityResource(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    /**
     * POST  /availabilities : Create a new availability.
     *
     * @param availabilityDTO the availabilityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new availabilityDTO, or with status 400 (Bad Request) if the availability has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/availabilities")
    @Timed
    public ResponseEntity<AvailabilityDTO> createAvailability(@Valid @RequestBody AvailabilityDTO availabilityDTO) throws URISyntaxException {
        log.debug("REST request to save Availability : {}", availabilityDTO);
        if (availabilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new availability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvailabilityDTO result = availabilityService.save(availabilityDTO);
        return ResponseEntity.created(new URI("/api/availabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /availabilities : Updates an existing availability.
     *
     * @param availabilityDTO the availabilityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated availabilityDTO,
     * or with status 400 (Bad Request) if the availabilityDTO is not valid,
     * or with status 500 (Internal Server Error) if the availabilityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/availabilities")
    @Timed
    public ResponseEntity<AvailabilityDTO> updateAvailability(@Valid @RequestBody AvailabilityDTO availabilityDTO) throws URISyntaxException {
        log.debug("REST request to update Availability : {}", availabilityDTO);
        if (availabilityDTO.getId() == null) {
            return createAvailability(availabilityDTO);
        }
        AvailabilityDTO result = availabilityService.save(availabilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, availabilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /availabilities : get all the availabilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of availabilities in body
     */
    @GetMapping("/availabilities")
    @Timed
    public List<AvailabilityDTO> getAllAvailabilities() {
        log.debug("REST request to get all Availabilities");
        return availabilityService.findAll();
        }

    /**
     * GET  /availabilities/:id : get the "id" availability.
     *
     * @param id the id of the availabilityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the availabilityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/availabilities/{id}")
    @Timed
    public ResponseEntity<AvailabilityDTO> getAvailability(@PathVariable Long id) {
        log.debug("REST request to get Availability : {}", id);
        AvailabilityDTO availabilityDTO = availabilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(availabilityDTO));
    }

    /**
     * DELETE  /availabilities/:id : delete the "id" availability.
     *
     * @param id the id of the availabilityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/availabilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) {
        log.debug("REST request to delete Availability : {}", id);
        availabilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/availabilities?query=:query : search for the availability corresponding
     * to the query.
     *
     * @param query the query of the availability search
     * @return the result of the search
     */
    @GetMapping("/_search/availabilities")
    @Timed
    public List<AvailabilityDTO> searchAvailabilities(@RequestParam String query) {
        log.debug("REST request to search Availabilities for query {}", query);
        return availabilityService.search(query);
    }

}
