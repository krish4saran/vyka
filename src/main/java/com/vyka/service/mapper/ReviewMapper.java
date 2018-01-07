package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.ReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Review and its DTO ReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileSubjectMapper.class})
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {

    @Mapping(source = "profileSubject.id", target = "profileSubjectId")
    ReviewDTO toDto(Review review); 

    @Mapping(source = "profileSubjectId", target = "profileSubject")
    Review toEntity(ReviewDTO reviewDTO);

    default Review fromId(Long id) {
        if (id == null) {
            return null;
        }
        Review review = new Review();
        review.setId(id);
        return review;
    }
}
