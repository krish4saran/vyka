package com.vyka.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Chapters.
 */
@Entity
@Table(name = "chapters")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "chapters")
public class Chapters implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @NotNull
    @Min(value = 1)
    @Column(name = "number_of_classes", nullable = false)
    private Integer numberOfClasses;

    @ManyToOne
    private Level level;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Chapters description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfClasses() {
        return numberOfClasses;
    }

    public Chapters numberOfClasses(Integer numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
        return this;
    }

    public void setNumberOfClasses(Integer numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public Level getLevel() {
        return level;
    }

    public Chapters level(Level level) {
        this.level = level;
        return this;
    }

    public void setLevel(Level level) {
        this.level = level;
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
        Chapters chapters = (Chapters) o;
        if (chapters.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chapters.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chapters{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", numberOfClasses='" + getNumberOfClasses() + "'" +
            "}";
    }
}
