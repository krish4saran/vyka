package com.vyka.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vyka.service.PackageOrderService;
import com.vyka.web.rest.errors.BadRequestAlertException;
import com.vyka.web.rest.util.HeaderUtil;
import com.vyka.web.rest.util.PaginationUtil;
import com.vyka.service.dto.PackageOrderDTO;
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
 * REST controller for managing PackageOrder.
 */
@RestController
@RequestMapping("/api")
public class PackageOrderResource {

    private final Logger log = LoggerFactory.getLogger(PackageOrderResource.class);

    private static final String ENTITY_NAME = "packageOrder";

    private final PackageOrderService packageOrderService;

    public PackageOrderResource(PackageOrderService packageOrderService) {
        this.packageOrderService = packageOrderService;
    }

    /**
     * POST  /package-orders : Create a new packageOrder.
     *
     * @param packageOrderDTO the packageOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new packageOrderDTO, or with status 400 (Bad Request) if the packageOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/package-orders")
    @Timed
    public ResponseEntity<PackageOrderDTO> createPackageOrder(@Valid @RequestBody PackageOrderDTO packageOrderDTO) throws URISyntaxException {
        log.debug("REST request to save PackageOrder : {}", packageOrderDTO);
        if (packageOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new packageOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackageOrderDTO result = packageOrderService.save(packageOrderDTO);
        return ResponseEntity.created(new URI("/api/package-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /package-orders : Updates an existing packageOrder.
     *
     * @param packageOrderDTO the packageOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated packageOrderDTO,
     * or with status 400 (Bad Request) if the packageOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the packageOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/package-orders")
    @Timed
    public ResponseEntity<PackageOrderDTO> updatePackageOrder(@Valid @RequestBody PackageOrderDTO packageOrderDTO) throws URISyntaxException {
        log.debug("REST request to update PackageOrder : {}", packageOrderDTO);
        if (packageOrderDTO.getId() == null) {
            return createPackageOrder(packageOrderDTO);
        }
        PackageOrderDTO result = packageOrderService.save(packageOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, packageOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /package-orders : get all the packageOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of packageOrders in body
     */
    @GetMapping("/package-orders")
    @Timed
    public ResponseEntity<List<PackageOrderDTO>> getAllPackageOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PackageOrders");
        Page<PackageOrderDTO> page = packageOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/package-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /package-orders/:id : get the "id" packageOrder.
     *
     * @param id the id of the packageOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the packageOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/package-orders/{id}")
    @Timed
    public ResponseEntity<PackageOrderDTO> getPackageOrder(@PathVariable Long id) {
        log.debug("REST request to get PackageOrder : {}", id);
        PackageOrderDTO packageOrderDTO = packageOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(packageOrderDTO));
    }

    /**
     * DELETE  /package-orders/:id : delete the "id" packageOrder.
     *
     * @param id the id of the packageOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/package-orders/{id}")
    @Timed
    public ResponseEntity<Void> deletePackageOrder(@PathVariable Long id) {
        log.debug("REST request to delete PackageOrder : {}", id);
        packageOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/package-orders?query=:query : search for the packageOrder corresponding
     * to the query.
     *
     * @param query the query of the packageOrder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/package-orders")
    @Timed
    public ResponseEntity<List<PackageOrderDTO>> searchPackageOrders(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of PackageOrders for query {}", query);
        Page<PackageOrderDTO> page = packageOrderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/package-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
