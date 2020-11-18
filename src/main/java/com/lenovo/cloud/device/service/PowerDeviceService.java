package com.lenovo.cloud.device.service;

import com.lenovo.cloud.device.domain.PowerDevice;
import com.lenovo.cloud.device.repository.PowerDeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PowerDevice}.
 */
@Service
@Transactional
public class PowerDeviceService {

    private final Logger log = LoggerFactory.getLogger(PowerDeviceService.class);

    private final PowerDeviceRepository powerDeviceRepository;

    public PowerDeviceService(PowerDeviceRepository powerDeviceRepository) {
        this.powerDeviceRepository = powerDeviceRepository;
    }

    /**
     * Save a powerDevice.
     *
     * @param powerDevice the entity to save.
     * @return the persisted entity.
     */
    public PowerDevice save(PowerDevice powerDevice) {
        log.debug("Request to save PowerDevice : {}", powerDevice);
        return powerDeviceRepository.save(powerDevice);
    }

    /**
     * Get all the powerDevices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PowerDevice> findAll(Pageable pageable) {
        log.debug("Request to get all PowerDevices");
        return powerDeviceRepository.findAll(pageable);
    }


    /**
     * Get one powerDevice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PowerDevice> findOne(Long id) {
        log.debug("Request to get PowerDevice : {}", id);
        return powerDeviceRepository.findById(id);
    }

    /**
     * Delete the powerDevice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PowerDevice : {}", id);
        powerDeviceRepository.deleteById(id);
    }
}
