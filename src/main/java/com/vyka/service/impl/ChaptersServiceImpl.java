package com.vyka.service.impl;

import com.vyka.service.ChaptersService;
import com.vyka.domain.Chapters;
import com.vyka.repository.ChaptersRepository;
import com.vyka.repository.search.ChaptersSearchRepository;
import com.vyka.service.dto.ChaptersDTO;
import com.vyka.service.mapper.ChaptersMapper;
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
 * Service Implementation for managing Chapters.
 */
@Service
@Transactional
public class ChaptersServiceImpl implements ChaptersService{

    private final Logger log = LoggerFactory.getLogger(ChaptersServiceImpl.class);

    private final ChaptersRepository chaptersRepository;

    private final ChaptersMapper chaptersMapper;

    private final ChaptersSearchRepository chaptersSearchRepository;

    public ChaptersServiceImpl(ChaptersRepository chaptersRepository, ChaptersMapper chaptersMapper, ChaptersSearchRepository chaptersSearchRepository) {
        this.chaptersRepository = chaptersRepository;
        this.chaptersMapper = chaptersMapper;
        this.chaptersSearchRepository = chaptersSearchRepository;
    }

    /**
     * Save a chapters.
     *
     * @param chaptersDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ChaptersDTO save(ChaptersDTO chaptersDTO) {
        log.debug("Request to save Chapters : {}", chaptersDTO);
        Chapters chapters = chaptersMapper.toEntity(chaptersDTO);
        chapters = chaptersRepository.save(chapters);
        ChaptersDTO result = chaptersMapper.toDto(chapters);
        chaptersSearchRepository.save(chapters);
        return result;
    }

    /**
     *  Get all the chapters.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChaptersDTO> findAll() {
        log.debug("Request to get all Chapters");
        return chaptersRepository.findAll().stream()
            .map(chaptersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one chapters by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ChaptersDTO findOne(Long id) {
        log.debug("Request to get Chapters : {}", id);
        Chapters chapters = chaptersRepository.findOne(id);
        return chaptersMapper.toDto(chapters);
    }

    /**
     *  Delete the  chapters by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chapters : {}", id);
        chaptersRepository.delete(id);
        chaptersSearchRepository.delete(id);
    }

    /**
     * Search for the chapters corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChaptersDTO> search(String query) {
        log.debug("Request to search Chapters for query {}", query);
        return StreamSupport
            .stream(chaptersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(chaptersMapper::toDto)
            .collect(Collectors.toList());
    }
}
