package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.ScheduleActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ScheduleActivity and its DTO ScheduleActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {ScheduleMapper.class, OrderActivityMapper.class})
public interface ScheduleActivityMapper extends EntityMapper<ScheduleActivityDTO, ScheduleActivity> {

    @Mapping(source = "schedule.id", target = "scheduleId")
    @Mapping(source = "orderActivity.id", target = "orderActivityId")
    ScheduleActivityDTO toDto(ScheduleActivity scheduleActivity); 

    @Mapping(source = "scheduleId", target = "schedule")
    @Mapping(source = "orderActivityId", target = "orderActivity")
    ScheduleActivity toEntity(ScheduleActivityDTO scheduleActivityDTO);

    default ScheduleActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScheduleActivity scheduleActivity = new ScheduleActivity();
        scheduleActivity.setId(id);
        return scheduleActivity;
    }
}
