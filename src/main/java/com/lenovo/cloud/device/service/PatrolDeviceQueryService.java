package com.lenovo.cloud.device.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.lenovo.cloud.device.domain.PatrolDevice;
import com.lenovo.cloud.device.domain.*; // for static metamodels
import com.lenovo.cloud.device.repository.PatrolDeviceRepository;
import com.lenovo.cloud.device.service.dto.PatrolDeviceCriteria;

/**
 * Service for executing complex queries for {@link PatrolDevice} entities in the database.
 * The main input is a {@link PatrolDeviceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PatrolDevice} or a {@link Page} of {@link PatrolDevice} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatrolDeviceQueryService extends QueryService<PatrolDevice> {

    private final Logger log = LoggerFactory.getLogger(PatrolDeviceQueryService.class);

    private final PatrolDeviceRepository patrolDeviceRepository;

    public PatrolDeviceQueryService(PatrolDeviceRepository patrolDeviceRepository) {
        this.patrolDeviceRepository = patrolDeviceRepository;
    }

    /**
     * Return a {@link List} of {@link PatrolDevice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PatrolDevice> findByCriteria(PatrolDeviceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PatrolDevice> specification = createSpecification(criteria);
        return patrolDeviceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PatrolDevice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PatrolDevice> findByCriteria(PatrolDeviceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PatrolDevice> specification = createSpecification(criteria);
        return patrolDeviceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PatrolDeviceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PatrolDevice> specification = createSpecification(criteria);
        return patrolDeviceRepository.count(specification);
    }

    /**
     * Function to convert {@link PatrolDeviceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PatrolDevice> createSpecification(PatrolDeviceCriteria criteria) {
        Specification<PatrolDevice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PatrolDevice_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PatrolDevice_.name));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), PatrolDevice_.source));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), PatrolDevice_.serialNumber));
            }
            if (criteria.getInstallDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstallDate(), PatrolDevice_.installDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), PatrolDevice_.status));
            }
            if (criteria.getDeviceModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeviceModel(), PatrolDevice_.deviceModel));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), PatrolDevice_.createTime));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), PatrolDevice_.updateTime));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), PatrolDevice_.remark));
            }
            if (criteria.getPowerDeviceId() != null) {
                specification = specification.and(buildSpecification(criteria.getPowerDeviceId(),
                    root -> root.join(PatrolDevice_.powerDevices, JoinType.LEFT).get(PowerDevice_.id)));
            }
        }
        return specification;
    }
}
