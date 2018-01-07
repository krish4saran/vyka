package com.vyka.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

import com.vyka.domain.enumeration.DayOfWeek;

import com.vyka.domain.enumeration.TimeZones;

/**
 * A Availability.
 */
@Entity
@Table(name = "availability")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "availability")
public class Availability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "startTime", nullable = false)
    private LocalTime startTime;
    

	@NotNull
    @Column(name = "endTime", nullable = false)
    private LocalTime endTime;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "availabile")
    private Boolean availabile;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "time_zone", nullable = false)
    private TimeZones timeZone;

    @ManyToOne
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public Availability dayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean isAvailabile() {
        return availabile;
    }

    public Availability availabile(Boolean availabile) {
        this.availabile = availabile;
        return this;
    }

    public void setAvailabile(Boolean availabile) {
        this.availabile = availabile;
    }

    public TimeZones getTimeZone() {
        return timeZone;
    }

    public Availability timeZone(TimeZones timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(TimeZones timeZone) {
        this.timeZone = timeZone;
    }

    public Profile getProfile() {
        return profile;
    }

    public Availability profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Availability availability = (Availability) o;
        if (availability.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), availability.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Availability{" +
            "id=" + getId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", availabile='" + isAvailabile() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
