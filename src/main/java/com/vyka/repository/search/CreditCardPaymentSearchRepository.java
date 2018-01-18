package com.vyka.repository.search;

import com.vyka.domain.CreditCardPayment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CreditCardPayment entity.
 */
public interface CreditCardPaymentSearchRepository extends ElasticsearchRepository<CreditCardPayment, Long> {
}
