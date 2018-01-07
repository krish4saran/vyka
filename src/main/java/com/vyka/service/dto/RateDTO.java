package com.vyka.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Rate entity.
 */
public class RateDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "10")
    private BigDecimal rate;

    @NotNull
    private Instant created;

    private Instant updated;

    private Long profileSubjectId;

    private Long classLengthId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

    public Long getProfileSubjectId() {
        return profileSubjectId;
    }

    public void setProfileSubjectId(Long profileSubjectId) {
        this.profileSubjectId = profileSubjectId;
    }

    public Long getClassLengthId() {
        return classLengthId;
    }

    public void setClassLengthId(Long classLengthId) {
        this.classLengthId = classLengthId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RateDTO rateDTO = (RateDTO) o;
        if(rateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RateDTO{" +
            "id=" + getId() +
            ", rate='" + getRate() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
