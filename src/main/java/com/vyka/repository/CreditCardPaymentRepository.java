package com.vyka.repository;

import com.vyka.domain.CreditCardPayment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CreditCardPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditCardPaymentRepository extends JpaRepository<CreditCardPayment, Long> {

}
