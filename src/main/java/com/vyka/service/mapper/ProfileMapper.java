package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profile and its DTO ProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {LanguageMapper.class,
		ProfileSubjectMapper.class,
		EducationMapper.class,
		ExperienceMapper.class,
		AwardMapper.class,
		AvailabilityMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    

//    @Mapping(target = "profileSubjects", ignore = true)
//    @Mapping(target = "educations", ignore = true)
//    @Mapping(target = "experiences", ignore = true)
//    @Mapping(target = "awards", ignore = true)
//    @Mapping(target = "availabilities", ignore = true)
    Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
