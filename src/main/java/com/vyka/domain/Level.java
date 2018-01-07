package com.vyka.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.vyka.domain.enumeration.LevelValue;

/**
 * A Level.
 */
@Entity
@Table(name = "level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "level")
public class Level implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_level", nullable = false)
    private LevelValue level;

    @ManyToOne
    private ProfileSubject profileSubject;

    @OneToMany(mappedBy = "level")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chapters> chapters = new HashSet<>();

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

    public Level level(LevelValue level) {
        this.level = level;
        return this;
    }

    public void setLevel(LevelValue level) {
        this.level = level;
    }

    public ProfileSubject getProfileSubject() {
        return profileSubject;
    }

    public Level profileSubject(ProfileSubject profileSubject) {
        this.profileSubject = profileSubject;
        return this;
    }

    public void setProfileSubject(ProfileSubject profileSubject) {
        this.profileSubject = profileSubject;
    }

    public Set<Chapters> getChapters() {
        return chapters;
    }

    public Level chapters(Set<Chapters> chapters) {
        this.chapters = chapters;
        return this;
    }

    public Level addChapters(Chapters chapters) {
        this.chapters.add(chapters);
        chapters.setLevel(this);
        return this;
    }

    public Level removeChapters(Chapters chapters) {
        this.chapters.remove(chapters);
        chapters.setLevel(null);
        return this;
    }

    public void setChapters(Set<Chapters> chapters) {
        this.chapters = chapters;
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
        Level level = (Level) o;
        if (level.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), level.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Level{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
