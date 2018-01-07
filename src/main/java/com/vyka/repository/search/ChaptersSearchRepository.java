package com.vyka.repository.search;

import com.vyka.domain.Chapters;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Chapters entity.
 */
public interface ChaptersSearchRepository extends ElasticsearchRepository<Chapters, Long> {
}
