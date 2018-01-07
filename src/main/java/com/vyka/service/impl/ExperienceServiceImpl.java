package com.vyka.service.impl;

import com.vyka.service.ExperienceService;
import com.vyka.domain.Experience;
import com.vyka.repository.ExperienceRepository;
import com.vyka.repository.search.ExperienceSearchRepository;
import com.vyka.service.dto.ExperienceDTO;
import com.vyka.service.mapper.ExperienceMapper;
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
 * Service Implementation for managing Experience.
 */
@Service
@Transactional
public class ExperienceServiceImpl implements ExperienceService{

    private final Logger log = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    private final ExperienceRepository experienceRepository;

    private final ExperienceMapper experienceMapper;

    private final ExperienceSearchRepository experienceSearchRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper, ExperienceSearchRepository experienceSearchRepository) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
        this.experienceSearchRepository = experienceSearchRepository;
    }

    /**
     * Save a experience.
     *
     * @param experienceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExperienceDTO save(ExperienceDTO experienceDTO) {
        log.debug("Request to save Experience : {}", experienceDTO);
        Experience experience = experienceMapper.toEntity(experienceDTO);
        experience = experienceRepository.save(experience);
        ExperienceDTO result = experienceMapper.toDto(experience);
        experienceSearchRepository.save(experience);
        return result;
    }

    /**
     *  Get all the experiences.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExperienceDTO> findAll() {
        log.debug("Request to get all Experiences");
        return experienceRepository.findAll().stream()
            .map(experienceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one experience by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExperienceDTO findOne(Long id) {
        log.debug("Request to get Experience : {}", id);
        Experience experience = experienceRepository.findOne(id);
        return experienceMapper.toDto(experience);
    }

    /**
     *  Delete the  experience by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Experience : {}", id);
        experienceRepository.delete(id);
        experienceSearchRepository.delete(id);
    }

    /**
     * Search for the experience corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExperienceDTO> search(String query) {
        log.debug("Request to search Experiences for query {}", query);
        return StreamSupport
            .stream(experienceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(experienceMapper::toDto)
            .collect(Collectors.toList());
    }
}
