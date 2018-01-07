package com.vyka.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.vyka.domain.enumeration.LevelValue;

/**
 * A DTO for the Level entity.
 */
public class LevelDTO implements Serializable {

    private Long id;

    @NotNull
    private LevelValue level;

    private Long profileSubjectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LevelValue getLevel() {
        return level;
    }

    public void setLevel(LevelValue level) {
        this.level = level;
    }

    public Long getProfileSubjectId() {
        return profileSubjectId;
    }

    public void setProfileSubjectId(Long profileSubjectId) {
        this.profileSubjectId = profileSubjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LevelDTO levelDTO = (LevelDTO) o;
        if(levelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), levelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LevelDTO{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
