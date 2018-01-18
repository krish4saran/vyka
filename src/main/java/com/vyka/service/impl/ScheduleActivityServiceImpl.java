package com.vyka.service.impl;

import com.vyka.service.ScheduleActivityService;
import com.vyka.domain.ScheduleActivity;
import com.vyka.repository.ScheduleActivityRepository;
import com.vyka.repository.search.ScheduleActivitySearchRepository;
import com.vyka.service.dto.ScheduleActivityDTO;
import com.vyka.service.mapper.ScheduleActivityMapper;
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
 * Service Implementation for managing ScheduleActivity.
 */
@Service
@Transactional
public class ScheduleActivityServiceImpl implements ScheduleActivityService{

    private final Logger log = LoggerFactory.getLogger(ScheduleActivityServiceImpl.class);

    private final ScheduleActivityRepository scheduleActivityRepository;

    private final ScheduleActivityMapper scheduleActivityMapper;

    private final ScheduleActivitySearchRepository scheduleActivitySearchRepository;

    public ScheduleActivityServiceImpl(ScheduleActivityRepository scheduleActivityRepository, ScheduleActivityMapper scheduleActivityMapper, ScheduleActivitySearchRepository scheduleActivitySearchRepository) {
        this.scheduleActivityRepository = scheduleActivityRepository;
        this.scheduleActivityMapper = scheduleActivityMapper;
        this.scheduleActivitySearchRepository = scheduleActivitySearchRepository;
    }

    /**
     * Save a scheduleActivity.
     *
     * @param scheduleActivityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScheduleActivityDTO save(ScheduleActivityDTO scheduleActivityDTO) {
        log.debug("Request to save ScheduleActivity : {}", scheduleActivityDTO);
        ScheduleActivity scheduleActivity = scheduleActivityMapper.toEntity(scheduleActivityDTO);
        scheduleActivity = scheduleActivityRepository.save(scheduleActivity);
        ScheduleActivityDTO result = scheduleActivityMapper.toDto(scheduleActivity);
        scheduleActivitySearchRepository.save(scheduleActivity);
        return result;
    }

    /**
     *  Get all the scheduleActivities.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleActivityDTO> findAll() {
        log.debug("Request to get all ScheduleActivities");
        return scheduleActivityRepository.findAll().stream()
            .map(scheduleActivityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one scheduleActivity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ScheduleActivityDTO findOne(Long id) {
        log.debug("Request to get ScheduleActivity : {}", id);
        ScheduleActivity scheduleActivity = scheduleActivityRepository.findOne(id);
        return scheduleActivityMapper.toDto(scheduleActivity);
    }

    /**
     *  Delete the  scheduleActivity by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduleActivity : {}", id);
        scheduleActivityRepository.delete(id);
        scheduleActivitySearchRepository.delete(id);
    }

    /**
     * Search for the scheduleActivity corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleActivityDTO> search(String query) {
        log.debug("Request to search ScheduleActivities for query {}", query);
        return StreamSupport
            .stream(scheduleActivitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(scheduleActivityMapper::toDto)
            .collect(Collectors.toList());
    }
}
