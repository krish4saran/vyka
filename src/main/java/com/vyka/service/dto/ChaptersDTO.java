package com.vyka.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Chapters entity.
 */
public class ChaptersDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String description;

    @NotNull
    @Min(value = 1)
    private Integer numberOfClasses;

    private Long levelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(Integer numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChaptersDTO chaptersDTO = (ChaptersDTO) o;
        if(chaptersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chaptersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChaptersDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", numberOfClasses='" + getNumberOfClasses() + "'" +
            "}";
    }
}
