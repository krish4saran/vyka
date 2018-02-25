package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.ProfileService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.web.rest.util.PaginationUtil;
import com.vyka.service.dto.ProfileDTO;
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
 * REST controller for managing Profile.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    private static final String ENTITY_NAME = "profile";

    private final ProfileService profileService;

    public ProfileResource(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * POST  /profiles : Create a new profile.
     *
     * @param profileDTO the profileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profileDTO, or with status 400 (Bad Request) if the profile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profiles")
    @Timed
    public ResponseEntity<ProfileDTO> createProfile(@Valid @RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to save Profile : {}", profileDTO);
        if (profileDTO.getId() != null) {
            throw new BadRequestAlertException("A new profile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profiles : Updates an existing profile.
     *
     * @param profileDTO the profileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profileDTO,
     * or with status 400 (Bad Request) if the profileDTO is not valid,
     * or with status 500 (Internal Server Error) if the profileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profiles")
    @Timed
    public ResponseEntity<ProfileDTO> updateProfile(@Valid @RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to update Profile : {}", profileDTO);
        if (profileDTO.getId() == null) {
            return createProfile(profileDTO);
        }
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profiles : get all the profiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of profiles in body
     */
    @GetMapping("/profiles")
    @Timed
    public ResponseEntity<List<ProfileDTO>> getAllProfiles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Profiles");
        Page<ProfileDTO> page = profileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /profiles/:id : get the "id" profile.
     *
     * @param id the id of the profileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profiles/{id}")
    @Timed
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) {
        log.debug("REST request to get Profile : {}", id);
        ProfileDTO profileDTO = profileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profileDTO));
    }
    
    /**
     * GET  /profiles/Users/:userId : get the "userid" profile.
     *
     * @param userId the userId of the profile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profiles/users/{userId}")
    @Timed
    public ResponseEntity<ProfileDTO> getUserProfile(@PathVariable Long userId) {
        log.debug("REST request to get Profile : {}", userId);
        ProfileDTO profileDTO = profileService.findProfileByUserId(userId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profileDTO));
    }


    /**
     * DELETE  /profiles/:id : delete the "id" profile.
     *
     * @param id the id of the profileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.debug("REST request to delete Profile : {}", id);
        profileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/profiles?query=:query : search for the profile corresponding
     * to the query.
     *
     * @param query the query of the profile search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/profiles")
    @Timed
    public ResponseEntity<List<ProfileDTO>> searchProfiles(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Profiles for query {}", query);
        Page<ProfileDTO> page = profileService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
