package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.AwardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Award and its DTO AwardDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface AwardMapper extends EntityMapper<AwardDTO, Award> {

    @Mapping(source = "profile.id", target = "profileId")
    AwardDTO toDto(Award award); 

    @Mapping(source = "profileId", target = "profile")
    Award toEntity(AwardDTO awardDTO);

    default Award fromId(Long id) {
        if (id == null) {
            return null;
        }
        Award award = new Award();
        award.setId(id);
        return award;
    }
}
