package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.LevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Level and its DTO LevelDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileSubjectMapper.class})
public interface LevelMapper extends EntityMapper<LevelDTO, Level> {

    @Mapping(source = "profileSubject.id", target = "profileSubjectId")
    LevelDTO toDto(Level level); 

    @Mapping(source = "profileSubjectId", target = "profileSubject")
    @Mapping(target = "chapters", ignore = true)
    Level toEntity(LevelDTO levelDTO);

    default Level fromId(Long id) {
        if (id == null) {
            return null;
        }
        Level level = new Level();
        level.setId(id);
        return level;
    }
}
