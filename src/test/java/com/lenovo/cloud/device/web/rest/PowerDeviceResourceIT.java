package com.lenovo.cloud.device.web.rest;

import com.lenovo.cloud.device.DeviceApp;
import com.lenovo.cloud.device.domain.PowerDevice;
import com.lenovo.cloud.device.domain.Threshold;
import com.lenovo.cloud.device.domain.PatrolDevice;
import com.lenovo.cloud.device.repository.PowerDeviceRepository;
import com.lenovo.cloud.device.service.PowerDeviceService;
import com.lenovo.cloud.device.service.dto.PowerDeviceCriteria;
import com.lenovo.cloud.device.service.PowerDeviceQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PowerDeviceResource} REST controller.
 */
@SpringBootTest(classes = DeviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PowerDeviceResourceIT {

    private static final Integer DEFAULT_AREA = 1;
    private static final Integer UPDATED_AREA = 2;
    private static final Integer SMALLER_AREA = 1 - 1;

    private static final String DEFAULT_SPACE = "AAAAAAAAAA";
    private static final String UPDATED_SPACE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_PART = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_PART = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_PART = "AAAAAAAAAA";
    private static final String UPDATED_SUB_PART = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_RECOGNIZE_TYPE = 1;
    private static final Integer UPDATED_RECOGNIZE_TYPE = 2;
    private static final Integer SMALLER_RECOGNIZE_TYPE = 1 - 1;

    private static final Integer DEFAULT_RECOGNIZE_CONTENT = 1;
    private static final Integer UPDATED_RECOGNIZE_CONTENT = 2;
    private static final Integer SMALLER_RECOGNIZE_CONTENT = 1 - 1;

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_LINE = "AAAAAAAAAA";
    private static final String UPDATED_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INSTALL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INSTALL_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INSTALL_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String DEFAULT_DEVICE_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_MODEL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATE_TIME = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_UPDATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_UPDATE_TIME = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private PowerDeviceRepository powerDeviceRepository;

    @Autowired
    private PowerDeviceService powerDeviceService;

    @Autowired
    private PowerDeviceQueryService powerDeviceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPowerDeviceMockMvc;

    private PowerDevice powerDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerDevice createEntity(EntityManager em) {
        PowerDevice powerDevice = new PowerDevice()
            .area(DEFAULT_AREA)
            .space(DEFAULT_SPACE)
            .mainPart(DEFAULT_MAIN_PART)
            .subPart(DEFAULT_SUB_PART)
            .name(DEFAULT_NAME)
            .recognizeType(DEFAULT_RECOGNIZE_TYPE)
            .recognizeContent(DEFAULT_RECOGNIZE_CONTENT)
            .site(DEFAULT_SITE)
            .line(DEFAULT_LINE)
            .source(DEFAULT_SOURCE)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .installDate(DEFAULT_INSTALL_DATE)
            .status(DEFAULT_STATUS)
            .deviceModel(DEFAULT_DEVICE_MODEL)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .remark(DEFAULT_REMARK);
        return powerDevice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerDevice createUpdatedEntity(EntityManager em) {
        PowerDevice powerDevice = new PowerDevice()
            .area(UPDATED_AREA)
            .space(UPDATED_SPACE)
            .mainPart(UPDATED_MAIN_PART)
            .subPart(UPDATED_SUB_PART)
            .name(UPDATED_NAME)
            .recognizeType(UPDATED_RECOGNIZE_TYPE)
            .recognizeContent(UPDATED_RECOGNIZE_CONTENT)
            .site(UPDATED_SITE)
            .line(UPDATED_LINE)
            .source(UPDATED_SOURCE)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .installDate(UPDATED_INSTALL_DATE)
            .status(UPDATED_STATUS)
            .deviceModel(UPDATED_DEVICE_MODEL)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .remark(UPDATED_REMARK);
        return powerDevice;
    }

    @BeforeEach
    public void initTest() {
        powerDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createPowerDevice() throws Exception {
        int databaseSizeBeforeCreate = powerDeviceRepository.findAll().size();
        // Create the PowerDevice
        restPowerDeviceMockMvc.perform(post("/api/power-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerDevice)))
            .andExpect(status().isCreated());

        // Validate the PowerDevice in the database
        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        PowerDevice testPowerDevice = powerDeviceList.get(powerDeviceList.size() - 1);
        assertThat(testPowerDevice.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testPowerDevice.getSpace()).isEqualTo(DEFAULT_SPACE);
        assertThat(testPowerDevice.getMainPart()).isEqualTo(DEFAULT_MAIN_PART);
        assertThat(testPowerDevice.getSubPart()).isEqualTo(DEFAULT_SUB_PART);
        assertThat(testPowerDevice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPowerDevice.getRecognizeType()).isEqualTo(DEFAULT_RECOGNIZE_TYPE);
        assertThat(testPowerDevice.getRecognizeContent()).isEqualTo(DEFAULT_RECOGNIZE_CONTENT);
        assertThat(testPowerDevice.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testPowerDevice.getLine()).isEqualTo(DEFAULT_LINE);
        assertThat(testPowerDevice.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testPowerDevice.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testPowerDevice.getInstallDate()).isEqualTo(DEFAULT_INSTALL_DATE);
        assertThat(testPowerDevice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPowerDevice.getDeviceModel()).isEqualTo(DEFAULT_DEVICE_MODEL);
        assertThat(testPowerDevice.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testPowerDevice.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testPowerDevice.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createPowerDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = powerDeviceRepository.findAll().size();

        // Create the PowerDevice with an existing ID
        powerDevice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPowerDeviceMockMvc.perform(post("/api/power-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerDevice)))
            .andExpect(status().isBadRequest());

        // Validate the PowerDevice in the database
        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerDeviceRepository.findAll().size();
        // set the field null
        powerDevice.setName(null);

        // Create the PowerDevice, which fails.


        restPowerDeviceMockMvc.perform(post("/api/power-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerDevice)))
            .andExpect(status().isBadRequest());

        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecognizeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerDeviceRepository.findAll().size();
        // set the field null
        powerDevice.setRecognizeType(null);

        // Create the PowerDevice, which fails.


        restPowerDeviceMockMvc.perform(post("/api/power-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerDevice)))
            .andExpect(status().isBadRequest());

        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecognizeContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerDeviceRepository.findAll().size();
        // set the field null
        powerDevice.setRecognizeContent(null);

        // Create the PowerDevice, which fails.


        restPowerDeviceMockMvc.perform(post("/api/power-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerDevice)))
            .andExpect(status().isBadRequest());

        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerDeviceRepository.findAll().size();
        // set the field null
        powerDevice.setStatus(null);

        // Create the PowerDevice, which fails.


        restPowerDeviceMockMvc.perform(post("/api/power-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerDevice)))
            .andExpect(status().isBadRequest());

        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerDeviceRepository.findAll().size();
        // set the field null
        powerDevice.setCreateTime(null);

        // Create the PowerDevice, which fails.


        restPowerDeviceMockMvc.perform(post("/api/power-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerDevice)))
            .andExpect(status().isBadRequest());

        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = powerDeviceRepository.findAll().size();
        // set the field null
        powerDevice.setUpdateTime(null);

        // Create the PowerDevice, which fails.


        restPowerDeviceMockMvc.perform(post("/api/power-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerDevice)))
            .andExpect(status().isBadRequest());

        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPowerDevices() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList
        restPowerDeviceMockMvc.perform(get("/api/power-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].space").value(hasItem(DEFAULT_SPACE)))
            .andExpect(jsonPath("$.[*].mainPart").value(hasItem(DEFAULT_MAIN_PART)))
            .andExpect(jsonPath("$.[*].subPart").value(hasItem(DEFAULT_SUB_PART)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].recognizeType").value(hasItem(DEFAULT_RECOGNIZE_TYPE)))
            .andExpect(jsonPath("$.[*].recognizeContent").value(hasItem(DEFAULT_RECOGNIZE_CONTENT)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].installDate").value(hasItem(DEFAULT_INSTALL_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].deviceModel").value(hasItem(DEFAULT_DEVICE_MODEL)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }
    
    @Test
    @Transactional
    public void getPowerDevice() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get the powerDevice
        restPowerDeviceMockMvc.perform(get("/api/power-devices/{id}", powerDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(powerDevice.getId().intValue()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.space").value(DEFAULT_SPACE))
            .andExpect(jsonPath("$.mainPart").value(DEFAULT_MAIN_PART))
            .andExpect(jsonPath("$.subPart").value(DEFAULT_SUB_PART))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.recognizeType").value(DEFAULT_RECOGNIZE_TYPE))
            .andExpect(jsonPath("$.recognizeContent").value(DEFAULT_RECOGNIZE_CONTENT))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE))
            .andExpect(jsonPath("$.line").value(DEFAULT_LINE))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.installDate").value(DEFAULT_INSTALL_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.deviceModel").value(DEFAULT_DEVICE_MODEL))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK));
    }


    @Test
    @Transactional
    public void getPowerDevicesByIdFiltering() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        Long id = powerDevice.getId();

        defaultPowerDeviceShouldBeFound("id.equals=" + id);
        defaultPowerDeviceShouldNotBeFound("id.notEquals=" + id);

        defaultPowerDeviceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPowerDeviceShouldNotBeFound("id.greaterThan=" + id);

        defaultPowerDeviceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPowerDeviceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where area equals to DEFAULT_AREA
        defaultPowerDeviceShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the powerDeviceList where area equals to UPDATED_AREA
        defaultPowerDeviceShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where area not equals to DEFAULT_AREA
        defaultPowerDeviceShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the powerDeviceList where area not equals to UPDATED_AREA
        defaultPowerDeviceShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where area in DEFAULT_AREA or UPDATED_AREA
        defaultPowerDeviceShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the powerDeviceList where area equals to UPDATED_AREA
        defaultPowerDeviceShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where area is not null
        defaultPowerDeviceShouldBeFound("area.specified=true");

        // Get all the powerDeviceList where area is null
        defaultPowerDeviceShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where area is greater than or equal to DEFAULT_AREA
        defaultPowerDeviceShouldBeFound("area.greaterThanOrEqual=" + DEFAULT_AREA);

        // Get all the powerDeviceList where area is greater than or equal to UPDATED_AREA
        defaultPowerDeviceShouldNotBeFound("area.greaterThanOrEqual=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where area is less than or equal to DEFAULT_AREA
        defaultPowerDeviceShouldBeFound("area.lessThanOrEqual=" + DEFAULT_AREA);

        // Get all the powerDeviceList where area is less than or equal to SMALLER_AREA
        defaultPowerDeviceShouldNotBeFound("area.lessThanOrEqual=" + SMALLER_AREA);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where area is less than DEFAULT_AREA
        defaultPowerDeviceShouldNotBeFound("area.lessThan=" + DEFAULT_AREA);

        // Get all the powerDeviceList where area is less than UPDATED_AREA
        defaultPowerDeviceShouldBeFound("area.lessThan=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where area is greater than DEFAULT_AREA
        defaultPowerDeviceShouldNotBeFound("area.greaterThan=" + DEFAULT_AREA);

        // Get all the powerDeviceList where area is greater than SMALLER_AREA
        defaultPowerDeviceShouldBeFound("area.greaterThan=" + SMALLER_AREA);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesBySpaceIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where space equals to DEFAULT_SPACE
        defaultPowerDeviceShouldBeFound("space.equals=" + DEFAULT_SPACE);

        // Get all the powerDeviceList where space equals to UPDATED_SPACE
        defaultPowerDeviceShouldNotBeFound("space.equals=" + UPDATED_SPACE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySpaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where space not equals to DEFAULT_SPACE
        defaultPowerDeviceShouldNotBeFound("space.notEquals=" + DEFAULT_SPACE);

        // Get all the powerDeviceList where space not equals to UPDATED_SPACE
        defaultPowerDeviceShouldBeFound("space.notEquals=" + UPDATED_SPACE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySpaceIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where space in DEFAULT_SPACE or UPDATED_SPACE
        defaultPowerDeviceShouldBeFound("space.in=" + DEFAULT_SPACE + "," + UPDATED_SPACE);

        // Get all the powerDeviceList where space equals to UPDATED_SPACE
        defaultPowerDeviceShouldNotBeFound("space.in=" + UPDATED_SPACE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySpaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where space is not null
        defaultPowerDeviceShouldBeFound("space.specified=true");

        // Get all the powerDeviceList where space is null
        defaultPowerDeviceShouldNotBeFound("space.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerDevicesBySpaceContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where space contains DEFAULT_SPACE
        defaultPowerDeviceShouldBeFound("space.contains=" + DEFAULT_SPACE);

        // Get all the powerDeviceList where space contains UPDATED_SPACE
        defaultPowerDeviceShouldNotBeFound("space.contains=" + UPDATED_SPACE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySpaceNotContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where space does not contain DEFAULT_SPACE
        defaultPowerDeviceShouldNotBeFound("space.doesNotContain=" + DEFAULT_SPACE);

        // Get all the powerDeviceList where space does not contain UPDATED_SPACE
        defaultPowerDeviceShouldBeFound("space.doesNotContain=" + UPDATED_SPACE);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByMainPartIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where mainPart equals to DEFAULT_MAIN_PART
        defaultPowerDeviceShouldBeFound("mainPart.equals=" + DEFAULT_MAIN_PART);

        // Get all the powerDeviceList where mainPart equals to UPDATED_MAIN_PART
        defaultPowerDeviceShouldNotBeFound("mainPart.equals=" + UPDATED_MAIN_PART);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByMainPartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where mainPart not equals to DEFAULT_MAIN_PART
        defaultPowerDeviceShouldNotBeFound("mainPart.notEquals=" + DEFAULT_MAIN_PART);

        // Get all the powerDeviceList where mainPart not equals to UPDATED_MAIN_PART
        defaultPowerDeviceShouldBeFound("mainPart.notEquals=" + UPDATED_MAIN_PART);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByMainPartIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where mainPart in DEFAULT_MAIN_PART or UPDATED_MAIN_PART
        defaultPowerDeviceShouldBeFound("mainPart.in=" + DEFAULT_MAIN_PART + "," + UPDATED_MAIN_PART);

        // Get all the powerDeviceList where mainPart equals to UPDATED_MAIN_PART
        defaultPowerDeviceShouldNotBeFound("mainPart.in=" + UPDATED_MAIN_PART);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByMainPartIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where mainPart is not null
        defaultPowerDeviceShouldBeFound("mainPart.specified=true");

        // Get all the powerDeviceList where mainPart is null
        defaultPowerDeviceShouldNotBeFound("mainPart.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerDevicesByMainPartContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where mainPart contains DEFAULT_MAIN_PART
        defaultPowerDeviceShouldBeFound("mainPart.contains=" + DEFAULT_MAIN_PART);

        // Get all the powerDeviceList where mainPart contains UPDATED_MAIN_PART
        defaultPowerDeviceShouldNotBeFound("mainPart.contains=" + UPDATED_MAIN_PART);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByMainPartNotContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where mainPart does not contain DEFAULT_MAIN_PART
        defaultPowerDeviceShouldNotBeFound("mainPart.doesNotContain=" + DEFAULT_MAIN_PART);

        // Get all the powerDeviceList where mainPart does not contain UPDATED_MAIN_PART
        defaultPowerDeviceShouldBeFound("mainPart.doesNotContain=" + UPDATED_MAIN_PART);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesBySubPartIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where subPart equals to DEFAULT_SUB_PART
        defaultPowerDeviceShouldBeFound("subPart.equals=" + DEFAULT_SUB_PART);

        // Get all the powerDeviceList where subPart equals to UPDATED_SUB_PART
        defaultPowerDeviceShouldNotBeFound("subPart.equals=" + UPDATED_SUB_PART);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySubPartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where subPart not equals to DEFAULT_SUB_PART
        defaultPowerDeviceShouldNotBeFound("subPart.notEquals=" + DEFAULT_SUB_PART);

        // Get all the powerDeviceList where subPart not equals to UPDATED_SUB_PART
        defaultPowerDeviceShouldBeFound("subPart.notEquals=" + UPDATED_SUB_PART);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySubPartIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where subPart in DEFAULT_SUB_PART or UPDATED_SUB_PART
        defaultPowerDeviceShouldBeFound("subPart.in=" + DEFAULT_SUB_PART + "," + UPDATED_SUB_PART);

        // Get all the powerDeviceList where subPart equals to UPDATED_SUB_PART
        defaultPowerDeviceShouldNotBeFound("subPart.in=" + UPDATED_SUB_PART);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySubPartIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where subPart is not null
        defaultPowerDeviceShouldBeFound("subPart.specified=true");

        // Get all the powerDeviceList where subPart is null
        defaultPowerDeviceShouldNotBeFound("subPart.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerDevicesBySubPartContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where subPart contains DEFAULT_SUB_PART
        defaultPowerDeviceShouldBeFound("subPart.contains=" + DEFAULT_SUB_PART);

        // Get all the powerDeviceList where subPart contains UPDATED_SUB_PART
        defaultPowerDeviceShouldNotBeFound("subPart.contains=" + UPDATED_SUB_PART);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySubPartNotContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where subPart does not contain DEFAULT_SUB_PART
        defaultPowerDeviceShouldNotBeFound("subPart.doesNotContain=" + DEFAULT_SUB_PART);

        // Get all the powerDeviceList where subPart does not contain UPDATED_SUB_PART
        defaultPowerDeviceShouldBeFound("subPart.doesNotContain=" + UPDATED_SUB_PART);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where name equals to DEFAULT_NAME
        defaultPowerDeviceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the powerDeviceList where name equals to UPDATED_NAME
        defaultPowerDeviceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where name not equals to DEFAULT_NAME
        defaultPowerDeviceShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the powerDeviceList where name not equals to UPDATED_NAME
        defaultPowerDeviceShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPowerDeviceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the powerDeviceList where name equals to UPDATED_NAME
        defaultPowerDeviceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where name is not null
        defaultPowerDeviceShouldBeFound("name.specified=true");

        // Get all the powerDeviceList where name is null
        defaultPowerDeviceShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerDevicesByNameContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where name contains DEFAULT_NAME
        defaultPowerDeviceShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the powerDeviceList where name contains UPDATED_NAME
        defaultPowerDeviceShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where name does not contain DEFAULT_NAME
        defaultPowerDeviceShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the powerDeviceList where name does not contain UPDATED_NAME
        defaultPowerDeviceShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeType equals to DEFAULT_RECOGNIZE_TYPE
        defaultPowerDeviceShouldBeFound("recognizeType.equals=" + DEFAULT_RECOGNIZE_TYPE);

        // Get all the powerDeviceList where recognizeType equals to UPDATED_RECOGNIZE_TYPE
        defaultPowerDeviceShouldNotBeFound("recognizeType.equals=" + UPDATED_RECOGNIZE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeType not equals to DEFAULT_RECOGNIZE_TYPE
        defaultPowerDeviceShouldNotBeFound("recognizeType.notEquals=" + DEFAULT_RECOGNIZE_TYPE);

        // Get all the powerDeviceList where recognizeType not equals to UPDATED_RECOGNIZE_TYPE
        defaultPowerDeviceShouldBeFound("recognizeType.notEquals=" + UPDATED_RECOGNIZE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeType in DEFAULT_RECOGNIZE_TYPE or UPDATED_RECOGNIZE_TYPE
        defaultPowerDeviceShouldBeFound("recognizeType.in=" + DEFAULT_RECOGNIZE_TYPE + "," + UPDATED_RECOGNIZE_TYPE);

        // Get all the powerDeviceList where recognizeType equals to UPDATED_RECOGNIZE_TYPE
        defaultPowerDeviceShouldNotBeFound("recognizeType.in=" + UPDATED_RECOGNIZE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeType is not null
        defaultPowerDeviceShouldBeFound("recognizeType.specified=true");

        // Get all the powerDeviceList where recognizeType is null
        defaultPowerDeviceShouldNotBeFound("recognizeType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeType is greater than or equal to DEFAULT_RECOGNIZE_TYPE
        defaultPowerDeviceShouldBeFound("recognizeType.greaterThanOrEqual=" + DEFAULT_RECOGNIZE_TYPE);

        // Get all the powerDeviceList where recognizeType is greater than or equal to UPDATED_RECOGNIZE_TYPE
        defaultPowerDeviceShouldNotBeFound("recognizeType.greaterThanOrEqual=" + UPDATED_RECOGNIZE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeType is less than or equal to DEFAULT_RECOGNIZE_TYPE
        defaultPowerDeviceShouldBeFound("recognizeType.lessThanOrEqual=" + DEFAULT_RECOGNIZE_TYPE);

        // Get all the powerDeviceList where recognizeType is less than or equal to SMALLER_RECOGNIZE_TYPE
        defaultPowerDeviceShouldNotBeFound("recognizeType.lessThanOrEqual=" + SMALLER_RECOGNIZE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeType is less than DEFAULT_RECOGNIZE_TYPE
        defaultPowerDeviceShouldNotBeFound("recognizeType.lessThan=" + DEFAULT_RECOGNIZE_TYPE);

        // Get all the powerDeviceList where recognizeType is less than UPDATED_RECOGNIZE_TYPE
        defaultPowerDeviceShouldBeFound("recognizeType.lessThan=" + UPDATED_RECOGNIZE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeType is greater than DEFAULT_RECOGNIZE_TYPE
        defaultPowerDeviceShouldNotBeFound("recognizeType.greaterThan=" + DEFAULT_RECOGNIZE_TYPE);

        // Get all the powerDeviceList where recognizeType is greater than SMALLER_RECOGNIZE_TYPE
        defaultPowerDeviceShouldBeFound("recognizeType.greaterThan=" + SMALLER_RECOGNIZE_TYPE);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeContentIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeContent equals to DEFAULT_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldBeFound("recognizeContent.equals=" + DEFAULT_RECOGNIZE_CONTENT);

        // Get all the powerDeviceList where recognizeContent equals to UPDATED_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldNotBeFound("recognizeContent.equals=" + UPDATED_RECOGNIZE_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeContent not equals to DEFAULT_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldNotBeFound("recognizeContent.notEquals=" + DEFAULT_RECOGNIZE_CONTENT);

        // Get all the powerDeviceList where recognizeContent not equals to UPDATED_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldBeFound("recognizeContent.notEquals=" + UPDATED_RECOGNIZE_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeContentIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeContent in DEFAULT_RECOGNIZE_CONTENT or UPDATED_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldBeFound("recognizeContent.in=" + DEFAULT_RECOGNIZE_CONTENT + "," + UPDATED_RECOGNIZE_CONTENT);

        // Get all the powerDeviceList where recognizeContent equals to UPDATED_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldNotBeFound("recognizeContent.in=" + UPDATED_RECOGNIZE_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeContent is not null
        defaultPowerDeviceShouldBeFound("recognizeContent.specified=true");

        // Get all the powerDeviceList where recognizeContent is null
        defaultPowerDeviceShouldNotBeFound("recognizeContent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeContentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeContent is greater than or equal to DEFAULT_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldBeFound("recognizeContent.greaterThanOrEqual=" + DEFAULT_RECOGNIZE_CONTENT);

        // Get all the powerDeviceList where recognizeContent is greater than or equal to UPDATED_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldNotBeFound("recognizeContent.greaterThanOrEqual=" + UPDATED_RECOGNIZE_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeContentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeContent is less than or equal to DEFAULT_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldBeFound("recognizeContent.lessThanOrEqual=" + DEFAULT_RECOGNIZE_CONTENT);

        // Get all the powerDeviceList where recognizeContent is less than or equal to SMALLER_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldNotBeFound("recognizeContent.lessThanOrEqual=" + SMALLER_RECOGNIZE_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeContentIsLessThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeContent is less than DEFAULT_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldNotBeFound("recognizeContent.lessThan=" + DEFAULT_RECOGNIZE_CONTENT);

        // Get all the powerDeviceList where recognizeContent is less than UPDATED_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldBeFound("recognizeContent.lessThan=" + UPDATED_RECOGNIZE_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRecognizeContentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where recognizeContent is greater than DEFAULT_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldNotBeFound("recognizeContent.greaterThan=" + DEFAULT_RECOGNIZE_CONTENT);

        // Get all the powerDeviceList where recognizeContent is greater than SMALLER_RECOGNIZE_CONTENT
        defaultPowerDeviceShouldBeFound("recognizeContent.greaterThan=" + SMALLER_RECOGNIZE_CONTENT);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesBySiteIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where site equals to DEFAULT_SITE
        defaultPowerDeviceShouldBeFound("site.equals=" + DEFAULT_SITE);

        // Get all the powerDeviceList where site equals to UPDATED_SITE
        defaultPowerDeviceShouldNotBeFound("site.equals=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where site not equals to DEFAULT_SITE
        defaultPowerDeviceShouldNotBeFound("site.notEquals=" + DEFAULT_SITE);

        // Get all the powerDeviceList where site not equals to UPDATED_SITE
        defaultPowerDeviceShouldBeFound("site.notEquals=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySiteIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where site in DEFAULT_SITE or UPDATED_SITE
        defaultPowerDeviceShouldBeFound("site.in=" + DEFAULT_SITE + "," + UPDATED_SITE);

        // Get all the powerDeviceList where site equals to UPDATED_SITE
        defaultPowerDeviceShouldNotBeFound("site.in=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where site is not null
        defaultPowerDeviceShouldBeFound("site.specified=true");

        // Get all the powerDeviceList where site is null
        defaultPowerDeviceShouldNotBeFound("site.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerDevicesBySiteContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where site contains DEFAULT_SITE
        defaultPowerDeviceShouldBeFound("site.contains=" + DEFAULT_SITE);

        // Get all the powerDeviceList where site contains UPDATED_SITE
        defaultPowerDeviceShouldNotBeFound("site.contains=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySiteNotContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where site does not contain DEFAULT_SITE
        defaultPowerDeviceShouldNotBeFound("site.doesNotContain=" + DEFAULT_SITE);

        // Get all the powerDeviceList where site does not contain UPDATED_SITE
        defaultPowerDeviceShouldBeFound("site.doesNotContain=" + UPDATED_SITE);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByLineIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where line equals to DEFAULT_LINE
        defaultPowerDeviceShouldBeFound("line.equals=" + DEFAULT_LINE);

        // Get all the powerDeviceList where line equals to UPDATED_LINE
        defaultPowerDeviceShouldNotBeFound("line.equals=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByLineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where line not equals to DEFAULT_LINE
        defaultPowerDeviceShouldNotBeFound("line.notEquals=" + DEFAULT_LINE);

        // Get all the powerDeviceList where line not equals to UPDATED_LINE
        defaultPowerDeviceShouldBeFound("line.notEquals=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByLineIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where line in DEFAULT_LINE or UPDATED_LINE
        defaultPowerDeviceShouldBeFound("line.in=" + DEFAULT_LINE + "," + UPDATED_LINE);

        // Get all the powerDeviceList where line equals to UPDATED_LINE
        defaultPowerDeviceShouldNotBeFound("line.in=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByLineIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where line is not null
        defaultPowerDeviceShouldBeFound("line.specified=true");

        // Get all the powerDeviceList where line is null
        defaultPowerDeviceShouldNotBeFound("line.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerDevicesByLineContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where line contains DEFAULT_LINE
        defaultPowerDeviceShouldBeFound("line.contains=" + DEFAULT_LINE);

        // Get all the powerDeviceList where line contains UPDATED_LINE
        defaultPowerDeviceShouldNotBeFound("line.contains=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByLineNotContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where line does not contain DEFAULT_LINE
        defaultPowerDeviceShouldNotBeFound("line.doesNotContain=" + DEFAULT_LINE);

        // Get all the powerDeviceList where line does not contain UPDATED_LINE
        defaultPowerDeviceShouldBeFound("line.doesNotContain=" + UPDATED_LINE);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where source equals to DEFAULT_SOURCE
        defaultPowerDeviceShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the powerDeviceList where source equals to UPDATED_SOURCE
        defaultPowerDeviceShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where source not equals to DEFAULT_SOURCE
        defaultPowerDeviceShouldNotBeFound("source.notEquals=" + DEFAULT_SOURCE);

        // Get all the powerDeviceList where source not equals to UPDATED_SOURCE
        defaultPowerDeviceShouldBeFound("source.notEquals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultPowerDeviceShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the powerDeviceList where source equals to UPDATED_SOURCE
        defaultPowerDeviceShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where source is not null
        defaultPowerDeviceShouldBeFound("source.specified=true");

        // Get all the powerDeviceList where source is null
        defaultPowerDeviceShouldNotBeFound("source.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerDevicesBySourceContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where source contains DEFAULT_SOURCE
        defaultPowerDeviceShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the powerDeviceList where source contains UPDATED_SOURCE
        defaultPowerDeviceShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where source does not contain DEFAULT_SOURCE
        defaultPowerDeviceShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the powerDeviceList where source does not contain UPDATED_SOURCE
        defaultPowerDeviceShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultPowerDeviceShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the powerDeviceList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultPowerDeviceShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySerialNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where serialNumber not equals to DEFAULT_SERIAL_NUMBER
        defaultPowerDeviceShouldNotBeFound("serialNumber.notEquals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the powerDeviceList where serialNumber not equals to UPDATED_SERIAL_NUMBER
        defaultPowerDeviceShouldBeFound("serialNumber.notEquals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultPowerDeviceShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the powerDeviceList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultPowerDeviceShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where serialNumber is not null
        defaultPowerDeviceShouldBeFound("serialNumber.specified=true");

        // Get all the powerDeviceList where serialNumber is null
        defaultPowerDeviceShouldNotBeFound("serialNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerDevicesBySerialNumberContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where serialNumber contains DEFAULT_SERIAL_NUMBER
        defaultPowerDeviceShouldBeFound("serialNumber.contains=" + DEFAULT_SERIAL_NUMBER);

        // Get all the powerDeviceList where serialNumber contains UPDATED_SERIAL_NUMBER
        defaultPowerDeviceShouldNotBeFound("serialNumber.contains=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesBySerialNumberNotContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where serialNumber does not contain DEFAULT_SERIAL_NUMBER
        defaultPowerDeviceShouldNotBeFound("serialNumber.doesNotContain=" + DEFAULT_SERIAL_NUMBER);

        // Get all the powerDeviceList where serialNumber does not contain UPDATED_SERIAL_NUMBER
        defaultPowerDeviceShouldBeFound("serialNumber.doesNotContain=" + UPDATED_SERIAL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByInstallDateIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where installDate equals to DEFAULT_INSTALL_DATE
        defaultPowerDeviceShouldBeFound("installDate.equals=" + DEFAULT_INSTALL_DATE);

        // Get all the powerDeviceList where installDate equals to UPDATED_INSTALL_DATE
        defaultPowerDeviceShouldNotBeFound("installDate.equals=" + UPDATED_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByInstallDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where installDate not equals to DEFAULT_INSTALL_DATE
        defaultPowerDeviceShouldNotBeFound("installDate.notEquals=" + DEFAULT_INSTALL_DATE);

        // Get all the powerDeviceList where installDate not equals to UPDATED_INSTALL_DATE
        defaultPowerDeviceShouldBeFound("installDate.notEquals=" + UPDATED_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByInstallDateIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where installDate in DEFAULT_INSTALL_DATE or UPDATED_INSTALL_DATE
        defaultPowerDeviceShouldBeFound("installDate.in=" + DEFAULT_INSTALL_DATE + "," + UPDATED_INSTALL_DATE);

        // Get all the powerDeviceList where installDate equals to UPDATED_INSTALL_DATE
        defaultPowerDeviceShouldNotBeFound("installDate.in=" + UPDATED_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByInstallDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where installDate is not null
        defaultPowerDeviceShouldBeFound("installDate.specified=true");

        // Get all the powerDeviceList where installDate is null
        defaultPowerDeviceShouldNotBeFound("installDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByInstallDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where installDate is greater than or equal to DEFAULT_INSTALL_DATE
        defaultPowerDeviceShouldBeFound("installDate.greaterThanOrEqual=" + DEFAULT_INSTALL_DATE);

        // Get all the powerDeviceList where installDate is greater than or equal to UPDATED_INSTALL_DATE
        defaultPowerDeviceShouldNotBeFound("installDate.greaterThanOrEqual=" + UPDATED_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByInstallDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where installDate is less than or equal to DEFAULT_INSTALL_DATE
        defaultPowerDeviceShouldBeFound("installDate.lessThanOrEqual=" + DEFAULT_INSTALL_DATE);

        // Get all the powerDeviceList where installDate is less than or equal to SMALLER_INSTALL_DATE
        defaultPowerDeviceShouldNotBeFound("installDate.lessThanOrEqual=" + SMALLER_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByInstallDateIsLessThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where installDate is less than DEFAULT_INSTALL_DATE
        defaultPowerDeviceShouldNotBeFound("installDate.lessThan=" + DEFAULT_INSTALL_DATE);

        // Get all the powerDeviceList where installDate is less than UPDATED_INSTALL_DATE
        defaultPowerDeviceShouldBeFound("installDate.lessThan=" + UPDATED_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByInstallDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where installDate is greater than DEFAULT_INSTALL_DATE
        defaultPowerDeviceShouldNotBeFound("installDate.greaterThan=" + DEFAULT_INSTALL_DATE);

        // Get all the powerDeviceList where installDate is greater than SMALLER_INSTALL_DATE
        defaultPowerDeviceShouldBeFound("installDate.greaterThan=" + SMALLER_INSTALL_DATE);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where status equals to DEFAULT_STATUS
        defaultPowerDeviceShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the powerDeviceList where status equals to UPDATED_STATUS
        defaultPowerDeviceShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where status not equals to DEFAULT_STATUS
        defaultPowerDeviceShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the powerDeviceList where status not equals to UPDATED_STATUS
        defaultPowerDeviceShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPowerDeviceShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the powerDeviceList where status equals to UPDATED_STATUS
        defaultPowerDeviceShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where status is not null
        defaultPowerDeviceShouldBeFound("status.specified=true");

        // Get all the powerDeviceList where status is null
        defaultPowerDeviceShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where status is greater than or equal to DEFAULT_STATUS
        defaultPowerDeviceShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the powerDeviceList where status is greater than or equal to UPDATED_STATUS
        defaultPowerDeviceShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where status is less than or equal to DEFAULT_STATUS
        defaultPowerDeviceShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the powerDeviceList where status is less than or equal to SMALLER_STATUS
        defaultPowerDeviceShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where status is less than DEFAULT_STATUS
        defaultPowerDeviceShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the powerDeviceList where status is less than UPDATED_STATUS
        defaultPowerDeviceShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where status is greater than DEFAULT_STATUS
        defaultPowerDeviceShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the powerDeviceList where status is greater than SMALLER_STATUS
        defaultPowerDeviceShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByDeviceModelIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where deviceModel equals to DEFAULT_DEVICE_MODEL
        defaultPowerDeviceShouldBeFound("deviceModel.equals=" + DEFAULT_DEVICE_MODEL);

        // Get all the powerDeviceList where deviceModel equals to UPDATED_DEVICE_MODEL
        defaultPowerDeviceShouldNotBeFound("deviceModel.equals=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByDeviceModelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where deviceModel not equals to DEFAULT_DEVICE_MODEL
        defaultPowerDeviceShouldNotBeFound("deviceModel.notEquals=" + DEFAULT_DEVICE_MODEL);

        // Get all the powerDeviceList where deviceModel not equals to UPDATED_DEVICE_MODEL
        defaultPowerDeviceShouldBeFound("deviceModel.notEquals=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByDeviceModelIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where deviceModel in DEFAULT_DEVICE_MODEL or UPDATED_DEVICE_MODEL
        defaultPowerDeviceShouldBeFound("deviceModel.in=" + DEFAULT_DEVICE_MODEL + "," + UPDATED_DEVICE_MODEL);

        // Get all the powerDeviceList where deviceModel equals to UPDATED_DEVICE_MODEL
        defaultPowerDeviceShouldNotBeFound("deviceModel.in=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByDeviceModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where deviceModel is not null
        defaultPowerDeviceShouldBeFound("deviceModel.specified=true");

        // Get all the powerDeviceList where deviceModel is null
        defaultPowerDeviceShouldNotBeFound("deviceModel.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerDevicesByDeviceModelContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where deviceModel contains DEFAULT_DEVICE_MODEL
        defaultPowerDeviceShouldBeFound("deviceModel.contains=" + DEFAULT_DEVICE_MODEL);

        // Get all the powerDeviceList where deviceModel contains UPDATED_DEVICE_MODEL
        defaultPowerDeviceShouldNotBeFound("deviceModel.contains=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByDeviceModelNotContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where deviceModel does not contain DEFAULT_DEVICE_MODEL
        defaultPowerDeviceShouldNotBeFound("deviceModel.doesNotContain=" + DEFAULT_DEVICE_MODEL);

        // Get all the powerDeviceList where deviceModel does not contain UPDATED_DEVICE_MODEL
        defaultPowerDeviceShouldBeFound("deviceModel.doesNotContain=" + UPDATED_DEVICE_MODEL);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where createTime equals to DEFAULT_CREATE_TIME
        defaultPowerDeviceShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the powerDeviceList where createTime equals to UPDATED_CREATE_TIME
        defaultPowerDeviceShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByCreateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where createTime not equals to DEFAULT_CREATE_TIME
        defaultPowerDeviceShouldNotBeFound("createTime.notEquals=" + DEFAULT_CREATE_TIME);

        // Get all the powerDeviceList where createTime not equals to UPDATED_CREATE_TIME
        defaultPowerDeviceShouldBeFound("createTime.notEquals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultPowerDeviceShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the powerDeviceList where createTime equals to UPDATED_CREATE_TIME
        defaultPowerDeviceShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where createTime is not null
        defaultPowerDeviceShouldBeFound("createTime.specified=true");

        // Get all the powerDeviceList where createTime is null
        defaultPowerDeviceShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByCreateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where createTime is greater than or equal to DEFAULT_CREATE_TIME
        defaultPowerDeviceShouldBeFound("createTime.greaterThanOrEqual=" + DEFAULT_CREATE_TIME);

        // Get all the powerDeviceList where createTime is greater than or equal to UPDATED_CREATE_TIME
        defaultPowerDeviceShouldNotBeFound("createTime.greaterThanOrEqual=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByCreateTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where createTime is less than or equal to DEFAULT_CREATE_TIME
        defaultPowerDeviceShouldBeFound("createTime.lessThanOrEqual=" + DEFAULT_CREATE_TIME);

        // Get all the powerDeviceList where createTime is less than or equal to SMALLER_CREATE_TIME
        defaultPowerDeviceShouldNotBeFound("createTime.lessThanOrEqual=" + SMALLER_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByCreateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where createTime is less than DEFAULT_CREATE_TIME
        defaultPowerDeviceShouldNotBeFound("createTime.lessThan=" + DEFAULT_CREATE_TIME);

        // Get all the powerDeviceList where createTime is less than UPDATED_CREATE_TIME
        defaultPowerDeviceShouldBeFound("createTime.lessThan=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByCreateTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where createTime is greater than DEFAULT_CREATE_TIME
        defaultPowerDeviceShouldNotBeFound("createTime.greaterThan=" + DEFAULT_CREATE_TIME);

        // Get all the powerDeviceList where createTime is greater than SMALLER_CREATE_TIME
        defaultPowerDeviceShouldBeFound("createTime.greaterThan=" + SMALLER_CREATE_TIME);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByUpdateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where updateTime equals to DEFAULT_UPDATE_TIME
        defaultPowerDeviceShouldBeFound("updateTime.equals=" + DEFAULT_UPDATE_TIME);

        // Get all the powerDeviceList where updateTime equals to UPDATED_UPDATE_TIME
        defaultPowerDeviceShouldNotBeFound("updateTime.equals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByUpdateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where updateTime not equals to DEFAULT_UPDATE_TIME
        defaultPowerDeviceShouldNotBeFound("updateTime.notEquals=" + DEFAULT_UPDATE_TIME);

        // Get all the powerDeviceList where updateTime not equals to UPDATED_UPDATE_TIME
        defaultPowerDeviceShouldBeFound("updateTime.notEquals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByUpdateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where updateTime in DEFAULT_UPDATE_TIME or UPDATED_UPDATE_TIME
        defaultPowerDeviceShouldBeFound("updateTime.in=" + DEFAULT_UPDATE_TIME + "," + UPDATED_UPDATE_TIME);

        // Get all the powerDeviceList where updateTime equals to UPDATED_UPDATE_TIME
        defaultPowerDeviceShouldNotBeFound("updateTime.in=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByUpdateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where updateTime is not null
        defaultPowerDeviceShouldBeFound("updateTime.specified=true");

        // Get all the powerDeviceList where updateTime is null
        defaultPowerDeviceShouldNotBeFound("updateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByUpdateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where updateTime is greater than or equal to DEFAULT_UPDATE_TIME
        defaultPowerDeviceShouldBeFound("updateTime.greaterThanOrEqual=" + DEFAULT_UPDATE_TIME);

        // Get all the powerDeviceList where updateTime is greater than or equal to UPDATED_UPDATE_TIME
        defaultPowerDeviceShouldNotBeFound("updateTime.greaterThanOrEqual=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByUpdateTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where updateTime is less than or equal to DEFAULT_UPDATE_TIME
        defaultPowerDeviceShouldBeFound("updateTime.lessThanOrEqual=" + DEFAULT_UPDATE_TIME);

        // Get all the powerDeviceList where updateTime is less than or equal to SMALLER_UPDATE_TIME
        defaultPowerDeviceShouldNotBeFound("updateTime.lessThanOrEqual=" + SMALLER_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByUpdateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where updateTime is less than DEFAULT_UPDATE_TIME
        defaultPowerDeviceShouldNotBeFound("updateTime.lessThan=" + DEFAULT_UPDATE_TIME);

        // Get all the powerDeviceList where updateTime is less than UPDATED_UPDATE_TIME
        defaultPowerDeviceShouldBeFound("updateTime.lessThan=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByUpdateTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where updateTime is greater than DEFAULT_UPDATE_TIME
        defaultPowerDeviceShouldNotBeFound("updateTime.greaterThan=" + DEFAULT_UPDATE_TIME);

        // Get all the powerDeviceList where updateTime is greater than SMALLER_UPDATE_TIME
        defaultPowerDeviceShouldBeFound("updateTime.greaterThan=" + SMALLER_UPDATE_TIME);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where remark equals to DEFAULT_REMARK
        defaultPowerDeviceShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the powerDeviceList where remark equals to UPDATED_REMARK
        defaultPowerDeviceShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where remark not equals to DEFAULT_REMARK
        defaultPowerDeviceShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the powerDeviceList where remark not equals to UPDATED_REMARK
        defaultPowerDeviceShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultPowerDeviceShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the powerDeviceList where remark equals to UPDATED_REMARK
        defaultPowerDeviceShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where remark is not null
        defaultPowerDeviceShouldBeFound("remark.specified=true");

        // Get all the powerDeviceList where remark is null
        defaultPowerDeviceShouldNotBeFound("remark.specified=false");
    }
                @Test
    @Transactional
    public void getAllPowerDevicesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where remark contains DEFAULT_REMARK
        defaultPowerDeviceShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the powerDeviceList where remark contains UPDATED_REMARK
        defaultPowerDeviceShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllPowerDevicesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);

        // Get all the powerDeviceList where remark does not contain DEFAULT_REMARK
        defaultPowerDeviceShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the powerDeviceList where remark does not contain UPDATED_REMARK
        defaultPowerDeviceShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByThresholdIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);
        Threshold threshold = ThresholdResourceIT.createEntity(em);
        em.persist(threshold);
        em.flush();
        powerDevice.setThreshold(threshold);
        powerDeviceRepository.saveAndFlush(powerDevice);
        Long thresholdId = threshold.getId();

        // Get all the powerDeviceList where threshold equals to thresholdId
        defaultPowerDeviceShouldBeFound("thresholdId.equals=" + thresholdId);

        // Get all the powerDeviceList where threshold equals to thresholdId + 1
        defaultPowerDeviceShouldNotBeFound("thresholdId.equals=" + (thresholdId + 1));
    }


    @Test
    @Transactional
    public void getAllPowerDevicesByPatrolDeviceIsEqualToSomething() throws Exception {
        // Initialize the database
        powerDeviceRepository.saveAndFlush(powerDevice);
        PatrolDevice patrolDevice = PatrolDeviceResourceIT.createEntity(em);
        em.persist(patrolDevice);
        em.flush();
        powerDevice.addPatrolDevice(patrolDevice);
        powerDeviceRepository.saveAndFlush(powerDevice);
        Long patrolDeviceId = patrolDevice.getId();

        // Get all the powerDeviceList where patrolDevice equals to patrolDeviceId
        defaultPowerDeviceShouldBeFound("patrolDeviceId.equals=" + patrolDeviceId);

        // Get all the powerDeviceList where patrolDevice equals to patrolDeviceId + 1
        defaultPowerDeviceShouldNotBeFound("patrolDeviceId.equals=" + (patrolDeviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPowerDeviceShouldBeFound(String filter) throws Exception {
        restPowerDeviceMockMvc.perform(get("/api/power-devices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].space").value(hasItem(DEFAULT_SPACE)))
            .andExpect(jsonPath("$.[*].mainPart").value(hasItem(DEFAULT_MAIN_PART)))
            .andExpect(jsonPath("$.[*].subPart").value(hasItem(DEFAULT_SUB_PART)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].recognizeType").value(hasItem(DEFAULT_RECOGNIZE_TYPE)))
            .andExpect(jsonPath("$.[*].recognizeContent").value(hasItem(DEFAULT_RECOGNIZE_CONTENT)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].installDate").value(hasItem(DEFAULT_INSTALL_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].deviceModel").value(hasItem(DEFAULT_DEVICE_MODEL)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));

        // Check, that the count call also returns 1
        restPowerDeviceMockMvc.perform(get("/api/power-devices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPowerDeviceShouldNotBeFound(String filter) throws Exception {
        restPowerDeviceMockMvc.perform(get("/api/power-devices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPowerDeviceMockMvc.perform(get("/api/power-devices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPowerDevice() throws Exception {
        // Get the powerDevice
        restPowerDeviceMockMvc.perform(get("/api/power-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePowerDevice() throws Exception {
        // Initialize the database
        powerDeviceService.save(powerDevice);

        int databaseSizeBeforeUpdate = powerDeviceRepository.findAll().size();

        // Update the powerDevice
        PowerDevice updatedPowerDevice = powerDeviceRepository.findById(powerDevice.getId()).get();
        // Disconnect from session so that the updates on updatedPowerDevice are not directly saved in db
        em.detach(updatedPowerDevice);
        updatedPowerDevice
            .area(UPDATED_AREA)
            .space(UPDATED_SPACE)
            .mainPart(UPDATED_MAIN_PART)
            .subPart(UPDATED_SUB_PART)
            .name(UPDATED_NAME)
            .recognizeType(UPDATED_RECOGNIZE_TYPE)
            .recognizeContent(UPDATED_RECOGNIZE_CONTENT)
            .site(UPDATED_SITE)
            .line(UPDATED_LINE)
            .source(UPDATED_SOURCE)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .installDate(UPDATED_INSTALL_DATE)
            .status(UPDATED_STATUS)
            .deviceModel(UPDATED_DEVICE_MODEL)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .remark(UPDATED_REMARK);

        restPowerDeviceMockMvc.perform(put("/api/power-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPowerDevice)))
            .andExpect(status().isOk());

        // Validate the PowerDevice in the database
        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeUpdate);
        PowerDevice testPowerDevice = powerDeviceList.get(powerDeviceList.size() - 1);
        assertThat(testPowerDevice.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testPowerDevice.getSpace()).isEqualTo(UPDATED_SPACE);
        assertThat(testPowerDevice.getMainPart()).isEqualTo(UPDATED_MAIN_PART);
        assertThat(testPowerDevice.getSubPart()).isEqualTo(UPDATED_SUB_PART);
        assertThat(testPowerDevice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPowerDevice.getRecognizeType()).isEqualTo(UPDATED_RECOGNIZE_TYPE);
        assertThat(testPowerDevice.getRecognizeContent()).isEqualTo(UPDATED_RECOGNIZE_CONTENT);
        assertThat(testPowerDevice.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testPowerDevice.getLine()).isEqualTo(UPDATED_LINE);
        assertThat(testPowerDevice.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testPowerDevice.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testPowerDevice.getInstallDate()).isEqualTo(UPDATED_INSTALL_DATE);
        assertThat(testPowerDevice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPowerDevice.getDeviceModel()).isEqualTo(UPDATED_DEVICE_MODEL);
        assertThat(testPowerDevice.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testPowerDevice.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testPowerDevice.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingPowerDevice() throws Exception {
        int databaseSizeBeforeUpdate = powerDeviceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPowerDeviceMockMvc.perform(put("/api/power-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(powerDevice)))
            .andExpect(status().isBadRequest());

        // Validate the PowerDevice in the database
        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePowerDevice() throws Exception {
        // Initialize the database
        powerDeviceService.save(powerDevice);

        int databaseSizeBeforeDelete = powerDeviceRepository.findAll().size();

        // Delete the powerDevice
        restPowerDeviceMockMvc.perform(delete("/api/power-devices/{id}", powerDevice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PowerDevice> powerDeviceList = powerDeviceRepository.findAll();
        assertThat(powerDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
