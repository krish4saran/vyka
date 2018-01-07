package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.ChaptersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Chapters and its DTO ChaptersDTO.
 */
@Mapper(componentModel = "spring", uses = {LevelMapper.class})
public interface ChaptersMapper extends EntityMapper<ChaptersDTO, Chapters> {

    @Mapping(source = "level.id", target = "levelId")
    ChaptersDTO toDto(Chapters chapters); 

    @Mapping(source = "levelId", target = "level")
    Chapters toEntity(ChaptersDTO chaptersDTO);

    default Chapters fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chapters chapters = new Chapters();
        chapters.setId(id);
        return chapters;
    }
}
