package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.ProfileSubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProfileSubject and its DTO ProfileSubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class, SubjectMapper.class})
public interface ProfileSubjectMapper extends EntityMapper<ProfileSubjectDTO, ProfileSubject> {

    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "subject.id", target = "subjectId")
    ProfileSubjectDTO toDto(ProfileSubject profileSubject); 

    @Mapping(source = "profileId", target = "profile")
    @Mapping(source = "subjectId", target = "subject")
    @Mapping(target = "reviews", ignore = true)
    ProfileSubject toEntity(ProfileSubjectDTO profileSubjectDTO);

    default ProfileSubject fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfileSubject profileSubject = new ProfileSubject();
        profileSubject.setId(id);
        return profileSubject;
    }
}
