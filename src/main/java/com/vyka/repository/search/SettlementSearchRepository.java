package com.vyka.repository.search;

import com.vyka.domain.Settlement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Settlement entity.
 */
public interface SettlementSearchRepository extends ElasticsearchRepository<Settlement, Long> {
}
