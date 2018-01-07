package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.AvailabilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Availability and its DTO AvailabilityDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface AvailabilityMapper extends EntityMapper<AvailabilityDTO, Availability> {

    @Mapping(source = "profile.id", target = "profileId")
    AvailabilityDTO toDto(Availability availability); 

    @Mapping(source = "profileId", target = "profile")
    Availability toEntity(AvailabilityDTO availabilityDTO);

    default Availability fromId(Long id) {
        if (id == null) {
            return null;
        }
        Availability availability = new Availability();
        availability.setId(id);
        return availability;
    }
}
