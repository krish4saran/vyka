package com.vyka.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.vyka.domain.enumeration.LevelValue;

/**
 * A SubjectLevel.
 */
@Entity
@Table(name = "subject_level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "subjectlevel")
public class SubjectLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_level")
    private LevelValue level;

    @Lob
    @Column(name = "description")
    private byte[] description;

    @Column(name = "description_content_type")
    private String descriptionContentType;

    @ManyToOne
    private Subject subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LevelValue getLevel() {
        return level;
    }

    public SubjectLevel level(LevelValue level) {
        this.level = level;
        return this;
    }

    public void setLevel(LevelValue level) {
        this.level = level;
    }

    public byte[] getDescription() {
        return description;
    }

    public SubjectLevel description(byte[] description) {
        this.description = description;
        return this;
    }

    public void setDescription(byte[] description) {
        this.description = description;
    }

    public String getDescriptionContentType() {
        return descriptionContentType;
    }

    public SubjectLevel descriptionContentType(String descriptionContentType) {
        this.descriptionContentType = descriptionContentType;
        return this;
    }

    public void setDescriptionContentType(String descriptionContentType) {
        this.descriptionContentType = descriptionContentType;
    }

    public Subject getSubject() {
        return subject;
    }

    public SubjectLevel subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
        SubjectLevel subjectLevel = (SubjectLevel) o;
        if (subjectLevel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subjectLevel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubjectLevel{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", description='" + getDescription() + "'" +
            ", descriptionContentType='" + descriptionContentType + "'" +
            "}";
    }
}
