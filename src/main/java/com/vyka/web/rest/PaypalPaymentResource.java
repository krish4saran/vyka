package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.PaypalPaymentService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.service.dto.PaypalPaymentDTO;
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
 * REST controller for managing PaypalPayment.
 */
@RestController
@RequestMapping("/api")
public class PaypalPaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaypalPaymentResource.class);

    private static final String ENTITY_NAME = "paypalPayment";

    private final PaypalPaymentService paypalPaymentService;

    public PaypalPaymentResource(PaypalPaymentService paypalPaymentService) {
        this.paypalPaymentService = paypalPaymentService;
    }

    /**
     * POST  /paypal-payments : Create a new paypalPayment.
     *
     * @param paypalPaymentDTO the paypalPaymentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paypalPaymentDTO, or with status 400 (Bad Request) if the paypalPayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/paypal-payments")
    @Timed
    public ResponseEntity<PaypalPaymentDTO> createPaypalPayment(@Valid @RequestBody PaypalPaymentDTO paypalPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save PaypalPayment : {}", paypalPaymentDTO);
        if (paypalPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new paypalPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaypalPaymentDTO result = paypalPaymentService.save(paypalPaymentDTO);
        return ResponseEntity.created(new URI("/api/paypal-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paypal-payments : Updates an existing paypalPayment.
     *
     * @param paypalPaymentDTO the paypalPaymentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paypalPaymentDTO,
     * or with status 400 (Bad Request) if the paypalPaymentDTO is not valid,
     * or with status 500 (Internal Server Error) if the paypalPaymentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/paypal-payments")
    @Timed
    public ResponseEntity<PaypalPaymentDTO> updatePaypalPayment(@Valid @RequestBody PaypalPaymentDTO paypalPaymentDTO) throws URISyntaxException {
        log.debug("REST request to update PaypalPayment : {}", paypalPaymentDTO);
        if (paypalPaymentDTO.getId() == null) {
            return createPaypalPayment(paypalPaymentDTO);
        }
        PaypalPaymentDTO result = paypalPaymentService.save(paypalPaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paypalPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paypal-payments : get all the paypalPayments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paypalPayments in body
     */
    @GetMapping("/paypal-payments")
    @Timed
    public List<PaypalPaymentDTO> getAllPaypalPayments() {
        log.debug("REST request to get all PaypalPayments");
        return paypalPaymentService.findAll();
        }

    /**
     * GET  /paypal-payments/:id : get the "id" paypalPayment.
     *
     * @param id the id of the paypalPaymentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paypalPaymentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/paypal-payments/{id}")
    @Timed
    public ResponseEntity<PaypalPaymentDTO> getPaypalPayment(@PathVariable Long id) {
        log.debug("REST request to get PaypalPayment : {}", id);
        PaypalPaymentDTO paypalPaymentDTO = paypalPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paypalPaymentDTO));
    }

    /**
     * DELETE  /paypal-payments/:id : delete the "id" paypalPayment.
     *
     * @param id the id of the paypalPaymentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/paypal-payments/{id}")
    @Timed
    public ResponseEntity<Void> deletePaypalPayment(@PathVariable Long id) {
        log.debug("REST request to delete PaypalPayment : {}", id);
        paypalPaymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/paypal-payments?query=:query : search for the paypalPayment corresponding
     * to the query.
     *
     * @param query the query of the paypalPayment search
     * @return the result of the search
     */
    @GetMapping("/_search/paypal-payments")
    @Timed
    public List<PaypalPaymentDTO> searchPaypalPayments(@RequestParam String query) {
        log.debug("REST request to search PaypalPayments for query {}", query);
        return paypalPaymentService.search(query);
    }

}
