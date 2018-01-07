package com.vyka.service.impl;

import com.vyka.service.LanguageService;
import com.vyka.domain.Language;
import com.vyka.repository.LanguageRepository;
import com.vyka.repository.search.LanguageSearchRepository;
import com.vyka.service.dto.LanguageDTO;
import com.vyka.service.mapper.LanguageMapper;
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
 * Service Implementation for managing Language.
 */
@Service
@Transactional
public class LanguageServiceImpl implements LanguageService{

    private final Logger log = LoggerFactory.getLogger(LanguageServiceImpl.class);

    private final LanguageRepository languageRepository;

    private final LanguageMapper languageMapper;

    private final LanguageSearchRepository languageSearchRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository, LanguageMapper languageMapper, LanguageSearchRepository languageSearchRepository) {
        this.languageRepository = languageRepository;
        this.languageMapper = languageMapper;
        this.languageSearchRepository = languageSearchRepository;
    }

    /**
     * Save a language.
     *
     * @param languageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LanguageDTO save(LanguageDTO languageDTO) {
        log.debug("Request to save Language : {}", languageDTO);
        Language language = languageMapper.toEntity(languageDTO);
        language = languageRepository.save(language);
        LanguageDTO result = languageMapper.toDto(language);
        languageSearchRepository.save(language);
        return result;
    }

    /**
     *  Get all the languages.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LanguageDTO> findAll() {
        log.debug("Request to get all Languages");
        return languageRepository.findAll().stream()
            .map(languageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one language by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LanguageDTO findOne(Long id) {
        log.debug("Request to get Language : {}", id);
        Language language = languageRepository.findOne(id);
        return languageMapper.toDto(language);
    }

    /**
     *  Delete the  language by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Language : {}", id);
        languageRepository.delete(id);
        languageSearchRepository.delete(id);
    }

    /**
     * Search for the language corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LanguageDTO> search(String query) {
        log.debug("Request to search Languages for query {}", query);
        return StreamSupport
            .stream(languageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(languageMapper::toDto)
            .collect(Collectors.toList());
    }
}
