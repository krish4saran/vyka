package com.vyka.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.vyka.domain.enumeration.LevelValue;

/**
 * A DTO for the ProfileSubject entity.
 */
public class ProfileSubjectDTO implements Serializable {

    private Long id;

    private LevelValue level;

    private BigDecimal rate;

    private Boolean sponsored;

    private Boolean active;

    private BigDecimal totalRating;

    private Long profileId;

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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Boolean isSponsored() {
        return sponsored;
    }

    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(BigDecimal totalRating) {
        this.totalRating = totalRating;
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
            ", level='" + getLevel() + "'" +
            ", rate='" + getRate() + "'" +
            ", sponsored='" + isSponsored() + "'" +
            ", active='" + isActive() + "'" +
            ", totalRating='" + getTotalRating() + "'" +
            "}";
    }
}
