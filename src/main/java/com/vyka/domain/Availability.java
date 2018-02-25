package com.vyka.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.vyka.domain.enumeration.DayOfWeek;

/**
 * Location available will be provided by configuration
 * so avoiding constraints for country
 */
@ApiModel(description = "Location available will be provided by configuration so avoiding constraints for country")
@Entity
@Table(name = "availability")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "availability")
public class Availability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @Column(name = "booked")
    private Boolean booked;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "effective_date")
    private Instant effectiveDate;

    @Column(name = "deactivated_date")
    private Instant deactivatedDate;

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

    public Boolean isBooked() {
        return booked;
    }

    public Availability booked(Boolean booked) {
        this.booked = booked;
        return this;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public Boolean isActive() {
        return active;
    }

    public Availability active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    public Availability effectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Instant getDeactivatedDate() {
        return deactivatedDate;
    }

    public Availability deactivatedDate(Instant deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
        return this;
    }

    public void setDeactivatedDate(Instant deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
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
            ", booked='" + isBooked() + "'" +
            ", active='" + isActive() + "'" +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", deactivatedDate='" + getDeactivatedDate() + "'" +
            "}";
    }
}
