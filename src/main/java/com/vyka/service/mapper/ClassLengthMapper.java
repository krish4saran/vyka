package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.ClassLengthDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClassLength and its DTO ClassLengthDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassLengthMapper extends EntityMapper<ClassLengthDTO, ClassLength> {

    

    

    default ClassLength fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassLength classLength = new ClassLength();
        classLength.setId(id);
        return classLength;
    }
}
