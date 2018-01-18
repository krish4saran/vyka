package com.vyka.repository;

import com.vyka.domain.OrderActivity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrderActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderActivityRepository extends JpaRepository<OrderActivity, Long> {

}
