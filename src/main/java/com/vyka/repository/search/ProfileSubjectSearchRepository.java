package com.vyka.repository.search;

import com.vyka.domain.ProfileSubject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProfileSubject entity.
 */
public interface ProfileSubjectSearchRepository extends ElasticsearchRepository<ProfileSubject, Long> {
}
