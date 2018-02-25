package com.vyka.web.rest;

import com.vyka.VykaApp;

import com.vyka.domain.Profile;
import com.vyka.repository.ProfileRepository;
import com.vyka.service.ProfileService;
import com.vyka.repository.search.ProfileSearchRepository;
import com.vyka.service.dto.ProfileDTO;
import com.vyka.service.mapper.ProfileMapper;
import com.vyka.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.vyka.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vyka.domain.enumeration.TimeZones;
/**
 * Test class for the ProfileResource REST controller.
 *
 * @see ProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VykaApp.class)
public class ProfileResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_VIDEO_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO_1 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_VIDEO_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_VIDEO_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO_2 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_VIDEO_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_2_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_BACKGROUND_CHECKED = false;
    private static final Boolean UPDATED_BACKGROUND_CHECKED = true;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AA";
    private static final String UPDATED_STATE = "BB";

    private static final String DEFAULT_COUNTRY = "AAA";
    private static final String UPDATED_COUNTRY = "BBB";

    private static final TimeZones DEFAULT_TIME_ZONE = TimeZones.IST;
    private static final TimeZones UPDATED_TIME_ZONE = TimeZones.CST;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileSearchRepository profileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .userId(DEFAULT_USER_ID)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .video1(DEFAULT_VIDEO_1)
            .video1ContentType(DEFAULT_VIDEO_1_CONTENT_TYPE)
            .video2(DEFAULT_VIDEO_2)
            .video2ContentType(DEFAULT_VIDEO_2_CONTENT_TYPE)
            .backgroundChecked(DEFAULT_BACKGROUND_CHECKED)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .timeZone(DEFAULT_TIME_ZONE);
        return profile;
    }

    @Before
    public void initTest() {
        profileSearchRepository.deleteAll();
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testProfile.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProfile.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testProfile.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProfile.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testProfile.getVideo1()).isEqualTo(DEFAULT_VIDEO_1);
        assertThat(testProfile.getVideo1ContentType()).isEqualTo(DEFAULT_VIDEO_1_CONTENT_TYPE);
        assertThat(testProfile.getVideo2()).isEqualTo(DEFAULT_VIDEO_2);
        assertThat(testProfile.getVideo2ContentType()).isEqualTo(DEFAULT_VIDEO_2_CONTENT_TYPE);
        assertThat(testProfile.isBackgroundChecked()).isEqualTo(DEFAULT_BACKGROUND_CHECKED);
        assertThat(testProfile.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testProfile.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testProfile.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testProfile.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);

        // Validate the Profile in Elasticsearch
        Profile profileEs = profileSearchRepository.findOne(testProfile.getId());
        assertThat(profileEs).isEqualToComparingFieldByField(testProfile);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setUserId(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].video1ContentType").value(hasItem(DEFAULT_VIDEO_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video1").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO_1))))
            .andExpect(jsonPath("$.[*].video2ContentType").value(hasItem(DEFAULT_VIDEO_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video2").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO_2))))
            .andExpect(jsonPath("$.[*].backgroundChecked").value(hasItem(DEFAULT_BACKGROUND_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())));
    }

    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.video1ContentType").value(DEFAULT_VIDEO_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.video1").value(Base64Utils.encodeToString(DEFAULT_VIDEO_1)))
            .andExpect(jsonPath("$.video2ContentType").value(DEFAULT_VIDEO_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.video2").value(Base64Utils.encodeToString(DEFAULT_VIDEO_2)))
            .andExpect(jsonPath("$.backgroundChecked").value(DEFAULT_BACKGROUND_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.timeZone").value(DEFAULT_TIME_ZONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        profileSearchRepository.save(profile);
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findOne(profile.getId());
        updatedProfile
            .userId(UPDATED_USER_ID)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .video1(UPDATED_VIDEO_1)
            .video1ContentType(UPDATED_VIDEO_1_CONTENT_TYPE)
            .video2(UPDATED_VIDEO_2)
            .video2ContentType(UPDATED_VIDEO_2_CONTENT_TYPE)
            .backgroundChecked(UPDATED_BACKGROUND_CHECKED)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .timeZone(UPDATED_TIME_ZONE);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testProfile.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProfile.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testProfile.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProfile.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProfile.getVideo1()).isEqualTo(UPDATED_VIDEO_1);
        assertThat(testProfile.getVideo1ContentType()).isEqualTo(UPDATED_VIDEO_1_CONTENT_TYPE);
        assertThat(testProfile.getVideo2()).isEqualTo(UPDATED_VIDEO_2);
        assertThat(testProfile.getVideo2ContentType()).isEqualTo(UPDATED_VIDEO_2_CONTENT_TYPE);
        assertThat(testProfile.isBackgroundChecked()).isEqualTo(UPDATED_BACKGROUND_CHECKED);
        assertThat(testProfile.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testProfile.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testProfile.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testProfile.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);

        // Validate the Profile in Elasticsearch
        Profile profileEs = profileSearchRepository.findOne(testProfile.getId());
        assertThat(profileEs).isEqualToComparingFieldByField(testProfile);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        profileSearchRepository.save(profile);
        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Get the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean profileExistsInEs = profileSearchRepository.exists(profile.getId());
        assertThat(profileExistsInEs).isFalse();

        // Validate the database is empty
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        profileSearchRepository.save(profile);

        // Search the profile
        restProfileMockMvc.perform(get("/api/_search/profiles?query=id:" + profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].video1ContentType").value(hasItem(DEFAULT_VIDEO_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video1").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO_1))))
            .andExpect(jsonPath("$.[*].video2ContentType").value(hasItem(DEFAULT_VIDEO_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video2").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO_2))))
            .andExpect(jsonPath("$.[*].backgroundChecked").value(hasItem(DEFAULT_BACKGROUND_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profile.class);
        Profile profile1 = new Profile();
        profile1.setId(1L);
        Profile profile2 = new Profile();
        profile2.setId(profile1.getId());
        assertThat(profile1).isEqualTo(profile2);
        profile2.setId(2L);
        assertThat(profile1).isNotEqualTo(profile2);
        profile1.setId(null);
        assertThat(profile1).isNotEqualTo(profile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileDTO.class);
        ProfileDTO profileDTO1 = new ProfileDTO();
        profileDTO1.setId(1L);
        ProfileDTO profileDTO2 = new ProfileDTO();
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
        profileDTO2.setId(profileDTO1.getId());
        assertThat(profileDTO1).isEqualTo(profileDTO2);
        profileDTO2.setId(2L);
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
        profileDTO1.setId(null);
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profileMapper.fromId(null)).isNull();
    }
}
