package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.SubjectLevelService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.service.dto.SubjectLevelDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SubjectLevel.
 */
@RestController
@RequestMapping("/api")
public class SubjectLevelResource {

    private final Logger log = LoggerFactory.getLogger(SubjectLevelResource.class);

    private static final String ENTITY_NAME = "subjectLevel";

    private final SubjectLevelService subjectLevelService;

    public SubjectLevelResource(SubjectLevelService subjectLevelService) {
        this.subjectLevelService = subjectLevelService;
    }

    /**
     * POST  /subject-levels : Create a new subjectLevel.
     *
     * @param subjectLevelDTO the subjectLevelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subjectLevelDTO, or with status 400 (Bad Request) if the subjectLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subject-levels")
    @Timed
    public ResponseEntity<SubjectLevelDTO> createSubjectLevel(@RequestBody SubjectLevelDTO subjectLevelDTO) throws URISyntaxException {
        log.debug("REST request to save SubjectLevel : {}", subjectLevelDTO);
        if (subjectLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new subjectLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubjectLevelDTO result = subjectLevelService.save(subjectLevelDTO);
        return ResponseEntity.created(new URI("/api/subject-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subject-levels : Updates an existing subjectLevel.
     *
     * @param subjectLevelDTO the subjectLevelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subjectLevelDTO,
     * or with status 400 (Bad Request) if the subjectLevelDTO is not valid,
     * or with status 500 (Internal Server Error) if the subjectLevelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subject-levels")
    @Timed
    public ResponseEntity<SubjectLevelDTO> updateSubjectLevel(@RequestBody SubjectLevelDTO subjectLevelDTO) throws URISyntaxException {
        log.debug("REST request to update SubjectLevel : {}", subjectLevelDTO);
        if (subjectLevelDTO.getId() == null) {
            return createSubjectLevel(subjectLevelDTO);
        }
        SubjectLevelDTO result = subjectLevelService.save(subjectLevelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subjectLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subject-levels : get all the subjectLevels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subjectLevels in body
     */
    @GetMapping("/subject-levels")
    @Timed
    public List<SubjectLevelDTO> getAllSubjectLevels() {
        log.debug("REST request to get all SubjectLevels");
        return subjectLevelService.findAll();
        }

    /**
     * GET  /subject-levels/:id : get the "id" subjectLevel.
     *
     * @param id the id of the subjectLevelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subjectLevelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/subject-levels/{id}")
    @Timed
    public ResponseEntity<SubjectLevelDTO> getSubjectLevel(@PathVariable Long id) {
        log.debug("REST request to get SubjectLevel : {}", id);
        SubjectLevelDTO subjectLevelDTO = subjectLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subjectLevelDTO));
    }

    /**
     * DELETE  /subject-levels/:id : delete the "id" subjectLevel.
     *
     * @param id the id of the subjectLevelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subject-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubjectLevel(@PathVariable Long id) {
        log.debug("REST request to delete SubjectLevel : {}", id);
        subjectLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/subject-levels?query=:query : search for the subjectLevel corresponding
     * to the query.
     *
     * @param query the query of the subjectLevel search
     * @return the result of the search
     */
    @GetMapping("/_search/subject-levels")
    @Timed
    public List<SubjectLevelDTO> searchSubjectLevels(@RequestParam String query) {
        log.debug("REST request to search SubjectLevels for query {}", query);
        return subjectLevelService.search(query);
    }

}
