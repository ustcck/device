package com.lenovo.cloud.device.web.rest;

import com.lenovo.cloud.device.domain.Threshold;
import com.lenovo.cloud.device.service.ThresholdService;
import com.lenovo.cloud.device.web.rest.errors.BadRequestAlertException;
import com.lenovo.cloud.device.service.dto.ThresholdCriteria;
import com.lenovo.cloud.device.service.ThresholdQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lenovo.cloud.device.domain.Threshold}.
 */
@RestController
@RequestMapping("/api")
public class ThresholdResource {

    private final Logger log = LoggerFactory.getLogger(ThresholdResource.class);

    private static final String ENTITY_NAME = "threshold";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThresholdService thresholdService;

    private final ThresholdQueryService thresholdQueryService;

    public ThresholdResource(ThresholdService thresholdService, ThresholdQueryService thresholdQueryService) {
        this.thresholdService = thresholdService;
        this.thresholdQueryService = thresholdQueryService;
    }

    /**
     * {@code POST  /thresholds} : Create a new threshold.
     *
     * @param threshold the threshold to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new threshold, or with status {@code 400 (Bad Request)} if the threshold has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thresholds")
    public ResponseEntity<Threshold> createThreshold(@RequestBody Threshold threshold) throws URISyntaxException {
        log.debug("REST request to save Threshold : {}", threshold);
        if (threshold.getId() != null) {
            throw new BadRequestAlertException("A new threshold cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Threshold result = thresholdService.save(threshold);
        return ResponseEntity.created(new URI("/api/thresholds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thresholds} : Updates an existing threshold.
     *
     * @param threshold the threshold to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated threshold,
     * or with status {@code 400 (Bad Request)} if the threshold is not valid,
     * or with status {@code 500 (Internal Server Error)} if the threshold couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thresholds")
    public ResponseEntity<Threshold> updateThreshold(@RequestBody Threshold threshold) throws URISyntaxException {
        log.debug("REST request to update Threshold : {}", threshold);
        if (threshold.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Threshold result = thresholdService.save(threshold);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, threshold.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /thresholds} : get all the thresholds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thresholds in body.
     */
    @GetMapping("/thresholds")
    public ResponseEntity<List<Threshold>> getAllThresholds(ThresholdCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Thresholds by criteria: {}", criteria);
        Page<Threshold> page = thresholdQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /thresholds/count} : count all the thresholds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/thresholds/count")
    public ResponseEntity<Long> countThresholds(ThresholdCriteria criteria) {
        log.debug("REST request to count Thresholds by criteria: {}", criteria);
        return ResponseEntity.ok().body(thresholdQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /thresholds/:id} : get the "id" threshold.
     *
     * @param id the id of the threshold to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the threshold, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thresholds/{id}")
    public ResponseEntity<Threshold> getThreshold(@PathVariable Long id) {
        log.debug("REST request to get Threshold : {}", id);
        Optional<Threshold> threshold = thresholdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(threshold);
    }

    /**
     * {@code DELETE  /thresholds/:id} : delete the "id" threshold.
     *
     * @param id the id of the threshold to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thresholds/{id}")
    public ResponseEntity<Void> deleteThreshold(@PathVariable Long id) {
        log.debug("REST request to delete Threshold : {}", id);
        thresholdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
