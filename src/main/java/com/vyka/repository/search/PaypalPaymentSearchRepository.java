package com.vyka.repository.search;

import com.vyka.domain.PaypalPayment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PaypalPayment entity.
 */
public interface PaypalPaymentSearchRepository extends ElasticsearchRepository<PaypalPayment, Long> {
}
