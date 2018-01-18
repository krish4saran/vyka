package com.vyka.service.impl;

import com.vyka.service.ScheduleService;
import com.vyka.domain.Schedule;
import com.vyka.repository.ScheduleRepository;
import com.vyka.repository.search.ScheduleSearchRepository;
import com.vyka.service.dto.ScheduleDTO;
import com.vyka.service.mapper.ScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Schedule.
 */
@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService{

    private final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final ScheduleSearchRepository scheduleSearchRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper, ScheduleSearchRepository scheduleSearchRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.scheduleSearchRepository = scheduleSearchRepository;
    }

    /**
     * Save a schedule.
     *
     * @param scheduleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScheduleDTO save(ScheduleDTO scheduleDTO) {
        log.debug("Request to save Schedule : {}", scheduleDTO);
        Schedule schedule = scheduleMapper.toEntity(scheduleDTO);
        schedule = scheduleRepository.save(schedule);
        ScheduleDTO result = scheduleMapper.toDto(schedule);
        scheduleSearchRepository.save(schedule);
        return result;
    }

    /**
     *  Get all the schedules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Schedules");
        return scheduleRepository.findAll(pageable)
            .map(scheduleMapper::toDto);
    }

    /**
     *  Get one schedule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ScheduleDTO findOne(Long id) {
        log.debug("Request to get Schedule : {}", id);
        Schedule schedule = scheduleRepository.findOne(id);
        return scheduleMapper.toDto(schedule);
    }

    /**
     *  Delete the  schedule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Schedule : {}", id);
        scheduleRepository.delete(id);
        scheduleSearchRepository.delete(id);
    }

    /**
     * Search for the schedule corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Schedules for query {}", query);
        Page<Schedule> result = scheduleSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(scheduleMapper::toDto);
    }
}
