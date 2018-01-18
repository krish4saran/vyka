package com.vyka.repository;

import com.vyka.domain.Settlement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Settlement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

}
