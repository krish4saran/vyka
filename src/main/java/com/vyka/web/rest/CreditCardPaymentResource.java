package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.CreditCardPaymentService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.service.dto.CreditCardPaymentDTO;
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
 * REST controller for managing CreditCardPayment.
 */
@RestController
@RequestMapping("/api")
public class CreditCardPaymentResource {

    private final Logger log = LoggerFactory.getLogger(CreditCardPaymentResource.class);

    private static final String ENTITY_NAME = "creditCardPayment";

    private final CreditCardPaymentService creditCardPaymentService;

    public CreditCardPaymentResource(CreditCardPaymentService creditCardPaymentService) {
        this.creditCardPaymentService = creditCardPaymentService;
    }

    /**
     * POST  /credit-card-payments : Create a new creditCardPayment.
     *
     * @param creditCardPaymentDTO the creditCardPaymentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditCardPaymentDTO, or with status 400 (Bad Request) if the creditCardPayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/credit-card-payments")
    @Timed
    public ResponseEntity<CreditCardPaymentDTO> createCreditCardPayment(@Valid @RequestBody CreditCardPaymentDTO creditCardPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save CreditCardPayment : {}", creditCardPaymentDTO);
        if (creditCardPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditCardPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditCardPaymentDTO result = creditCardPaymentService.save(creditCardPaymentDTO);
        return ResponseEntity.created(new URI("/api/credit-card-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /credit-card-payments : Updates an existing creditCardPayment.
     *
     * @param creditCardPaymentDTO the creditCardPaymentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditCardPaymentDTO,
     * or with status 400 (Bad Request) if the creditCardPaymentDTO is not valid,
     * or with status 500 (Internal Server Error) if the creditCardPaymentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/credit-card-payments")
    @Timed
    public ResponseEntity<CreditCardPaymentDTO> updateCreditCardPayment(@Valid @RequestBody CreditCardPaymentDTO creditCardPaymentDTO) throws URISyntaxException {
        log.debug("REST request to update CreditCardPayment : {}", creditCardPaymentDTO);
        if (creditCardPaymentDTO.getId() == null) {
            return createCreditCardPayment(creditCardPaymentDTO);
        }
        CreditCardPaymentDTO result = creditCardPaymentService.save(creditCardPaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creditCardPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /credit-card-payments : get all the creditCardPayments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of creditCardPayments in body
     */
    @GetMapping("/credit-card-payments")
    @Timed
    public List<CreditCardPaymentDTO> getAllCreditCardPayments() {
        log.debug("REST request to get all CreditCardPayments");
        return creditCardPaymentService.findAll();
        }

    /**
     * GET  /credit-card-payments/:id : get the "id" creditCardPayment.
     *
     * @param id the id of the creditCardPaymentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditCardPaymentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/credit-card-payments/{id}")
    @Timed
    public ResponseEntity<CreditCardPaymentDTO> getCreditCardPayment(@PathVariable Long id) {
        log.debug("REST request to get CreditCardPayment : {}", id);
        CreditCardPaymentDTO creditCardPaymentDTO = creditCardPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(creditCardPaymentDTO));
    }

    /**
     * DELETE  /credit-card-payments/:id : delete the "id" creditCardPayment.
     *
     * @param id the id of the creditCardPaymentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/credit-card-payments/{id}")
    @Timed
    public ResponseEntity<Void> deleteCreditCardPayment(@PathVariable Long id) {
        log.debug("REST request to delete CreditCardPayment : {}", id);
        creditCardPaymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/credit-card-payments?query=:query : search for the creditCardPayment corresponding
     * to the query.
     *
     * @param query the query of the creditCardPayment search
     * @return the result of the search
     */
    @GetMapping("/_search/credit-card-payments")
    @Timed
    public List<CreditCardPaymentDTO> searchCreditCardPayments(@RequestParam String query) {
        log.debug("REST request to search CreditCardPayments for query {}", query);
        return creditCardPaymentService.search(query);
    }

}
