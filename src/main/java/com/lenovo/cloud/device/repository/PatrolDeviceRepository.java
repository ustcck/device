package com.lenovo.cloud.device.repository;

import com.lenovo.cloud.device.domain.PatrolDevice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PatrolDevice entity.
 */
@Repository
public interface PatrolDeviceRepository extends JpaRepository<PatrolDevice, Long>, JpaSpecificationExecutor<PatrolDevice> {

    @Query(value = "select distinct patrolDevice from PatrolDevice patrolDevice left join fetch patrolDevice.powerDevices",
        countQuery = "select count(distinct patrolDevice) from PatrolDevice patrolDevice")
    Page<PatrolDevice> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct patrolDevice from PatrolDevice patrolDevice left join fetch patrolDevice.powerDevices")
    List<PatrolDevice> findAllWithEagerRelationships();

    @Query("select patrolDevice from PatrolDevice patrolDevice left join fetch patrolDevice.powerDevices where patrolDevice.id =:id")
    Optional<PatrolDevice> findOneWithEagerRelationships(@Param("id") Long id);
}
