package com.lenovo.cloud.device.web.rest;

import com.lenovo.cloud.device.domain.PatrolDevice;
import com.lenovo.cloud.device.service.PatrolDeviceService;
import com.lenovo.cloud.device.web.rest.errors.BadRequestAlertException;
import com.lenovo.cloud.device.service.dto.PatrolDeviceCriteria;
import com.lenovo.cloud.device.service.PatrolDeviceQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lenovo.cloud.device.domain.PatrolDevice}.
 */
@RestController
@RequestMapping("/api")
public class PatrolDeviceResource {

    private final Logger log = LoggerFactory.getLogger(PatrolDeviceResource.class);

    private static final String ENTITY_NAME = "patrolDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatrolDeviceService patrolDeviceService;

    private final PatrolDeviceQueryService patrolDeviceQueryService;

    public PatrolDeviceResource(PatrolDeviceService patrolDeviceService, PatrolDeviceQueryService patrolDeviceQueryService) {
        this.patrolDeviceService = patrolDeviceService;
        this.patrolDeviceQueryService = patrolDeviceQueryService;
    }

    /**
     * {@code POST  /patrol-devices} : Create a new patrolDevice.
     *
     * @param patrolDevice the patrolDevice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patrolDevice, or with status {@code 400 (Bad Request)} if the patrolDevice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patrol-devices")
    public ResponseEntity<PatrolDevice> createPatrolDevice(@Valid @RequestBody PatrolDevice patrolDevice) throws URISyntaxException {
        log.debug("REST request to save PatrolDevice : {}", patrolDevice);
        if (patrolDevice.getId() != null) {
            throw new BadRequestAlertException("A new patrolDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatrolDevice result = patrolDeviceService.save(patrolDevice);
        return ResponseEntity.created(new URI("/api/patrol-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patrol-devices} : Updates an existing patrolDevice.
     *
     * @param patrolDevice the patrolDevice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patrolDevice,
     * or with status {@code 400 (Bad Request)} if the patrolDevice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patrolDevice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patrol-devices")
    public ResponseEntity<PatrolDevice> updatePatrolDevice(@Valid @RequestBody PatrolDevice patrolDevice) throws URISyntaxException {
        log.debug("REST request to update PatrolDevice : {}", patrolDevice);
        if (patrolDevice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatrolDevice result = patrolDeviceService.save(patrolDevice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patrolDevice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patrol-devices} : get all the patrolDevices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patrolDevices in body.
     */
    @GetMapping("/patrol-devices")
    public ResponseEntity<List<PatrolDevice>> getAllPatrolDevices(PatrolDeviceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PatrolDevices by criteria: {}", criteria);
        Page<PatrolDevice> page = patrolDeviceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /patrol-devices/count} : count all the patrolDevices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/patrol-devices/count")
    public ResponseEntity<Long> countPatrolDevices(PatrolDeviceCriteria criteria) {
        log.debug("REST request to count PatrolDevices by criteria: {}", criteria);
        return ResponseEntity.ok().body(patrolDeviceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /patrol-devices/:id} : get the "id" patrolDevice.
     *
     * @param id the id of the patrolDevice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patrolDevice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patrol-devices/{id}")
    public ResponseEntity<PatrolDevice> getPatrolDevice(@PathVariable Long id) {
        log.debug("REST request to get PatrolDevice : {}", id);
        Optional<PatrolDevice> patrolDevice = patrolDeviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patrolDevice);
    }

    /**
     * {@code DELETE  /patrol-devices/:id} : delete the "id" patrolDevice.
     *
     * @param id the id of the patrolDevice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patrol-devices/{id}")
    public ResponseEntity<Void> deletePatrolDevice(@PathVariable Long id) {
        log.debug("REST request to delete PatrolDevice : {}", id);
        patrolDeviceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
