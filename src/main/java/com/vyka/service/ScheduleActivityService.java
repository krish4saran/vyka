package com.vyka.service;

import com.vyka.service.dto.ScheduleActivityDTO;
import java.util.List;

/**
 * Service Interface for managing ScheduleActivity.
 */
public interface ScheduleActivityService {

    /**
     * Save a scheduleActivity.
     *
     * @param scheduleActivityDTO the entity to save
     * @return the persisted entity
     */
    ScheduleActivityDTO save(ScheduleActivityDTO scheduleActivityDTO);

    /**
     *  Get all the scheduleActivities.
     *
     *  @return the list of entities
     */
    List<ScheduleActivityDTO> findAll();

    /**
     *  Get the "id" scheduleActivity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ScheduleActivityDTO findOne(Long id);

    /**
     *  Delete the "id" scheduleActivity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the scheduleActivity corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ScheduleActivityDTO> search(String query);
}
