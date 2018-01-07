package com.vyka.repository;

import com.vyka.domain.Award;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Award entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {

}
