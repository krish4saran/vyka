package com.vyka.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProfileSubject.
 */
@Entity
@Table(name = "profile_subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "profilesubject")
public class ProfileSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Profile profile;

    @OneToOne
    @JoinColumn(unique = true)
    private Subject subject;

    @OneToMany(mappedBy = "profileSubject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "profileSubject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Level> levels = new HashSet<>();

    @OneToMany(mappedBy = "profileSubject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rate> rates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public ProfileSubject profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Subject getSubject() {
        return subject;
    }

    public ProfileSubject subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public ProfileSubject reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public ProfileSubject addReview(Review review) {
        this.reviews.add(review);
        review.setProfileSubject(this);
        return this;
    }

    public ProfileSubject removeReview(Review review) {
        this.reviews.remove(review);
        review.setProfileSubject(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Level> getLevels() {
        return levels;
    }

    public ProfileSubject levels(Set<Level> levels) {
        this.levels = levels;
        return this;
    }

    public ProfileSubject addLevel(Level level) {
        this.levels.add(level);
        level.setProfileSubject(this);
        return this;
    }

    public ProfileSubject removeLevel(Level level) {
        this.levels.remove(level);
        level.setProfileSubject(null);
        return this;
    }

    public void setLevels(Set<Level> levels) {
        this.levels = levels;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public ProfileSubject rates(Set<Rate> rates) {
        this.rates = rates;
        return this;
    }

    public ProfileSubject addRate(Rate rate) {
        this.rates.add(rate);
        rate.setProfileSubject(this);
        return this;
    }

    public ProfileSubject removeRate(Rate rate) {
        this.rates.remove(rate);
        rate.setProfileSubject(null);
        return this;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
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
        ProfileSubject profileSubject = (ProfileSubject) o;
        if (profileSubject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileSubject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileSubject{" +
            "id=" + getId() +
            "}";
    }
}
