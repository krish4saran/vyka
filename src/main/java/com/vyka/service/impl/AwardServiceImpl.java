package com.vyka.service.impl;

import com.vyka.service.AwardService;
import com.vyka.domain.Award;
import com.vyka.repository.AwardRepository;
import com.vyka.repository.search.AwardSearchRepository;
import com.vyka.service.dto.AwardDTO;
import com.vyka.service.mapper.AwardMapper;
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
 * Service Implementation for managing Award.
 */
@Service
@Transactional
public class AwardServiceImpl implements AwardService{

    private final Logger log = LoggerFactory.getLogger(AwardServiceImpl.class);

    private final AwardRepository awardRepository;

    private final AwardMapper awardMapper;

    private final AwardSearchRepository awardSearchRepository;

    public AwardServiceImpl(AwardRepository awardRepository, AwardMapper awardMapper, AwardSearchRepository awardSearchRepository) {
        this.awardRepository = awardRepository;
        this.awardMapper = awardMapper;
        this.awardSearchRepository = awardSearchRepository;
    }

    /**
     * Save a award.
     *
     * @param awardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AwardDTO save(AwardDTO awardDTO) {
        log.debug("Request to save Award : {}", awardDTO);
        Award award = awardMapper.toEntity(awardDTO);
        award = awardRepository.save(award);
        AwardDTO result = awardMapper.toDto(award);
        awardSearchRepository.save(award);
        return result;
    }

    /**
     *  Get all the awards.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AwardDTO> findAll() {
        log.debug("Request to get all Awards");
        return awardRepository.findAll().stream()
            .map(awardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one award by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AwardDTO findOne(Long id) {
        log.debug("Request to get Award : {}", id);
        Award award = awardRepository.findOne(id);
        return awardMapper.toDto(award);
    }

    /**
     *  Delete the  award by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Award : {}", id);
        awardRepository.delete(id);
        awardSearchRepository.delete(id);
    }

    /**
     * Search for the award corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AwardDTO> search(String query) {
        log.debug("Request to search Awards for query {}", query);
        return StreamSupport
            .stream(awardSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(awardMapper::toDto)
            .collect(Collectors.toList());
    }
}
