package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.ScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Schedule and its DTO ScheduleDTO.
 */
@Mapper(componentModel = "spring", uses = {PackageOrderMapper.class})
public interface ScheduleMapper extends EntityMapper<ScheduleDTO, Schedule> {

    @Mapping(source = "packageOrder.id", target = "packageOrderId")
    ScheduleDTO toDto(Schedule schedule); 

    @Mapping(source = "packageOrderId", target = "packageOrder")
    @Mapping(target = "scheduleActivities", ignore = true)
    Schedule toEntity(ScheduleDTO scheduleDTO);

    default Schedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Schedule schedule = new Schedule();
        schedule.setId(id);
        return schedule;
    }
}
