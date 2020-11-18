package com.lenovo.cloud.device.service;

import com.lenovo.cloud.device.domain.Threshold;
import com.lenovo.cloud.device.repository.ThresholdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Threshold}.
 */
@Service
@Transactional
public class ThresholdService {

    private final Logger log = LoggerFactory.getLogger(ThresholdService.class);

    private final ThresholdRepository thresholdRepository;

    public ThresholdService(ThresholdRepository thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    /**
     * Save a threshold.
     *
     * @param threshold the entity to save.
     * @return the persisted entity.
     */
    public Threshold save(Threshold threshold) {
        log.debug("Request to save Threshold : {}", threshold);
        return thresholdRepository.save(threshold);
    }

    /**
     * Get all the thresholds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Threshold> findAll(Pageable pageable) {
        log.debug("Request to get all Thresholds");
        return thresholdRepository.findAll(pageable);
    }


    /**
     * Get one threshold by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Threshold> findOne(Long id) {
        log.debug("Request to get Threshold : {}", id);
        return thresholdRepository.findById(id);
    }

    /**
     * Delete the threshold by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Threshold : {}", id);
        thresholdRepository.deleteById(id);
    }
}
