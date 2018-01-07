package com.vyka.service.impl;

import com.vyka.service.EducationService;
import com.vyka.domain.Education;
import com.vyka.repository.EducationRepository;
import com.vyka.repository.search.EducationSearchRepository;
import com.vyka.service.dto.EducationDTO;
import com.vyka.service.mapper.EducationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Education.
 */
@Service
@Transactional
public class EducationServiceImpl implements EducationService{

    private final Logger log = LoggerFactory.getLogger(EducationServiceImpl.class);

    private final EducationRepository educationRepository;

    private final EducationMapper educationMapper;

    private final EducationSearchRepository educationSearchRepository;

    public EducationServiceImpl(EducationRepository educationRepository, EducationMapper educationMapper, EducationSearchRepository educationSearchRepository) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
        this.educationSearchRepository = educationSearchRepository;
    }

    /**
     * Save a education.
     *
     * @param educationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EducationDTO save(EducationDTO educationDTO) {
        log.debug("Request to save Education : {}", educationDTO);
        Education education = educationMapper.toEntity(educationDTO);
        education = educationRepository.save(education);
        EducationDTO result = educationMapper.toDto(education);
        educationSearchRepository.save(education);
        return result;
    }

    /**
     *  Get all the educations.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EducationDTO> findAll() {
        log.debug("Request to get all Educations");
        return educationRepository.findAll().stream()
            .map(educationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one education by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EducationDTO findOne(Long id) {
        log.debug("Request to get Education : {}", id);
        Education education = educationRepository.findOne(id);
        return educationMapper.toDto(education);
    }

    /**
     *  Delete the  education by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Education : {}", id);
        educationRepository.delete(id);
        educationSearchRepository.delete(id);
    }

    /**
     * Search for the education corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EducationDTO> search(String query) {
        log.debug("Request to search Educations for query {}", query);
        return StreamSupport
            .stream(educationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(educationMapper::toDto)
            .collect(Collectors.toList());
    }
}
