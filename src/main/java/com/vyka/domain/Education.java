package com.vyka.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * entity ClassLength{
 * classLength Integer required min(30) max(60),
 * active Boolean required,
 * created Instant required,
 * updated Instant
 * }
 */
@ApiModel(description = "entity ClassLength{ classLength Integer required min(30) max(60), active Boolean required, created Instant required, updated Instant }")
@Entity
@Table(name = "education")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "education")
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "course", length = 50, nullable = false)
    private String course;

    @Column(name = "university")
    private String university;

    @NotNull
    @Column(name = "jhi_start", nullable = false)
    private Integer start;

    @Column(name = "jhi_end")
    private Integer end;

    @ManyToOne
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public Education course(String course) {
        this.course = course;
        return this;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUniversity() {
        return university;
    }

    public Education university(String university) {
        this.university = university;
        return this;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Integer getStart() {
        return start;
    }

    public Education start(Integer start) {
        this.start = start;
        return this;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public Education end(Integer end) {
        this.end = end;
        return this;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Profile getProfile() {
        return profile;
    }

    public Education profile(Profile profile) {
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
        Education education = (Education) o;
        if (education.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), education.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Education{" +
            "id=" + getId() +
            ", course='" + getCourse() + "'" +
            ", university='" + getUniversity() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
