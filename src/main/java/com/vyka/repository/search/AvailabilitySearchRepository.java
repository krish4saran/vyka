package com.vyka.repository.search;

import com.vyka.domain.Availability;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Availability entity.
 */
public interface AvailabilitySearchRepository extends ElasticsearchRepository<Availability, Long> {
}
