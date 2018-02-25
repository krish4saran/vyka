package com.vyka.service.impl;

import com.vyka.service.SubjectLevelService;
import com.vyka.domain.SubjectLevel;
import com.vyka.repository.SubjectLevelRepository;
import com.vyka.repository.search.SubjectLevelSearchRepository;
import com.vyka.service.dto.SubjectLevelDTO;
import com.vyka.service.mapper.SubjectLevelMapper;
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
 * Service Implementation for managing SubjectLevel.
 */
@Service
@Transactional
public class SubjectLevelServiceImpl implements SubjectLevelService{

    private final Logger log = LoggerFactory.getLogger(SubjectLevelServiceImpl.class);

    private final SubjectLevelRepository subjectLevelRepository;

    private final SubjectLevelMapper subjectLevelMapper;

    private final SubjectLevelSearchRepository subjectLevelSearchRepository;

    public SubjectLevelServiceImpl(SubjectLevelRepository subjectLevelRepository, SubjectLevelMapper subjectLevelMapper, SubjectLevelSearchRepository subjectLevelSearchRepository) {
        this.subjectLevelRepository = subjectLevelRepository;
        this.subjectLevelMapper = subjectLevelMapper;
        this.subjectLevelSearchRepository = subjectLevelSearchRepository;
    }

    /**
     * Save a subjectLevel.
     *
     * @param subjectLevelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SubjectLevelDTO save(SubjectLevelDTO subjectLevelDTO) {
        log.debug("Request to save SubjectLevel : {}", subjectLevelDTO);
        SubjectLevel subjectLevel = subjectLevelMapper.toEntity(subjectLevelDTO);
        subjectLevel = subjectLevelRepository.save(subjectLevel);
        SubjectLevelDTO result = subjectLevelMapper.toDto(subjectLevel);
        subjectLevelSearchRepository.save(subjectLevel);
        return result;
    }

    /**
     *  Get all the subjectLevels.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectLevelDTO> findAll() {
        log.debug("Request to get all SubjectLevels");
        return subjectLevelRepository.findAll().stream()
            .map(subjectLevelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one subjectLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SubjectLevelDTO findOne(Long id) {
        log.debug("Request to get SubjectLevel : {}", id);
        SubjectLevel subjectLevel = subjectLevelRepository.findOne(id);
        return subjectLevelMapper.toDto(subjectLevel);
    }

    /**
     *  Delete the  subjectLevel by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubjectLevel : {}", id);
        subjectLevelRepository.delete(id);
        subjectLevelSearchRepository.delete(id);
    }

    /**
     * Search for the subjectLevel corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectLevelDTO> search(String query) {
        log.debug("Request to search SubjectLevels for query {}", query);
        return StreamSupport
            .stream(subjectLevelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(subjectLevelMapper::toDto)
            .collect(Collectors.toList());
    }
}
