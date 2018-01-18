package com.vyka.repository;

import com.vyka.domain.PackageOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PackageOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackageOrderRepository extends JpaRepository<PackageOrder, Long> {

}
