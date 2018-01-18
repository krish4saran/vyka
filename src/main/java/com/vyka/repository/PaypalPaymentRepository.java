package com.vyka.repository;

import com.vyka.domain.PaypalPayment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PaypalPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaypalPaymentRepository extends JpaRepository<PaypalPayment, Long> {

}
