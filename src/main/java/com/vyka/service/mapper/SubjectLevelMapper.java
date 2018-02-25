package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.SubjectLevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SubjectLevel and its DTO SubjectLevelDTO.
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class})
public interface SubjectLevelMapper extends EntityMapper<SubjectLevelDTO, SubjectLevel> {

    @Mapping(source = "subject.id", target = "subjectId")
    SubjectLevelDTO toDto(SubjectLevel subjectLevel); 

    @Mapping(source = "subjectId", target = "subject")
    SubjectLevel toEntity(SubjectLevelDTO subjectLevelDTO);

    default SubjectLevel fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubjectLevel subjectLevel = new SubjectLevel();
        subjectLevel.setId(id);
        return subjectLevel;
    }
}
