package com.lenovo.cloud.device.web.rest;

import com.lenovo.cloud.device.DeviceApp;
import com.lenovo.cloud.device.domain.Threshold;
import com.lenovo.cloud.device.repository.ThresholdRepository;
import com.lenovo.cloud.device.service.ThresholdService;
import com.lenovo.cloud.device.service.dto.ThresholdCriteria;
import com.lenovo.cloud.device.service.ThresholdQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ThresholdResource} REST controller.
 */
@SpringBootTest(classes = DeviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ThresholdResourceIT {

    private static final Double DEFAULT_LOW_LIMIT = 1D;
    private static final Double UPDATED_LOW_LIMIT = 2D;
    private static final Double SMALLER_LOW_LIMIT = 1D - 1D;

    private static final Double DEFAULT_HIGH_LIMIT = 1D;
    private static final Double UPDATED_HIGH_LIMIT = 2D;
    private static final Double SMALLER_HIGH_LIMIT = 1D - 1D;

    private static final Integer DEFAULT_OPEN_CLOSE_INDICATOR = 1;
    private static final Integer UPDATED_OPEN_CLOSE_INDICATOR = 2;
    private static final Integer SMALLER_OPEN_CLOSE_INDICATOR = 1 - 1;

    @Autowired
    private ThresholdRepository thresholdRepository;

    @Autowired
    private ThresholdService thresholdService;

    @Autowired
    private ThresholdQueryService thresholdQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThresholdMockMvc;

    private Threshold threshold;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Threshold createEntity(EntityManager em) {
        Threshold threshold = new Threshold()
            .lowLimit(DEFAULT_LOW_LIMIT)
            .highLimit(DEFAULT_HIGH_LIMIT)
            .openCloseIndicator(DEFAULT_OPEN_CLOSE_INDICATOR);
        return threshold;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Threshold createUpdatedEntity(EntityManager em) {
        Threshold threshold = new Threshold()
            .lowLimit(UPDATED_LOW_LIMIT)
            .highLimit(UPDATED_HIGH_LIMIT)
            .openCloseIndicator(UPDATED_OPEN_CLOSE_INDICATOR);
        return threshold;
    }

    @BeforeEach
    public void initTest() {
        threshold = createEntity(em);
    }

    @Test
    @Transactional
    public void createThreshold() throws Exception {
        int databaseSizeBeforeCreate = thresholdRepository.findAll().size();
        // Create the Threshold
        restThresholdMockMvc.perform(post("/api/thresholds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(threshold)))
            .andExpect(status().isCreated());

        // Validate the Threshold in the database
        List<Threshold> thresholdList = thresholdRepository.findAll();
        assertThat(thresholdList).hasSize(databaseSizeBeforeCreate + 1);
        Threshold testThreshold = thresholdList.get(thresholdList.size() - 1);
        assertThat(testThreshold.getLowLimit()).isEqualTo(DEFAULT_LOW_LIMIT);
        assertThat(testThreshold.getHighLimit()).isEqualTo(DEFAULT_HIGH_LIMIT);
        assertThat(testThreshold.getOpenCloseIndicator()).isEqualTo(DEFAULT_OPEN_CLOSE_INDICATOR);
    }

    @Test
    @Transactional
    public void createThresholdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thresholdRepository.findAll().size();

        // Create the Threshold with an existing ID
        threshold.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThresholdMockMvc.perform(post("/api/thresholds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(threshold)))
            .andExpect(status().isBadRequest());

        // Validate the Threshold in the database
        List<Threshold> thresholdList = thresholdRepository.findAll();
        assertThat(thresholdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllThresholds() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList
        restThresholdMockMvc.perform(get("/api/thresholds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(threshold.getId().intValue())))
            .andExpect(jsonPath("$.[*].lowLimit").value(hasItem(DEFAULT_LOW_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].highLimit").value(hasItem(DEFAULT_HIGH_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].openCloseIndicator").value(hasItem(DEFAULT_OPEN_CLOSE_INDICATOR)));
    }
    
    @Test
    @Transactional
    public void getThreshold() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get the threshold
        restThresholdMockMvc.perform(get("/api/thresholds/{id}", threshold.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(threshold.getId().intValue()))
            .andExpect(jsonPath("$.lowLimit").value(DEFAULT_LOW_LIMIT.doubleValue()))
            .andExpect(jsonPath("$.highLimit").value(DEFAULT_HIGH_LIMIT.doubleValue()))
            .andExpect(jsonPath("$.openCloseIndicator").value(DEFAULT_OPEN_CLOSE_INDICATOR));
    }


    @Test
    @Transactional
    public void getThresholdsByIdFiltering() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        Long id = threshold.getId();

        defaultThresholdShouldBeFound("id.equals=" + id);
        defaultThresholdShouldNotBeFound("id.notEquals=" + id);

        defaultThresholdShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultThresholdShouldNotBeFound("id.greaterThan=" + id);

        defaultThresholdShouldBeFound("id.lessThanOrEqual=" + id);
        defaultThresholdShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllThresholdsByLowLimitIsEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where lowLimit equals to DEFAULT_LOW_LIMIT
        defaultThresholdShouldBeFound("lowLimit.equals=" + DEFAULT_LOW_LIMIT);

        // Get all the thresholdList where lowLimit equals to UPDATED_LOW_LIMIT
        defaultThresholdShouldNotBeFound("lowLimit.equals=" + UPDATED_LOW_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByLowLimitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where lowLimit not equals to DEFAULT_LOW_LIMIT
        defaultThresholdShouldNotBeFound("lowLimit.notEquals=" + DEFAULT_LOW_LIMIT);

        // Get all the thresholdList where lowLimit not equals to UPDATED_LOW_LIMIT
        defaultThresholdShouldBeFound("lowLimit.notEquals=" + UPDATED_LOW_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByLowLimitIsInShouldWork() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where lowLimit in DEFAULT_LOW_LIMIT or UPDATED_LOW_LIMIT
        defaultThresholdShouldBeFound("lowLimit.in=" + DEFAULT_LOW_LIMIT + "," + UPDATED_LOW_LIMIT);

        // Get all the thresholdList where lowLimit equals to UPDATED_LOW_LIMIT
        defaultThresholdShouldNotBeFound("lowLimit.in=" + UPDATED_LOW_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByLowLimitIsNullOrNotNull() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where lowLimit is not null
        defaultThresholdShouldBeFound("lowLimit.specified=true");

        // Get all the thresholdList where lowLimit is null
        defaultThresholdShouldNotBeFound("lowLimit.specified=false");
    }

    @Test
    @Transactional
    public void getAllThresholdsByLowLimitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where lowLimit is greater than or equal to DEFAULT_LOW_LIMIT
        defaultThresholdShouldBeFound("lowLimit.greaterThanOrEqual=" + DEFAULT_LOW_LIMIT);

        // Get all the thresholdList where lowLimit is greater than or equal to UPDATED_LOW_LIMIT
        defaultThresholdShouldNotBeFound("lowLimit.greaterThanOrEqual=" + UPDATED_LOW_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByLowLimitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where lowLimit is less than or equal to DEFAULT_LOW_LIMIT
        defaultThresholdShouldBeFound("lowLimit.lessThanOrEqual=" + DEFAULT_LOW_LIMIT);

        // Get all the thresholdList where lowLimit is less than or equal to SMALLER_LOW_LIMIT
        defaultThresholdShouldNotBeFound("lowLimit.lessThanOrEqual=" + SMALLER_LOW_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByLowLimitIsLessThanSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where lowLimit is less than DEFAULT_LOW_LIMIT
        defaultThresholdShouldNotBeFound("lowLimit.lessThan=" + DEFAULT_LOW_LIMIT);

        // Get all the thresholdList where lowLimit is less than UPDATED_LOW_LIMIT
        defaultThresholdShouldBeFound("lowLimit.lessThan=" + UPDATED_LOW_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByLowLimitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where lowLimit is greater than DEFAULT_LOW_LIMIT
        defaultThresholdShouldNotBeFound("lowLimit.greaterThan=" + DEFAULT_LOW_LIMIT);

        // Get all the thresholdList where lowLimit is greater than SMALLER_LOW_LIMIT
        defaultThresholdShouldBeFound("lowLimit.greaterThan=" + SMALLER_LOW_LIMIT);
    }


    @Test
    @Transactional
    public void getAllThresholdsByHighLimitIsEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where highLimit equals to DEFAULT_HIGH_LIMIT
        defaultThresholdShouldBeFound("highLimit.equals=" + DEFAULT_HIGH_LIMIT);

        // Get all the thresholdList where highLimit equals to UPDATED_HIGH_LIMIT
        defaultThresholdShouldNotBeFound("highLimit.equals=" + UPDATED_HIGH_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByHighLimitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where highLimit not equals to DEFAULT_HIGH_LIMIT
        defaultThresholdShouldNotBeFound("highLimit.notEquals=" + DEFAULT_HIGH_LIMIT);

        // Get all the thresholdList where highLimit not equals to UPDATED_HIGH_LIMIT
        defaultThresholdShouldBeFound("highLimit.notEquals=" + UPDATED_HIGH_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByHighLimitIsInShouldWork() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where highLimit in DEFAULT_HIGH_LIMIT or UPDATED_HIGH_LIMIT
        defaultThresholdShouldBeFound("highLimit.in=" + DEFAULT_HIGH_LIMIT + "," + UPDATED_HIGH_LIMIT);

        // Get all the thresholdList where highLimit equals to UPDATED_HIGH_LIMIT
        defaultThresholdShouldNotBeFound("highLimit.in=" + UPDATED_HIGH_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByHighLimitIsNullOrNotNull() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where highLimit is not null
        defaultThresholdShouldBeFound("highLimit.specified=true");

        // Get all the thresholdList where highLimit is null
        defaultThresholdShouldNotBeFound("highLimit.specified=false");
    }

    @Test
    @Transactional
    public void getAllThresholdsByHighLimitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where highLimit is greater than or equal to DEFAULT_HIGH_LIMIT
        defaultThresholdShouldBeFound("highLimit.greaterThanOrEqual=" + DEFAULT_HIGH_LIMIT);

        // Get all the thresholdList where highLimit is greater than or equal to UPDATED_HIGH_LIMIT
        defaultThresholdShouldNotBeFound("highLimit.greaterThanOrEqual=" + UPDATED_HIGH_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByHighLimitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where highLimit is less than or equal to DEFAULT_HIGH_LIMIT
        defaultThresholdShouldBeFound("highLimit.lessThanOrEqual=" + DEFAULT_HIGH_LIMIT);

        // Get all the thresholdList where highLimit is less than or equal to SMALLER_HIGH_LIMIT
        defaultThresholdShouldNotBeFound("highLimit.lessThanOrEqual=" + SMALLER_HIGH_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByHighLimitIsLessThanSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where highLimit is less than DEFAULT_HIGH_LIMIT
        defaultThresholdShouldNotBeFound("highLimit.lessThan=" + DEFAULT_HIGH_LIMIT);

        // Get all the thresholdList where highLimit is less than UPDATED_HIGH_LIMIT
        defaultThresholdShouldBeFound("highLimit.lessThan=" + UPDATED_HIGH_LIMIT);
    }

    @Test
    @Transactional
    public void getAllThresholdsByHighLimitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where highLimit is greater than DEFAULT_HIGH_LIMIT
        defaultThresholdShouldNotBeFound("highLimit.greaterThan=" + DEFAULT_HIGH_LIMIT);

        // Get all the thresholdList where highLimit is greater than SMALLER_HIGH_LIMIT
        defaultThresholdShouldBeFound("highLimit.greaterThan=" + SMALLER_HIGH_LIMIT);
    }


    @Test
    @Transactional
    public void getAllThresholdsByOpenCloseIndicatorIsEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where openCloseIndicator equals to DEFAULT_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldBeFound("openCloseIndicator.equals=" + DEFAULT_OPEN_CLOSE_INDICATOR);

        // Get all the thresholdList where openCloseIndicator equals to UPDATED_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldNotBeFound("openCloseIndicator.equals=" + UPDATED_OPEN_CLOSE_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllThresholdsByOpenCloseIndicatorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where openCloseIndicator not equals to DEFAULT_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldNotBeFound("openCloseIndicator.notEquals=" + DEFAULT_OPEN_CLOSE_INDICATOR);

        // Get all the thresholdList where openCloseIndicator not equals to UPDATED_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldBeFound("openCloseIndicator.notEquals=" + UPDATED_OPEN_CLOSE_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllThresholdsByOpenCloseIndicatorIsInShouldWork() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where openCloseIndicator in DEFAULT_OPEN_CLOSE_INDICATOR or UPDATED_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldBeFound("openCloseIndicator.in=" + DEFAULT_OPEN_CLOSE_INDICATOR + "," + UPDATED_OPEN_CLOSE_INDICATOR);

        // Get all the thresholdList where openCloseIndicator equals to UPDATED_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldNotBeFound("openCloseIndicator.in=" + UPDATED_OPEN_CLOSE_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllThresholdsByOpenCloseIndicatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where openCloseIndicator is not null
        defaultThresholdShouldBeFound("openCloseIndicator.specified=true");

        // Get all the thresholdList where openCloseIndicator is null
        defaultThresholdShouldNotBeFound("openCloseIndicator.specified=false");
    }

    @Test
    @Transactional
    public void getAllThresholdsByOpenCloseIndicatorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where openCloseIndicator is greater than or equal to DEFAULT_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldBeFound("openCloseIndicator.greaterThanOrEqual=" + DEFAULT_OPEN_CLOSE_INDICATOR);

        // Get all the thresholdList where openCloseIndicator is greater than or equal to UPDATED_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldNotBeFound("openCloseIndicator.greaterThanOrEqual=" + UPDATED_OPEN_CLOSE_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllThresholdsByOpenCloseIndicatorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where openCloseIndicator is less than or equal to DEFAULT_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldBeFound("openCloseIndicator.lessThanOrEqual=" + DEFAULT_OPEN_CLOSE_INDICATOR);

        // Get all the thresholdList where openCloseIndicator is less than or equal to SMALLER_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldNotBeFound("openCloseIndicator.lessThanOrEqual=" + SMALLER_OPEN_CLOSE_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllThresholdsByOpenCloseIndicatorIsLessThanSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where openCloseIndicator is less than DEFAULT_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldNotBeFound("openCloseIndicator.lessThan=" + DEFAULT_OPEN_CLOSE_INDICATOR);

        // Get all the thresholdList where openCloseIndicator is less than UPDATED_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldBeFound("openCloseIndicator.lessThan=" + UPDATED_OPEN_CLOSE_INDICATOR);
    }

    @Test
    @Transactional
    public void getAllThresholdsByOpenCloseIndicatorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        thresholdRepository.saveAndFlush(threshold);

        // Get all the thresholdList where openCloseIndicator is greater than DEFAULT_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldNotBeFound("openCloseIndicator.greaterThan=" + DEFAULT_OPEN_CLOSE_INDICATOR);

        // Get all the thresholdList where openCloseIndicator is greater than SMALLER_OPEN_CLOSE_INDICATOR
        defaultThresholdShouldBeFound("openCloseIndicator.greaterThan=" + SMALLER_OPEN_CLOSE_INDICATOR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultThresholdShouldBeFound(String filter) throws Exception {
        restThresholdMockMvc.perform(get("/api/thresholds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(threshold.getId().intValue())))
            .andExpect(jsonPath("$.[*].lowLimit").value(hasItem(DEFAULT_LOW_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].highLimit").value(hasItem(DEFAULT_HIGH_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].openCloseIndicator").value(hasItem(DEFAULT_OPEN_CLOSE_INDICATOR)));

        // Check, that the count call also returns 1
        restThresholdMockMvc.perform(get("/api/thresholds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultThresholdShouldNotBeFound(String filter) throws Exception {
        restThresholdMockMvc.perform(get("/api/thresholds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restThresholdMockMvc.perform(get("/api/thresholds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingThreshold() throws Exception {
        // Get the threshold
        restThresholdMockMvc.perform(get("/api/thresholds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThreshold() throws Exception {
        // Initialize the database
        thresholdService.save(threshold);

        int databaseSizeBeforeUpdate = thresholdRepository.findAll().size();

        // Update the threshold
        Threshold updatedThreshold = thresholdRepository.findById(threshold.getId()).get();
        // Disconnect from session so that the updates on updatedThreshold are not directly saved in db
        em.detach(updatedThreshold);
        updatedThreshold
            .lowLimit(UPDATED_LOW_LIMIT)
            .highLimit(UPDATED_HIGH_LIMIT)
            .openCloseIndicator(UPDATED_OPEN_CLOSE_INDICATOR);

        restThresholdMockMvc.perform(put("/api/thresholds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedThreshold)))
            .andExpect(status().isOk());

        // Validate the Threshold in the database
        List<Threshold> thresholdList = thresholdRepository.findAll();
        assertThat(thresholdList).hasSize(databaseSizeBeforeUpdate);
        Threshold testThreshold = thresholdList.get(thresholdList.size() - 1);
        assertThat(testThreshold.getLowLimit()).isEqualTo(UPDATED_LOW_LIMIT);
        assertThat(testThreshold.getHighLimit()).isEqualTo(UPDATED_HIGH_LIMIT);
        assertThat(testThreshold.getOpenCloseIndicator()).isEqualTo(UPDATED_OPEN_CLOSE_INDICATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingThreshold() throws Exception {
        int databaseSizeBeforeUpdate = thresholdRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThresholdMockMvc.perform(put("/api/thresholds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(threshold)))
            .andExpect(status().isBadRequest());

        // Validate the Threshold in the database
        List<Threshold> thresholdList = thresholdRepository.findAll();
        assertThat(thresholdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThreshold() throws Exception {
        // Initialize the database
        thresholdService.save(threshold);

        int databaseSizeBeforeDelete = thresholdRepository.findAll().size();

        // Delete the threshold
        restThresholdMockMvc.perform(delete("/api/thresholds/{id}", threshold.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Threshold> thresholdList = thresholdRepository.findAll();
        assertThat(thresholdList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
