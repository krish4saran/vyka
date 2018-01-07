package com.vyka.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Profile entity.
 */
public class ProfileDTO implements Serializable {

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
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

    @NotNull
    private Instant created;

    private Instant updated;

    private Long locationId;

    private Set<LanguageDTO> languages = new HashSet<>();

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

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Set<LanguageDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<LanguageDTO> languages) {
        this.languages = languages;
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
        return "ProfileDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + isActive() + "'" +
            ", image='" + getImage() + "'" +
            ", video1='" + getVideo1() + "'" +
            ", video2='" + getVideo2() + "'" +
            ", backgroundChecked='" + isBackgroundChecked() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
