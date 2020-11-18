package com.lenovo.cloud.device.repository;

import com.lenovo.cloud.device.domain.Threshold;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Threshold entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThresholdRepository extends JpaRepository<Threshold, Long>, JpaSpecificationExecutor<Threshold> {
}
