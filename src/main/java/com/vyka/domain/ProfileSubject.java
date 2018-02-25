package com.vyka.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.vyka.domain.enumeration.LevelValue;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_level")
    private LevelValue level;

    @Column(name = "rate", precision=10, scale=2)
    private BigDecimal rate;

    @Column(name = "sponsored")
    private Boolean sponsored;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "total_rating", precision=10, scale=2)
    private BigDecimal totalRating;

    @ManyToOne
    private Profile profile;

    @OneToOne
    @JoinColumn(unique = true)
    private Subject subject;

    @OneToMany(mappedBy = "profileSubject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Review> reviews = new HashSet<>();

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

    public ProfileSubject level(LevelValue level) {
        this.level = level;
        return this;
    }

    public void setLevel(LevelValue level) {
        this.level = level;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public ProfileSubject rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Boolean isSponsored() {
        return sponsored;
    }

    public ProfileSubject sponsored(Boolean sponsored) {
        this.sponsored = sponsored;
        return this;
    }

    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
    }

    public Boolean isActive() {
        return active;
    }

    public ProfileSubject active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getTotalRating() {
        return totalRating;
    }

    public ProfileSubject totalRating(BigDecimal totalRating) {
        this.totalRating = totalRating;
        return this;
    }

    public void setTotalRating(BigDecimal totalRating) {
        this.totalRating = totalRating;
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
            ", level='" + getLevel() + "'" +
            ", rate='" + getRate() + "'" +
            ", sponsored='" + isSponsored() + "'" +
            ", active='" + isActive() + "'" +
            ", totalRating='" + getTotalRating() + "'" +
            "}";
    }
}
