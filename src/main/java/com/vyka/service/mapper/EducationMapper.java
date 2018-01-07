package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.EducationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Education and its DTO EducationDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface EducationMapper extends EntityMapper<EducationDTO, Education> {

    @Mapping(source = "profile.id", target = "profileId")
    EducationDTO toDto(Education education); 

    @Mapping(source = "profileId", target = "profile")
    Education toEntity(EducationDTO educationDTO);

    default Education fromId(Long id) {
        if (id == null) {
            return null;
        }
        Education education = new Education();
        education.setId(id);
        return education;
    }
}
