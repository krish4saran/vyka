package com.vyka.service.impl;

import com.vyka.service.AvailabilityService;
import com.vyka.domain.Availability;
import com.vyka.repository.AvailabilityRepository;
import com.vyka.repository.search.AvailabilitySearchRepository;
import com.vyka.service.dto.AvailabilityDTO;
import com.vyka.service.mapper.AvailabilityMapper;
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
 * Service Implementation for managing Availability.
 */
@Service
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService{

    private final Logger log = LoggerFactory.getLogger(AvailabilityServiceImpl.class);

    private final AvailabilityRepository availabilityRepository;

    private final AvailabilityMapper availabilityMapper;

    private final AvailabilitySearchRepository availabilitySearchRepository;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, AvailabilityMapper availabilityMapper, AvailabilitySearchRepository availabilitySearchRepository) {
        this.availabilityRepository = availabilityRepository;
        this.availabilityMapper = availabilityMapper;
        this.availabilitySearchRepository = availabilitySearchRepository;
    }

    /**
     * Save a availability.
     *
     * @param availabilityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AvailabilityDTO save(AvailabilityDTO availabilityDTO) {
        log.debug("Request to save Availability : {}", availabilityDTO);
        Availability availability = availabilityMapper.toEntity(availabilityDTO);
        availability = availabilityRepository.save(availability);
        AvailabilityDTO result = availabilityMapper.toDto(availability);
        availabilitySearchRepository.save(availability);
        return result;
    }

    /**
     *  Get all the availabilities.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AvailabilityDTO> findAll() {
        log.debug("Request to get all Availabilities");
        return availabilityRepository.findAll().stream()
            .map(availabilityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one availability by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AvailabilityDTO findOne(Long id) {
        log.debug("Request to get Availability : {}", id);
        Availability availability = availabilityRepository.findOne(id);
        return availabilityMapper.toDto(availability);
    }

    /**
     *  Delete the  availability by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Availability : {}", id);
        availabilityRepository.delete(id);
        availabilitySearchRepository.delete(id);
    }

    /**
     * Search for the availability corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AvailabilityDTO> search(String query) {
        log.debug("Request to search Availabilities for query {}", query);
        return StreamSupport
            .stream(availabilitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(availabilityMapper::toDto)
            .collect(Collectors.toList());
    }
}
