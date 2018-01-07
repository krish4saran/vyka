package com.vyka.repository.search;

import com.vyka.domain.Award;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Award entity.
 */
public interface AwardSearchRepository extends ElasticsearchRepository<Award, Long> {
}
