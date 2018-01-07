package com.vyka.service.impl;

import com.vyka.service.LevelService;
import com.vyka.domain.Level;
import com.vyka.repository.LevelRepository;
import com.vyka.repository.search.LevelSearchRepository;
import com.vyka.service.dto.LevelDTO;
import com.vyka.service.mapper.LevelMapper;
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
 * Service Implementation for managing Level.
 */
@Service
@Transactional
public class LevelServiceImpl implements LevelService{

    private final Logger log = LoggerFactory.getLogger(LevelServiceImpl.class);

    private final LevelRepository levelRepository;

    private final LevelMapper levelMapper;

    private final LevelSearchRepository levelSearchRepository;

    public LevelServiceImpl(LevelRepository levelRepository, LevelMapper levelMapper, LevelSearchRepository levelSearchRepository) {
        this.levelRepository = levelRepository;
        this.levelMapper = levelMapper;
        this.levelSearchRepository = levelSearchRepository;
    }

    /**
     * Save a level.
     *
     * @param levelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LevelDTO save(LevelDTO levelDTO) {
        log.debug("Request to save Level : {}", levelDTO);
        Level level = levelMapper.toEntity(levelDTO);
        level = levelRepository.save(level);
        LevelDTO result = levelMapper.toDto(level);
        levelSearchRepository.save(level);
        return result;
    }

    /**
     *  Get all the levels.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LevelDTO> findAll() {
        log.debug("Request to get all Levels");
        return levelRepository.findAll().stream()
            .map(levelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one level by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LevelDTO findOne(Long id) {
        log.debug("Request to get Level : {}", id);
        Level level = levelRepository.findOne(id);
        return levelMapper.toDto(level);
    }

    /**
     *  Delete the  level by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Level : {}", id);
        levelRepository.delete(id);
        levelSearchRepository.delete(id);
    }

    /**
     * Search for the level corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LevelDTO> search(String query) {
        log.debug("Request to search Levels for query {}", query);
        return StreamSupport
            .stream(levelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(levelMapper::toDto)
            .collect(Collectors.toList());
    }
}
