package com.vyka.repository;

import com.vyka.domain.SubjectLevel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SubjectLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectLevelRepository extends JpaRepository<SubjectLevel, Long> {

}
