package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.RateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rate and its DTO RateDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileSubjectMapper.class, ClassLengthMapper.class})
public interface RateMapper extends EntityMapper<RateDTO, Rate> {

    @Mapping(source = "profileSubject.id", target = "profileSubjectId")
    @Mapping(source = "classLength.id", target = "classLengthId")
    RateDTO toDto(Rate rate); 

    @Mapping(source = "profileSubjectId", target = "profileSubject")
    @Mapping(source = "classLengthId", target = "classLength")
    Rate toEntity(RateDTO rateDTO);

    default Rate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rate rate = new Rate();
        rate.setId(id);
        return rate;
    }
}
