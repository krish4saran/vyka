package com.vyka.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.vyka.domain.enumeration.TimeZones;

/**
 * A DTO for the Profile entity.
 */
public class ProfileDTO implements Serializable {

    private Long id;

    @NotNull
    private Long userId;

    @Lob
    private String description;

    private Boolean active;

    @Lob
    private byte[] image;
    private String imageContentType;

    @Lob
    private byte[] video1;
    private String video1ContentType;

    @Lob
    private byte[] video2;
    private String video2ContentType;

    private Boolean backgroundChecked;

    private String city;

    @Size(max = 2)
    private String state;

    @Size(max = 3)
    private String country;

    private TimeZones timeZone;

    private Set<LanguageDTO> languages = new HashSet<>();
    
    private Set<ProfileSubjectDTO> profileSubjects = new HashSet<>();
    
    private Set<EducationDTO> educations = new HashSet<>();
    
    private Set<ExperienceDTO> experiences =  new HashSet<>();
    
    private Set<AwardDTO> awards = new HashSet<>();
    
    private Set<AvailabilityDTO> availabilities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getVideo1() {
        return video1;
    }

    public void setVideo1(byte[] video1) {
        this.video1 = video1;
    }

    public String getVideo1ContentType() {
        return video1ContentType;
    }

    public void setVideo1ContentType(String video1ContentType) {
        this.video1ContentType = video1ContentType;
    }

    public byte[] getVideo2() {
        return video2;
    }

    public void setVideo2(byte[] video2) {
        this.video2 = video2;
    }

    public String getVideo2ContentType() {
        return video2ContentType;
    }

    public void setVideo2ContentType(String video2ContentType) {
        this.video2ContentType = video2ContentType;
    }

    public Boolean isBackgroundChecked() {
        return backgroundChecked;
    }

    public void setBackgroundChecked(Boolean backgroundChecked) {
        this.backgroundChecked = backgroundChecked;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public TimeZones getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZones timeZone) {
        this.timeZone = timeZone;
    }

    public Set<LanguageDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<LanguageDTO> languages) {
        this.languages = languages;
    }
    
    

    public Set<ProfileSubjectDTO> getProfileSubjects() {
		return profileSubjects;
	}

	public void setProfileSubjects(Set<ProfileSubjectDTO> profileSubjects) {
		this.profileSubjects = profileSubjects;
	}

	public Set<EducationDTO> getEducations() {
		return educations;
	}

	public void setEducations(Set<EducationDTO> educations) {
		this.educations = educations;
	}

	public Set<ExperienceDTO> getExperiences() {
		return experiences;
	}

	public void setExperiences(Set<ExperienceDTO> experiences) {
		this.experiences = experiences;
	}

	public Set<AwardDTO> getAwards() {
		return awards;
	}

	public void setAwards(Set<AwardDTO> awards) {
		this.awards = awards;
	}

	public Set<AvailabilityDTO> getAvailabilities() {
		return availabilities;
	}

	public void setAvailabilities(Set<AvailabilityDTO> availabilities) {
		this.availabilities = availabilities;
	}

	public Boolean getActive() {
		return active;
	}

	public Boolean getBackgroundChecked() {
		return backgroundChecked;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileDTO profileDTO = (ProfileDTO) o;
        if(profileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProfileDTO [id=");
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
		builder.append(", languages=");
		builder.append(languages);
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
		builder.append("]");
		return builder.toString();
	}




}
