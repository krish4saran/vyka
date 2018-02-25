package com.vyka.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.vyka.domain.enumeration.DayOfWeek;

/**
 * A DTO for the Availability entity.
 */
public class AvailabilityDTO implements Serializable {

    private Long id;

    private DayOfWeek dayOfWeek;

    private Boolean booked;

    private Boolean active;

    private Instant effectiveDate;

    private Instant deactivatedDate;

    private Long profileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean isBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Instant getDeactivatedDate() {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Instant deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AvailabilityDTO availabilityDTO = (AvailabilityDTO) o;
        if(availabilityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), availabilityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AvailabilityDTO{" +
            "id=" + getId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", booked='" + isBooked() + "'" +
            ", active='" + isActive() + "'" +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", deactivatedDate='" + getDeactivatedDate() + "'" +
            "}";
    }
}
