package com.vyka.service.impl;

import com.vyka.service.SettlementService;
import com.vyka.domain.Settlement;
import com.vyka.repository.SettlementRepository;
import com.vyka.repository.search.SettlementSearchRepository;
import com.vyka.service.dto.SettlementDTO;
import com.vyka.service.mapper.SettlementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Settlement.
 */
@Service
@Transactional
public class SettlementServiceImpl implements SettlementService{

    private final Logger log = LoggerFactory.getLogger(SettlementServiceImpl.class);

    private final SettlementRepository settlementRepository;

    private final SettlementMapper settlementMapper;

    private final SettlementSearchRepository settlementSearchRepository;

    public SettlementServiceImpl(SettlementRepository settlementRepository, SettlementMapper settlementMapper, SettlementSearchRepository settlementSearchRepository) {
        this.settlementRepository = settlementRepository;
        this.settlementMapper = settlementMapper;
        this.settlementSearchRepository = settlementSearchRepository;
    }

    /**
     * Save a settlement.
     *
     * @param settlementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SettlementDTO save(SettlementDTO settlementDTO) {
        log.debug("Request to save Settlement : {}", settlementDTO);
        Settlement settlement = settlementMapper.toEntity(settlementDTO);
        settlement = settlementRepository.save(settlement);
        SettlementDTO result = settlementMapper.toDto(settlement);
        settlementSearchRepository.save(settlement);
        return result;
    }

    /**
     *  Get all the settlements.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SettlementDTO> findAll() {
        log.debug("Request to get all Settlements");
        return settlementRepository.findAll().stream()
            .map(settlementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one settlement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SettlementDTO findOne(Long id) {
        log.debug("Request to get Settlement : {}", id);
        Settlement settlement = settlementRepository.findOne(id);
        return settlementMapper.toDto(settlement);
    }

    /**
     *  Delete the  settlement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Settlement : {}", id);
        settlementRepository.delete(id);
        settlementSearchRepository.delete(id);
    }

    /**
     * Search for the settlement corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SettlementDTO> search(String query) {
        log.debug("Request to search Settlements for query {}", query);
        return StreamSupport
            .stream(settlementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(settlementMapper::toDto)
            .collect(Collectors.toList());
    }
}
