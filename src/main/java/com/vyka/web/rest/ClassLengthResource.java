package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.ClassLengthService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.service.dto.ClassLengthDTO;
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
 * REST controller for managing ClassLength.
 */
@RestController
@RequestMapping("/api")
public class ClassLengthResource {

    private final Logger log = LoggerFactory.getLogger(ClassLengthResource.class);

    private static final String ENTITY_NAME = "classLength";

    private final ClassLengthService classLengthService;

    public ClassLengthResource(ClassLengthService classLengthService) {
        this.classLengthService = classLengthService;
    }

    /**
     * POST  /class-lengths : Create a new classLength.
     *
     * @param classLengthDTO the classLengthDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classLengthDTO, or with status 400 (Bad Request) if the classLength has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-lengths")
    @Timed
    public ResponseEntity<ClassLengthDTO> createClassLength(@Valid @RequestBody ClassLengthDTO classLengthDTO) throws URISyntaxException {
        log.debug("REST request to save ClassLength : {}", classLengthDTO);
        if (classLengthDTO.getId() != null) {
            throw new BadRequestAlertException("A new classLength cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassLengthDTO result = classLengthService.save(classLengthDTO);
        return ResponseEntity.created(new URI("/api/class-lengths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-lengths : Updates an existing classLength.
     *
     * @param classLengthDTO the classLengthDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classLengthDTO,
     * or with status 400 (Bad Request) if the classLengthDTO is not valid,
     * or with status 500 (Internal Server Error) if the classLengthDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-lengths")
    @Timed
    public ResponseEntity<ClassLengthDTO> updateClassLength(@Valid @RequestBody ClassLengthDTO classLengthDTO) throws URISyntaxException {
        log.debug("REST request to update ClassLength : {}", classLengthDTO);
        if (classLengthDTO.getId() == null) {
            return createClassLength(classLengthDTO);
        }
        ClassLengthDTO result = classLengthService.save(classLengthDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classLengthDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-lengths : get all the classLengths.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of classLengths in body
     */
    @GetMapping("/class-lengths")
    @Timed
    public List<ClassLengthDTO> getAllClassLengths() {
        log.debug("REST request to get all ClassLengths");
        return classLengthService.findAll();
        }

    /**
     * GET  /class-lengths/:id : get the "id" classLength.
     *
     * @param id the id of the classLengthDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classLengthDTO, or with status 404 (Not Found)
     */
    @GetMapping("/class-lengths/{id}")
    @Timed
    public ResponseEntity<ClassLengthDTO> getClassLength(@PathVariable Long id) {
        log.debug("REST request to get ClassLength : {}", id);
        ClassLengthDTO classLengthDTO = classLengthService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classLengthDTO));
    }

    /**
     * DELETE  /class-lengths/:id : delete the "id" classLength.
     *
     * @param id the id of the classLengthDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-lengths/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassLength(@PathVariable Long id) {
        log.debug("REST request to delete ClassLength : {}", id);
        classLengthService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/class-lengths?query=:query : search for the classLength corresponding
     * to the query.
     *
     * @param query the query of the classLength search
     * @return the result of the search
     */
    @GetMapping("/_search/class-lengths")
    @Timed
    public List<ClassLengthDTO> searchClassLengths(@RequestParam String query) {
        log.debug("REST request to search ClassLengths for query {}", query);
        return classLengthService.search(query);
    }

}
