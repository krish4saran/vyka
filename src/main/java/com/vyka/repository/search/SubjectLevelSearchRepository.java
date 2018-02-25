package com.vyka.repository.search;

import com.vyka.domain.SubjectLevel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SubjectLevel entity.
 */
public interface SubjectLevelSearchRepository extends ElasticsearchRepository<SubjectLevel, Long> {
}
