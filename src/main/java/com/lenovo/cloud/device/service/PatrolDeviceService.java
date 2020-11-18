package com.lenovo.cloud.device.service;

import com.lenovo.cloud.device.domain.PatrolDevice;
import com.lenovo.cloud.device.repository.PatrolDeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PatrolDevice}.
 */
@Service
@Transactional
public class PatrolDeviceService {

    private final Logger log = LoggerFactory.getLogger(PatrolDeviceService.class);

    private final PatrolDeviceRepository patrolDeviceRepository;

    public PatrolDeviceService(PatrolDeviceRepository patrolDeviceRepository) {
        this.patrolDeviceRepository = patrolDeviceRepository;
    }

    /**
     * Save a patrolDevice.
     *
     * @param patrolDevice the entity to save.
     * @return the persisted entity.
     */
    public PatrolDevice save(PatrolDevice patrolDevice) {
        log.debug("Request to save PatrolDevice : {}", patrolDevice);
        return patrolDeviceRepository.save(patrolDevice);
    }

    /**
     * Get all the patrolDevices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PatrolDevice> findAll(Pageable pageable) {
        log.debug("Request to get all PatrolDevices");
        return patrolDeviceRepository.findAll(pageable);
    }


    /**
     * Get all the patrolDevices with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PatrolDevice> findAllWithEagerRelationships(Pageable pageable) {
        return patrolDeviceRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one patrolDevice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PatrolDevice> findOne(Long id) {
        log.debug("Request to get PatrolDevice : {}", id);
        return patrolDeviceRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the patrolDevice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PatrolDevice : {}", id);
        patrolDeviceRepository.deleteById(id);
    }
}
