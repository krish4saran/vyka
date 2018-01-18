package com.vyka.repository.search;

import com.vyka.domain.Schedule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Schedule entity.
 */
public interface ScheduleSearchRepository extends ElasticsearchRepository<Schedule, Long> {
}
