package com.vyka.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.vyka.domain.enumeration.TimeZones;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "video_1")
    private byte[] video1;

    @Column(name = "video_1_content_type")
    private String video1ContentType;

    @Lob
    @Column(name = "video_2")
    private byte[] video2;

    @Column(name = "video_2_content_type")
    private String video2ContentType;

    @Column(name = "background_checked")
    private Boolean backgroundChecked;

    @Column(name = "city")
    private String city;

    @Size(max = 2)
    @Column(name = "state", length = 2)
    private String state;

    @Size(max = 3)
    @Column(name = "country", length = 3)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_zone")
    private TimeZones timeZone;

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProfileSubject> profileSubjects = new HashSet<>();

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Education> educations = new HashSet<>();

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Experience> experiences = new HashSet<>();

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Award> awards = new HashSet<>();

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Availability> availabilities = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "profile_language",
               joinColumns = @JoinColumn(name="profiles_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="languages_id", referencedColumnName="id"))
    private Set<Language> languages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Profile userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public Profile description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public Profile active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public byte[] getImage() {
        return image;
    }

    public Profile image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Profile imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getVideo1() {
        return video1;
    }

    public Profile video1(byte[] video1) {
        this.video1 = video1;
        return this;
    }

    public void setVideo1(byte[] video1) {
        this.video1 = video1;
    }

    public String getVideo1ContentType() {
        return video1ContentType;
    }

    public Profile video1ContentType(String video1ContentType) {
        this.video1ContentType = video1ContentType;
        return this;
    }

    public void setVideo1ContentType(String video1ContentType) {
        this.video1ContentType = video1ContentType;
    }

    public byte[] getVideo2() {
        return video2;
    }

    public Profile video2(byte[] video2) {
        this.video2 = video2;
        return this;
    }

    public void setVideo2(byte[] video2) {
        this.video2 = video2;
    }

    public String getVideo2ContentType() {
        return video2ContentType;
    }

    public Profile video2ContentType(String video2ContentType) {
        this.video2ContentType = video2ContentType;
        return this;
    }

    public void setVideo2ContentType(String video2ContentType) {
        this.video2ContentType = video2ContentType;
    }

    public Boolean isBackgroundChecked() {
        return backgroundChecked;
    }

    public Profile backgroundChecked(Boolean backgroundChecked) {
        this.backgroundChecked = backgroundChecked;
        return this;
    }

    public void setBackgroundChecked(Boolean backgroundChecked) {
        this.backgroundChecked = backgroundChecked;
    }

    public String getCity() {
        return city;
    }

    public Profile city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Profile state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public Profile country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public TimeZones getTimeZone() {
        return timeZone;
    }

    public Profile timeZone(TimeZones timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(TimeZones timeZone) {
        this.timeZone = timeZone;
    }

    public Set<ProfileSubject> getProfileSubjects() {
        return profileSubjects;
    }

    public Profile profileSubjects(Set<ProfileSubject> profileSubjects) {
        this.profileSubjects = profileSubjects;
        return this;
    }

    public Profile addProfileSubject(ProfileSubject profileSubject) {
        this.profileSubjects.add(profileSubject);
        profileSubject.setProfile(this);
        return this;
    }

    public Profile removeProfileSubject(ProfileSubject profileSubject) {
        this.profileSubjects.remove(profileSubject);
        profileSubject.setProfile(null);
        return this;
    }

    public void setProfileSubjects(Set<ProfileSubject> profileSubjects) {
        this.profileSubjects = profileSubjects;
    }

    public Set<Education> getEducations() {
        return educations;
    }

    public Profile educations(Set<Education> educations) {
        this.educations = educations;
        return this;
    }

    public Profile addEducation(Education education) {
        this.educations.add(education);
        education.setProfile(this);
        return this;
    }

    public Profile removeEducation(Education education) {
        this.educations.remove(education);
        education.setProfile(null);
        return this;
    }

    public void setEducations(Set<Education> educations) {
        this.educations = educations;
    }

    public Set<Experience> getExperiences() {
        return experiences;
    }

    public Profile experiences(Set<Experience> experiences) {
        this.experiences = experiences;
        return this;
    }

    public Profile addExperience(Experience experience) {
        this.experiences.add(experience);
        experience.setProfile(this);
        return this;
    }

    public Profile removeExperience(Experience experience) {
        this.experiences.remove(experience);
        experience.setProfile(null);
        return this;
    }

    public void setExperiences(Set<Experience> experiences) {
        this.experiences = experiences;
    }

    public Set<Award> getAwards() {
        return awards;
    }

    public Profile awards(Set<Award> awards) {
        this.awards = awards;
        return this;
    }

    public Profile addAward(Award award) {
        this.awards.add(award);
        award.setProfile(this);
        return this;
    }

    public Profile removeAward(Award award) {
        this.awards.remove(award);
        award.setProfile(null);
        return this;
    }

    public void setAwards(Set<Award> awards) {
        this.awards = awards;
    }

    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public Profile availabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
        return this;
    }

    public Profile addAvailability(Availability availability) {
        this.availabilities.add(availability);
        availability.setProfile(this);
        return this;
    }

    public Profile removeAvailability(Availability availability) {
        this.availabilities.remove(availability);
        availability.setProfile(null);
        return this;
    }

    public void setAvailabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public Profile languages(Set<Language> languages) {
        this.languages = languages;
        return this;
    }

    public Profile addLanguage(Language language) {
        this.languages.add(language);
        language.getProfiles().add(this);
        return this;
    }

    public Profile removeLanguage(Language language) {
        this.languages.remove(language);
        language.getProfiles().remove(this);
        return this;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
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
        Profile profile = (Profile) o;
        if (profile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Profile [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", description=");
		builder.append(description);
		builder.append(", active=");
		builder.append(active);
		builder.append(", image=");
		builder.append(Arrays.toString(image));
		builder.append(", imageContentType=");
		builder.append(imageContentType);
		builder.append(", video1=");
		builder.append(Arrays.toString(video1));
		builder.append(", video1ContentType=");
		builder.append(video1ContentType);
		builder.append(", video2=");
		builder.append(Arrays.toString(video2));
		builder.append(", video2ContentType=");
		builder.append(video2ContentType);
		builder.append(", backgroundChecked=");
		builder.append(backgroundChecked);
		builder.append(", city=");
		builder.append(city);
		builder.append(", state=");
		builder.append(state);
		builder.append(", country=");
		builder.append(country);
		builder.append(", timeZone=");
		builder.append(timeZone);
		builder.append(", profileSubjects=");
		builder.append(profileSubjects);
		builder.append(", educations=");
		builder.append(educations);
		builder.append(", experiences=");
		builder.append(experiences);
		builder.append(", awards=");
		builder.append(awards);
		builder.append(", availabilities=");
		builder.append(availabilities);
		builder.append(", languages=");
		builder.append(languages);
		builder.append("]");
		return builder.toString();
	}

 
}
