package com.vyka.service.impl;

import com.vyka.service.PackageOrderService;
import com.vyka.domain.PackageOrder;
import com.vyka.repository.PackageOrderRepository;
import com.vyka.repository.search.PackageOrderSearchRepository;
import com.vyka.service.dto.PackageOrderDTO;
import com.vyka.service.mapper.PackageOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PackageOrder.
 */
@Service
@Transactional
public class PackageOrderServiceImpl implements PackageOrderService{

    private final Logger log = LoggerFactory.getLogger(PackageOrderServiceImpl.class);

    private final PackageOrderRepository packageOrderRepository;

    private final PackageOrderMapper packageOrderMapper;

    private final PackageOrderSearchRepository packageOrderSearchRepository;

    public PackageOrderServiceImpl(PackageOrderRepository packageOrderRepository, PackageOrderMapper packageOrderMapper, PackageOrderSearchRepository packageOrderSearchRepository) {
        this.packageOrderRepository = packageOrderRepository;
        this.packageOrderMapper = packageOrderMapper;
        this.packageOrderSearchRepository = packageOrderSearchRepository;
    }

    /**
     * Save a packageOrder.
     *
     * @param packageOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PackageOrderDTO save(PackageOrderDTO packageOrderDTO) {
        log.debug("Request to save PackageOrder : {}", packageOrderDTO);
        PackageOrder packageOrder = packageOrderMapper.toEntity(packageOrderDTO);
        packageOrder = packageOrderRepository.save(packageOrder);
        PackageOrderDTO result = packageOrderMapper.toDto(packageOrder);
        packageOrderSearchRepository.save(packageOrder);
        return result;
    }

    /**
     *  Get all the packageOrders.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PackageOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PackageOrders");
        return packageOrderRepository.findAll(pageable)
            .map(packageOrderMapper::toDto);
    }

    /**
     *  Get one packageOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PackageOrderDTO findOne(Long id) {
        log.debug("Request to get PackageOrder : {}", id);
        PackageOrder packageOrder = packageOrderRepository.findOne(id);
        return packageOrderMapper.toDto(packageOrder);
    }

    /**
     *  Delete the  packageOrder by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PackageOrder : {}", id);
        packageOrderRepository.delete(id);
        packageOrderSearchRepository.delete(id);
    }

    /**
     * Search for the packageOrder corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PackageOrderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PackageOrders for query {}", query);
        Page<PackageOrder> result = packageOrderSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(packageOrderMapper::toDto);
    }
}
