package com.vyka.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.vyka.domain.enumeration.LevelValue;

/**
 * A DTO for the SubjectLevel entity.
 */
public class SubjectLevelDTO implements Serializable {

    private Long id;

    private LevelValue level;

    @Lob
    private byte[] description;
    private String descriptionContentType;

    private Long subjectId;

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

    public byte[] getDescription() {
        return description;
    }

    public void setDescription(byte[] description) {
        this.description = description;
    }

    public String getDescriptionContentType() {
        return descriptionContentType;
    }

    public void setDescriptionContentType(String descriptionContentType) {
        this.descriptionContentType = descriptionContentType;
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

        SubjectLevelDTO subjectLevelDTO = (SubjectLevelDTO) o;
        if(subjectLevelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subjectLevelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubjectLevelDTO{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
