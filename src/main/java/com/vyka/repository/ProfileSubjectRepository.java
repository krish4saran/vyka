package com.vyka.repository;

import com.vyka.domain.ProfileSubject;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProfileSubject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileSubjectRepository extends JpaRepository<ProfileSubject, Long> {

}
