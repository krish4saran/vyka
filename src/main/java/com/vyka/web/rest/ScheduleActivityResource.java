package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.ScheduleActivityService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.service.dto.ScheduleActivityDTO;
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
 * REST controller for managing ScheduleActivity.
 */
@RestController
@RequestMapping("/api")
public class ScheduleActivityResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleActivityResource.class);

    private static final String ENTITY_NAME = "scheduleActivity";

    private final ScheduleActivityService scheduleActivityService;

    public ScheduleActivityResource(ScheduleActivityService scheduleActivityService) {
        this.scheduleActivityService = scheduleActivityService;
    }

    /**
     * POST  /schedule-activities : Create a new scheduleActivity.
     *
     * @param scheduleActivityDTO the scheduleActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scheduleActivityDTO, or with status 400 (Bad Request) if the scheduleActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schedule-activities")
    @Timed
    public ResponseEntity<ScheduleActivityDTO> createScheduleActivity(@Valid @RequestBody ScheduleActivityDTO scheduleActivityDTO) throws URISyntaxException {
        log.debug("REST request to save ScheduleActivity : {}", scheduleActivityDTO);
        if (scheduleActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheduleActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleActivityDTO result = scheduleActivityService.save(scheduleActivityDTO);
        return ResponseEntity.created(new URI("/api/schedule-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schedule-activities : Updates an existing scheduleActivity.
     *
     * @param scheduleActivityDTO the scheduleActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scheduleActivityDTO,
     * or with status 400 (Bad Request) if the scheduleActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the scheduleActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schedule-activities")
    @Timed
    public ResponseEntity<ScheduleActivityDTO> updateScheduleActivity(@Valid @RequestBody ScheduleActivityDTO scheduleActivityDTO) throws URISyntaxException {
        log.debug("REST request to update ScheduleActivity : {}", scheduleActivityDTO);
        if (scheduleActivityDTO.getId() == null) {
            return createScheduleActivity(scheduleActivityDTO);
        }
        ScheduleActivityDTO result = scheduleActivityService.save(scheduleActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scheduleActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schedule-activities : get all the scheduleActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of scheduleActivities in body
     */
    @GetMapping("/schedule-activities")
    @Timed
    public List<ScheduleActivityDTO> getAllScheduleActivities() {
        log.debug("REST request to get all ScheduleActivities");
        return scheduleActivityService.findAll();
        }

    /**
     * GET  /schedule-activities/:id : get the "id" scheduleActivity.
     *
     * @param id the id of the scheduleActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scheduleActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/schedule-activities/{id}")
    @Timed
    public ResponseEntity<ScheduleActivityDTO> getScheduleActivity(@PathVariable Long id) {
        log.debug("REST request to get ScheduleActivity : {}", id);
        ScheduleActivityDTO scheduleActivityDTO = scheduleActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(scheduleActivityDTO));
    }

    /**
     * DELETE  /schedule-activities/:id : delete the "id" scheduleActivity.
     *
     * @param id the id of the scheduleActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schedule-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteScheduleActivity(@PathVariable Long id) {
        log.debug("REST request to delete ScheduleActivity : {}", id);
        scheduleActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/schedule-activities?query=:query : search for the scheduleActivity corresponding
     * to the query.
     *
     * @param query the query of the scheduleActivity search
     * @return the result of the search
     */
    @GetMapping("/_search/schedule-activities")
    @Timed
    public List<ScheduleActivityDTO> searchScheduleActivities(@RequestParam String query) {
        log.debug("REST request to search ScheduleActivities for query {}", query);
        return scheduleActivityService.search(query);
    }

}
