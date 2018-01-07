package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.ChaptersService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.service.dto.ChaptersDTO;
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
 * REST controller for managing Chapters.
 */
@RestController
@RequestMapping("/api")
public class ChaptersResource {

    private final Logger log = LoggerFactory.getLogger(ChaptersResource.class);

    private static final String ENTITY_NAME = "chapters";

    private final ChaptersService chaptersService;

    public ChaptersResource(ChaptersService chaptersService) {
        this.chaptersService = chaptersService;
    }

    /**
     * POST  /chapters : Create a new chapters.
     *
     * @param chaptersDTO the chaptersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chaptersDTO, or with status 400 (Bad Request) if the chapters has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chapters")
    @Timed
    public ResponseEntity<ChaptersDTO> createChapters(@Valid @RequestBody ChaptersDTO chaptersDTO) throws URISyntaxException {
        log.debug("REST request to save Chapters : {}", chaptersDTO);
        if (chaptersDTO.getId() != null) {
            throw new BadRequestAlertException("A new chapters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChaptersDTO result = chaptersService.save(chaptersDTO);
        return ResponseEntity.created(new URI("/api/chapters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chapters : Updates an existing chapters.
     *
     * @param chaptersDTO the chaptersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chaptersDTO,
     * or with status 400 (Bad Request) if the chaptersDTO is not valid,
     * or with status 500 (Internal Server Error) if the chaptersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chapters")
    @Timed
    public ResponseEntity<ChaptersDTO> updateChapters(@Valid @RequestBody ChaptersDTO chaptersDTO) throws URISyntaxException {
        log.debug("REST request to update Chapters : {}", chaptersDTO);
        if (chaptersDTO.getId() == null) {
            return createChapters(chaptersDTO);
        }
        ChaptersDTO result = chaptersService.save(chaptersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chaptersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chapters : get all the chapters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chapters in body
     */
    @GetMapping("/chapters")
    @Timed
    public List<ChaptersDTO> getAllChapters() {
        log.debug("REST request to get all Chapters");
        return chaptersService.findAll();
        }

    /**
     * GET  /chapters/:id : get the "id" chapters.
     *
     * @param id the id of the chaptersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chaptersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/chapters/{id}")
    @Timed
    public ResponseEntity<ChaptersDTO> getChapters(@PathVariable Long id) {
        log.debug("REST request to get Chapters : {}", id);
        ChaptersDTO chaptersDTO = chaptersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chaptersDTO));
    }

    /**
     * DELETE  /chapters/:id : delete the "id" chapters.
     *
     * @param id the id of the chaptersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chapters/{id}")
    @Timed
    public ResponseEntity<Void> deleteChapters(@PathVariable Long id) {
        log.debug("REST request to delete Chapters : {}", id);
        chaptersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/chapters?query=:query : search for the chapters corresponding
     * to the query.
     *
     * @param query the query of the chapters search
     * @return the result of the search
     */
    @GetMapping("/_search/chapters")
    @Timed
    public List<ChaptersDTO> searchChapters(@RequestParam String query) {
        log.debug("REST request to search Chapters for query {}", query);
        return chaptersService.search(query);
    }

}
