package com.vyka.service.impl;

import com.vyka.service.ClassLengthService;
import com.vyka.domain.ClassLength;
import com.vyka.repository.ClassLengthRepository;
import com.vyka.repository.search.ClassLengthSearchRepository;
import com.vyka.service.dto.ClassLengthDTO;
import com.vyka.service.mapper.ClassLengthMapper;
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
 * Service Implementation for managing ClassLength.
 */
@Service
@Transactional
public class ClassLengthServiceImpl implements ClassLengthService{

    private final Logger log = LoggerFactory.getLogger(ClassLengthServiceImpl.class);

    private final ClassLengthRepository classLengthRepository;

    private final ClassLengthMapper classLengthMapper;

    private final ClassLengthSearchRepository classLengthSearchRepository;

    public ClassLengthServiceImpl(ClassLengthRepository classLengthRepository, ClassLengthMapper classLengthMapper, ClassLengthSearchRepository classLengthSearchRepository) {
        this.classLengthRepository = classLengthRepository;
        this.classLengthMapper = classLengthMapper;
        this.classLengthSearchRepository = classLengthSearchRepository;
    }

    /**
     * Save a classLength.
     *
     * @param classLengthDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassLengthDTO save(ClassLengthDTO classLengthDTO) {
        log.debug("Request to save ClassLength : {}", classLengthDTO);
        ClassLength classLength = classLengthMapper.toEntity(classLengthDTO);
        classLength = classLengthRepository.save(classLength);
        ClassLengthDTO result = classLengthMapper.toDto(classLength);
        classLengthSearchRepository.save(classLength);
        return result;
    }

    /**
     *  Get all the classLengths.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClassLengthDTO> findAll() {
        log.debug("Request to get all ClassLengths");
        return classLengthRepository.findAll().stream()
            .map(classLengthMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one classLength by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassLengthDTO findOne(Long id) {
        log.debug("Request to get ClassLength : {}", id);
        ClassLength classLength = classLengthRepository.findOne(id);
        return classLengthMapper.toDto(classLength);
    }

    /**
     *  Delete the  classLength by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassLength : {}", id);
        classLengthRepository.delete(id);
        classLengthSearchRepository.delete(id);
    }

    /**
     * Search for the classLength corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClassLengthDTO> search(String query) {
        log.debug("Request to search ClassLengths for query {}", query);
        return StreamSupport
            .stream(classLengthSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(classLengthMapper::toDto)
            .collect(Collectors.toList());
    }
}
