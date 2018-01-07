package com.vyka.service.dto;


import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.vyka.domain.enumeration.DayOfWeek;
import com.vyka.domain.enumeration.TimeZones;

/**
 * A DTO for the Availability entity.
 */
public class AvailabilityDTO implements Serializable {

    private Long id;

    @NotNull
    private DayOfWeek dayOfWeek;

    private Boolean availabile;

    @NotNull
    private TimeZones timeZone;

    private Long profileId;
    
    private LocalTime endTime;

    private LocalTime startTime;

    

    public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

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

    public Boolean isAvailabile() {
        return availabile;
    }

    public void setAvailabile(Boolean availabile) {
        this.availabile = availabile;
    }

    public TimeZones getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZones timeZone) {
        this.timeZone = timeZone;
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
            ", availabile='" + isAvailabile() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            "}";
    }
}
