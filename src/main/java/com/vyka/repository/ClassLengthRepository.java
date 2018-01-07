package com.vyka.repository;

import com.vyka.domain.ClassLength;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClassLength entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassLengthRepository extends JpaRepository<ClassLength, Long> {

}
