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

import com.lenovo.cloud.device.domain.Threshold;
import com.lenovo.cloud.device.domain.*; // for static metamodels
import com.lenovo.cloud.device.repository.ThresholdRepository;
import com.lenovo.cloud.device.service.dto.ThresholdCriteria;

/**
 * Service for executing complex queries for {@link Threshold} entities in the database.
 * The main input is a {@link ThresholdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Threshold} or a {@link Page} of {@link Threshold} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ThresholdQueryService extends QueryService<Threshold> {

    private final Logger log = LoggerFactory.getLogger(ThresholdQueryService.class);

    private final ThresholdRepository thresholdRepository;

    public ThresholdQueryService(ThresholdRepository thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    /**
     * Return a {@link List} of {@link Threshold} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Threshold> findByCriteria(ThresholdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Threshold> specification = createSpecification(criteria);
        return thresholdRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Threshold} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Threshold> findByCriteria(ThresholdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Threshold> specification = createSpecification(criteria);
        return thresholdRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ThresholdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Threshold> specification = createSpecification(criteria);
        return thresholdRepository.count(specification);
    }

    /**
     * Function to convert {@link ThresholdCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Threshold> createSpecification(ThresholdCriteria criteria) {
        Specification<Threshold> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Threshold_.id));
            }
            if (criteria.getLowLimit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLowLimit(), Threshold_.lowLimit));
            }
            if (criteria.getHighLimit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHighLimit(), Threshold_.highLimit));
            }
            if (criteria.getOpenCloseIndicator() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOpenCloseIndicator(), Threshold_.openCloseIndicator));
            }
        }
        return specification;
    }
}
