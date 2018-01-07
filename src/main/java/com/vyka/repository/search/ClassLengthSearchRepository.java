package com.vyka.repository.search;

import com.vyka.domain.ClassLength;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ClassLength entity.
 */
public interface ClassLengthSearchRepository extends ElasticsearchRepository<ClassLength, Long> {
}
