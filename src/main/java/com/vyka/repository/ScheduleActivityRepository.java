package com.vyka.repository;

import com.vyka.domain.ScheduleActivity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ScheduleActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleActivityRepository extends JpaRepository<ScheduleActivity, Long> {

}
