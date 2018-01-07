package com.vyka.repository;

import com.vyka.domain.Chapters;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Chapters entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChaptersRepository extends JpaRepository<Chapters, Long> {

}
