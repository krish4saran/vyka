package com.vyka.repository.search;

import com.vyka.domain.ScheduleActivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ScheduleActivity entity.
 */
public interface ScheduleActivitySearchRepository extends ElasticsearchRepository<ScheduleActivity, Long> {
}
