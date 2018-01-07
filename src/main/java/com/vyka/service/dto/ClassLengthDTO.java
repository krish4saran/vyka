package com.vyka.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClassLength entity.
 */
public class ClassLengthDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 30)
    @Max(value = 60)
    private Integer classLength;

    @NotNull
    private Boolean active;

    @NotNull
    private Instant created;

    private Instant updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClassLength() {
        return classLength;
    }

    public void setClassLength(Integer classLength) {
        this.classLength = classLength;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassLengthDTO classLengthDTO = (ClassLengthDTO) o;
        if(classLengthDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classLengthDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassLengthDTO{" +
            "id=" + getId() +
            ", classLength='" + getClassLength() + "'" +
            ", active='" + isActive() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
