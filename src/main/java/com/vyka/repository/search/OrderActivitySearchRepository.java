package com.vyka.repository.search;

import com.vyka.domain.OrderActivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderActivity entity.
 */
public interface OrderActivitySearchRepository extends ElasticsearchRepository<OrderActivity, Long> {
}
