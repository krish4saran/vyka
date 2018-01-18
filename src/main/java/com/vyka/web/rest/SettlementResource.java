package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.SettlementService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.service.dto.SettlementDTO;
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
 * REST controller for managing Settlement.
 */
@RestController
@RequestMapping("/api")
public class SettlementResource {

    private final Logger log = LoggerFactory.getLogger(SettlementResource.class);

    private static final String ENTITY_NAME = "settlement";

    private final SettlementService settlementService;

    public SettlementResource(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    /**
     * POST  /settlements : Create a new settlement.
     *
     * @param settlementDTO the settlementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new settlementDTO, or with status 400 (Bad Request) if the settlement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/settlements")
    @Timed
    public ResponseEntity<SettlementDTO> createSettlement(@Valid @RequestBody SettlementDTO settlementDTO) throws URISyntaxException {
        log.debug("REST request to save Settlement : {}", settlementDTO);
        if (settlementDTO.getId() != null) {
            throw new BadRequestAlertException("A new settlement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SettlementDTO result = settlementService.save(settlementDTO);
        return ResponseEntity.created(new URI("/api/settlements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /settlements : Updates an existing settlement.
     *
     * @param settlementDTO the settlementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated settlementDTO,
     * or with status 400 (Bad Request) if the settlementDTO is not valid,
     * or with status 500 (Internal Server Error) if the settlementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/settlements")
    @Timed
    public ResponseEntity<SettlementDTO> updateSettlement(@Valid @RequestBody SettlementDTO settlementDTO) throws URISyntaxException {
        log.debug("REST request to update Settlement : {}", settlementDTO);
        if (settlementDTO.getId() == null) {
            return createSettlement(settlementDTO);
        }
        SettlementDTO result = settlementService.save(settlementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, settlementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /settlements : get all the settlements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of settlements in body
     */
    @GetMapping("/settlements")
    @Timed
    public List<SettlementDTO> getAllSettlements() {
        log.debug("REST request to get all Settlements");
        return settlementService.findAll();
        }

    /**
     * GET  /settlements/:id : get the "id" settlement.
     *
     * @param id the id of the settlementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the settlementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/settlements/{id}")
    @Timed
    public ResponseEntity<SettlementDTO> getSettlement(@PathVariable Long id) {
        log.debug("REST request to get Settlement : {}", id);
        SettlementDTO settlementDTO = settlementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(settlementDTO));
    }

    /**
     * DELETE  /settlements/:id : delete the "id" settlement.
     *
     * @param id the id of the settlementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/settlements/{id}")
    @Timed
    public ResponseEntity<Void> deleteSettlement(@PathVariable Long id) {
        log.debug("REST request to delete Settlement : {}", id);
        settlementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/settlements?query=:query : search for the settlement corresponding
     * to the query.
     *
     * @param query the query of the settlement search
     * @return the result of the search
     */
    @GetMapping("/_search/settlements")
    @Timed
    public List<SettlementDTO> searchSettlements(@RequestParam String query) {
        log.debug("REST request to search Settlements for query {}", query);
        return settlementService.search(query);
    }

}
