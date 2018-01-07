package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.LevelService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.service.dto.LevelDTO;
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
 * REST controller for managing Level.
 */
@RestController
@RequestMapping("/api")
public class LevelResource {

    private final Logger log = LoggerFactory.getLogger(LevelResource.class);

    private static final String ENTITY_NAME = "level";

    private final LevelService levelService;

    public LevelResource(LevelService levelService) {
        this.levelService = levelService;
    }

    /**
     * POST  /levels : Create a new level.
     *
     * @param levelDTO the levelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new levelDTO, or with status 400 (Bad Request) if the level has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/levels")
    @Timed
    public ResponseEntity<LevelDTO> createLevel(@Valid @RequestBody LevelDTO levelDTO) throws URISyntaxException {
        log.debug("REST request to save Level : {}", levelDTO);
        if (levelDTO.getId() != null) {
            throw new BadRequestAlertException("A new level cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelDTO result = levelService.save(levelDTO);
        return ResponseEntity.created(new URI("/api/levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /levels : Updates an existing level.
     *
     * @param levelDTO the levelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated levelDTO,
     * or with status 400 (Bad Request) if the levelDTO is not valid,
     * or with status 500 (Internal Server Error) if the levelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/levels")
    @Timed
    public ResponseEntity<LevelDTO> updateLevel(@Valid @RequestBody LevelDTO levelDTO) throws URISyntaxException {
        log.debug("REST request to update Level : {}", levelDTO);
        if (levelDTO.getId() == null) {
            return createLevel(levelDTO);
        }
        LevelDTO result = levelService.save(levelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, levelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /levels : get all the levels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of levels in body
     */
    @GetMapping("/levels")
    @Timed
    public List<LevelDTO> getAllLevels() {
        log.debug("REST request to get all Levels");
        return levelService.findAll();
        }

    /**
     * GET  /levels/:id : get the "id" level.
     *
     * @param id the id of the levelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the levelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/levels/{id}")
    @Timed
    public ResponseEntity<LevelDTO> getLevel(@PathVariable Long id) {
        log.debug("REST request to get Level : {}", id);
        LevelDTO levelDTO = levelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(levelDTO));
    }

    /**
     * DELETE  /levels/:id : delete the "id" level.
     *
     * @param id the id of the levelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteLevel(@PathVariable Long id) {
        log.debug("REST request to delete Level : {}", id);
        levelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/levels?query=:query : search for the level corresponding
     * to the query.
     *
     * @param query the query of the level search
     * @return the result of the search
     */
    @GetMapping("/_search/levels")
    @Timed
    public List<LevelDTO> searchLevels(@RequestParam String query) {
        log.debug("REST request to search Levels for query {}", query);
        return levelService.search(query);
    }

}
