package com.vyka.service.impl;

import com.vyka.service.ProfileSubjectService;
import com.vyka.domain.ProfileSubject;
import com.vyka.repository.ProfileSubjectRepository;
import com.vyka.repository.search.ProfileSubjectSearchRepository;
import com.vyka.service.dto.ProfileSubjectDTO;
import com.vyka.service.mapper.ProfileSubjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProfileSubject.
 */
@Service
@Transactional
public class ProfileSubjectServiceImpl implements ProfileSubjectService{

    private final Logger log = LoggerFactory.getLogger(ProfileSubjectServiceImpl.class);

    private final ProfileSubjectRepository profileSubjectRepository;

    private final ProfileSubjectMapper profileSubjectMapper;

    private final ProfileSubjectSearchRepository profileSubjectSearchRepository;

    public ProfileSubjectServiceImpl(ProfileSubjectRepository profileSubjectRepository, ProfileSubjectMapper profileSubjectMapper, ProfileSubjectSearchRepository profileSubjectSearchRepository) {
        this.profileSubjectRepository = profileSubjectRepository;
        this.profileSubjectMapper = profileSubjectMapper;
        this.profileSubjectSearchRepository = profileSubjectSearchRepository;
    }

    /**
     * Save a profileSubject.
     *
     * @param profileSubjectDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProfileSubjectDTO save(ProfileSubjectDTO profileSubjectDTO) {
        log.debug("Request to save ProfileSubject : {}", profileSubjectDTO);
        ProfileSubject profileSubject = profileSubjectMapper.toEntity(profileSubjectDTO);
        profileSubject = profileSubjectRepository.save(profileSubject);
        ProfileSubjectDTO result = profileSubjectMapper.toDto(profileSubject);
        profileSubjectSearchRepository.save(profileSubject);
        return result;
    }

    /**
     *  Get all the profileSubjects.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfileSubjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProfileSubjects");
        return profileSubjectRepository.findAll(pageable)
            .map(profileSubjectMapper::toDto);
    }

    /**
     *  Get one profileSubject by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProfileSubjectDTO findOne(Long id) {
        log.debug("Request to get ProfileSubject : {}", id);
        ProfileSubject profileSubject = profileSubjectRepository.findOne(id);
        return profileSubjectMapper.toDto(profileSubject);
    }

    /**
     *  Delete the  profileSubject by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfileSubject : {}", id);
        profileSubjectRepository.delete(id);
        profileSubjectSearchRepository.delete(id);
    }

    /**
     * Search for the profileSubject corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfileSubjectDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProfileSubjects for query {}", query);
        Page<ProfileSubject> result = profileSubjectSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(profileSubjectMapper::toDto);
    }
}
