package com.vyka.repository;

import com.vyka.domain.Level;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Level entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

}
