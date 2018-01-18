package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.OrderActivityService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.web.rest.util.PaginationUtil;
import com.vyka.service.dto.OrderActivityDTO;
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
 * REST controller for managing OrderActivity.
 */
@RestController
@RequestMapping("/api")
public class OrderActivityResource {

    private final Logger log = LoggerFactory.getLogger(OrderActivityResource.class);

    private static final String ENTITY_NAME = "orderActivity";

    private final OrderActivityService orderActivityService;

    public OrderActivityResource(OrderActivityService orderActivityService) {
        this.orderActivityService = orderActivityService;
    }

    /**
     * POST  /order-activities : Create a new orderActivity.
     *
     * @param orderActivityDTO the orderActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderActivityDTO, or with status 400 (Bad Request) if the orderActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-activities")
    @Timed
    public ResponseEntity<OrderActivityDTO> createOrderActivity(@Valid @RequestBody OrderActivityDTO orderActivityDTO) throws URISyntaxException {
        log.debug("REST request to save OrderActivity : {}", orderActivityDTO);
        if (orderActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderActivityDTO result = orderActivityService.save(orderActivityDTO);
        return ResponseEntity.created(new URI("/api/order-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-activities : Updates an existing orderActivity.
     *
     * @param orderActivityDTO the orderActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderActivityDTO,
     * or with status 400 (Bad Request) if the orderActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-activities")
    @Timed
    public ResponseEntity<OrderActivityDTO> updateOrderActivity(@Valid @RequestBody OrderActivityDTO orderActivityDTO) throws URISyntaxException {
        log.debug("REST request to update OrderActivity : {}", orderActivityDTO);
        if (orderActivityDTO.getId() == null) {
            return createOrderActivity(orderActivityDTO);
        }
        OrderActivityDTO result = orderActivityService.save(orderActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-activities : get all the orderActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orderActivities in body
     */
    @GetMapping("/order-activities")
    @Timed
    public ResponseEntity<List<OrderActivityDTO>> getAllOrderActivities(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OrderActivities");
        Page<OrderActivityDTO> page = orderActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/order-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /order-activities/:id : get the "id" orderActivity.
     *
     * @param id the id of the orderActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-activities/{id}")
    @Timed
    public ResponseEntity<OrderActivityDTO> getOrderActivity(@PathVariable Long id) {
        log.debug("REST request to get OrderActivity : {}", id);
        OrderActivityDTO orderActivityDTO = orderActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderActivityDTO));
    }

    /**
     * DELETE  /order-activities/:id : delete the "id" orderActivity.
     *
     * @param id the id of the orderActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderActivity(@PathVariable Long id) {
        log.debug("REST request to delete OrderActivity : {}", id);
        orderActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/order-activities?query=:query : search for the orderActivity corresponding
     * to the query.
     *
     * @param query the query of the orderActivity search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/order-activities")
    @Timed
    public ResponseEntity<List<OrderActivityDTO>> searchOrderActivities(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of OrderActivities for query {}", query);
        Page<OrderActivityDTO> page = orderActivityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/order-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
