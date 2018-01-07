package com.vyka.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProfileSubject entity.
 */
public class ProfileSubjectDTO implements Serializable {

    private Long id;

    private Long profileId;

    private Long subjectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileSubjectDTO profileSubjectDTO = (ProfileSubjectDTO) o;
        if(profileSubjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileSubjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileSubjectDTO{" +
            "id=" + getId() +
            "}";
    }
}
