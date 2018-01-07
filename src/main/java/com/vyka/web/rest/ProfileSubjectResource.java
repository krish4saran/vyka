package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.ProfileSubjectService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.web.rest.util.PaginationUtil;
import com.vyka.service.dto.ProfileSubjectDTO;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProfileSubject.
 */
@RestController
@RequestMapping("/api")
public class ProfileSubjectResource {

    private final Logger log = LoggerFactory.getLogger(ProfileSubjectResource.class);

    private static final String ENTITY_NAME = "profileSubject";

    private final ProfileSubjectService profileSubjectService;

    public ProfileSubjectResource(ProfileSubjectService profileSubjectService) {
        this.profileSubjectService = profileSubjectService;
    }

    /**
     * POST  /profile-subjects : Create a new profileSubject.
     *
     * @param profileSubjectDTO the profileSubjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profileSubjectDTO, or with status 400 (Bad Request) if the profileSubject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profile-subjects")
    @Timed
    public ResponseEntity<ProfileSubjectDTO> createProfileSubject(@RequestBody ProfileSubjectDTO profileSubjectDTO) throws URISyntaxException {
        log.debug("REST request to save ProfileSubject : {}", profileSubjectDTO);
        if (profileSubjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new profileSubject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileSubjectDTO result = profileSubjectService.save(profileSubjectDTO);
        return ResponseEntity.created(new URI("/api/profile-subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profile-subjects : Updates an existing profileSubject.
     *
     * @param profileSubjectDTO the profileSubjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profileSubjectDTO,
     * or with status 400 (Bad Request) if the profileSubjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the profileSubjectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profile-subjects")
    @Timed
    public ResponseEntity<ProfileSubjectDTO> updateProfileSubject(@RequestBody ProfileSubjectDTO profileSubjectDTO) throws URISyntaxException {
        log.debug("REST request to update ProfileSubject : {}", profileSubjectDTO);
        if (profileSubjectDTO.getId() == null) {
            return createProfileSubject(profileSubjectDTO);
        }
        ProfileSubjectDTO result = profileSubjectService.save(profileSubjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profileSubjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profile-subjects : get all the profileSubjects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of profileSubjects in body
     */
    @GetMapping("/profile-subjects")
    @Timed
    public ResponseEntity<List<ProfileSubjectDTO>> getAllProfileSubjects(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProfileSubjects");
        Page<ProfileSubjectDTO> page = profileSubjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profile-subjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /profile-subjects/:id : get the "id" profileSubject.
     *
     * @param id the id of the profileSubjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profileSubjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profile-subjects/{id}")
    @Timed
    public ResponseEntity<ProfileSubjectDTO> getProfileSubject(@PathVariable Long id) {
        log.debug("REST request to get ProfileSubject : {}", id);
        ProfileSubjectDTO profileSubjectDTO = profileSubjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profileSubjectDTO));
    }

    /**
     * DELETE  /profile-subjects/:id : delete the "id" profileSubject.
     *
     * @param id the id of the profileSubjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profile-subjects/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfileSubject(@PathVariable Long id) {
        log.debug("REST request to delete ProfileSubject : {}", id);
        profileSubjectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/profile-subjects?query=:query : search for the profileSubject corresponding
     * to the query.
     *
     * @param query the query of the profileSubject search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/profile-subjects")
    @Timed
    public ResponseEntity<List<ProfileSubjectDTO>> searchProfileSubjects(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ProfileSubjects for query {}", query);
        Page<ProfileSubjectDTO> page = profileSubjectService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/profile-subjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
