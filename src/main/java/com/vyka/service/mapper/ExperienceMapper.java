package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.ExperienceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Experience and its DTO ExperienceDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface ExperienceMapper extends EntityMapper<ExperienceDTO, Experience> {

    @Mapping(source = "profile.id", target = "profileId")
    ExperienceDTO toDto(Experience experience); 

    @Mapping(source = "profileId", target = "profile")
    Experience toEntity(ExperienceDTO experienceDTO);

    default Experience fromId(Long id) {
        if (id == null) {
            return null;
        }
        Experience experience = new Experience();
        experience.setId(id);
        return experience;
    }
}
