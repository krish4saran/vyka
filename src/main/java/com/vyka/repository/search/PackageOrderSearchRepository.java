package com.vyka.repository.search;

import com.vyka.domain.PackageOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PackageOrder entity.
 */
public interface PackageOrderSearchRepository extends ElasticsearchRepository<PackageOrder, Long> {
}
