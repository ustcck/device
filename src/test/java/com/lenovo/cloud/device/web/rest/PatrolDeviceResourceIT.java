package com.lenovo.cloud.device.web.rest;

import com.lenovo.cloud.device.DeviceApp;
import com.lenovo.cloud.device.domain.PatrolDevice;
import com.lenovo.cloud.device.domain.PowerDevice;
import com.lenovo.cloud.device.repository.PatrolDeviceRepository;
import com.lenovo.cloud.device.service.PatrolDeviceService;
import com.lenovo.cloud.device.service.dto.PatrolDeviceCriteria;
import com.lenovo.cloud.device.service.PatrolDeviceQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PatrolDeviceResource} REST controller.
 */
@SpringBootTest(classes = DeviceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PatrolDeviceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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
    private PatrolDeviceRepository patrolDeviceRepository;

    @Mock
    private PatrolDeviceRepository patrolDeviceRepositoryMock;

    @Mock
    private PatrolDeviceService patrolDeviceServiceMock;

    @Autowired
    private PatrolDeviceService patrolDeviceService;

    @Autowired
    private PatrolDeviceQueryService patrolDeviceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatrolDeviceMockMvc;

    private PatrolDevice patrolDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatrolDevice createEntity(EntityManager em) {
        PatrolDevice patrolDevice = new PatrolDevice()
            .name(DEFAULT_NAME)
            .source(DEFAULT_SOURCE)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .installDate(DEFAULT_INSTALL_DATE)
            .status(DEFAULT_STATUS)
            .deviceModel(DEFAULT_DEVICE_MODEL)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .remark(DEFAULT_REMARK);
        return patrolDevice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatrolDevice createUpdatedEntity(EntityManager em) {
        PatrolDevice patrolDevice = new PatrolDevice()
            .name(UPDATED_NAME)
            .source(UPDATED_SOURCE)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .installDate(UPDATED_INSTALL_DATE)
            .status(UPDATED_STATUS)
            .deviceModel(UPDATED_DEVICE_MODEL)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .remark(UPDATED_REMARK);
        return patrolDevice;
    }

    @BeforeEach
    public void initTest() {
        patrolDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatrolDevice() throws Exception {
        int databaseSizeBeforeCreate = patrolDeviceRepository.findAll().size();
        // Create the PatrolDevice
        restPatrolDeviceMockMvc.perform(post("/api/patrol-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patrolDevice)))
            .andExpect(status().isCreated());

        // Validate the PatrolDevice in the database
        List<PatrolDevice> patrolDeviceList = patrolDeviceRepository.findAll();
        assertThat(patrolDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        PatrolDevice testPatrolDevice = patrolDeviceList.get(patrolDeviceList.size() - 1);
        assertThat(testPatrolDevice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPatrolDevice.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testPatrolDevice.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testPatrolDevice.getInstallDate()).isEqualTo(DEFAULT_INSTALL_DATE);
        assertThat(testPatrolDevice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPatrolDevice.getDeviceModel()).isEqualTo(DEFAULT_DEVICE_MODEL);
        assertThat(testPatrolDevice.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testPatrolDevice.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testPatrolDevice.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createPatrolDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patrolDeviceRepository.findAll().size();

        // Create the PatrolDevice with an existing ID
        patrolDevice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatrolDeviceMockMvc.perform(post("/api/patrol-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patrolDevice)))
            .andExpect(status().isBadRequest());

        // Validate the PatrolDevice in the database
        List<PatrolDevice> patrolDeviceList = patrolDeviceRepository.findAll();
        assertThat(patrolDeviceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patrolDeviceRepository.findAll().size();
        // set the field null
        patrolDevice.setName(null);

        // Create the PatrolDevice, which fails.


        restPatrolDeviceMockMvc.perform(post("/api/patrol-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patrolDevice)))
            .andExpect(status().isBadRequest());

        List<PatrolDevice> patrolDeviceList = patrolDeviceRepository.findAll();
        assertThat(patrolDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = patrolDeviceRepository.findAll().size();
        // set the field null
        patrolDevice.setStatus(null);

        // Create the PatrolDevice, which fails.


        restPatrolDeviceMockMvc.perform(post("/api/patrol-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patrolDevice)))
            .andExpect(status().isBadRequest());

        List<PatrolDevice> patrolDeviceList = patrolDeviceRepository.findAll();
        assertThat(patrolDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = patrolDeviceRepository.findAll().size();
        // set the field null
        patrolDevice.setCreateTime(null);

        // Create the PatrolDevice, which fails.


        restPatrolDeviceMockMvc.perform(post("/api/patrol-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patrolDevice)))
            .andExpect(status().isBadRequest());

        List<PatrolDevice> patrolDeviceList = patrolDeviceRepository.findAll();
        assertThat(patrolDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = patrolDeviceRepository.findAll().size();
        // set the field null
        patrolDevice.setUpdateTime(null);

        // Create the PatrolDevice, which fails.


        restPatrolDeviceMockMvc.perform(post("/api/patrol-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patrolDevice)))
            .andExpect(status().isBadRequest());

        List<PatrolDevice> patrolDeviceList = patrolDeviceRepository.findAll();
        assertThat(patrolDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatrolDevices() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList
        restPatrolDeviceMockMvc.perform(get("/api/patrol-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patrolDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].installDate").value(hasItem(DEFAULT_INSTALL_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].deviceModel").value(hasItem(DEFAULT_DEVICE_MODEL)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPatrolDevicesWithEagerRelationshipsIsEnabled() throws Exception {
        when(patrolDeviceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPatrolDeviceMockMvc.perform(get("/api/patrol-devices?eagerload=true"))
            .andExpect(status().isOk());

        verify(patrolDeviceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPatrolDevicesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(patrolDeviceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPatrolDeviceMockMvc.perform(get("/api/patrol-devices?eagerload=true"))
            .andExpect(status().isOk());

        verify(patrolDeviceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPatrolDevice() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get the patrolDevice
        restPatrolDeviceMockMvc.perform(get("/api/patrol-devices/{id}", patrolDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patrolDevice.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
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
    public void getPatrolDevicesByIdFiltering() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        Long id = patrolDevice.getId();

        defaultPatrolDeviceShouldBeFound("id.equals=" + id);
        defaultPatrolDeviceShouldNotBeFound("id.notEquals=" + id);

        defaultPatrolDeviceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPatrolDeviceShouldNotBeFound("id.greaterThan=" + id);

        defaultPatrolDeviceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPatrolDeviceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPatrolDevicesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where name equals to DEFAULT_NAME
        defaultPatrolDeviceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the patrolDeviceList where name equals to UPDATED_NAME
        defaultPatrolDeviceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where name not equals to DEFAULT_NAME
        defaultPatrolDeviceShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the patrolDeviceList where name not equals to UPDATED_NAME
        defaultPatrolDeviceShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPatrolDeviceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the patrolDeviceList where name equals to UPDATED_NAME
        defaultPatrolDeviceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where name is not null
        defaultPatrolDeviceShouldBeFound("name.specified=true");

        // Get all the patrolDeviceList where name is null
        defaultPatrolDeviceShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPatrolDevicesByNameContainsSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where name contains DEFAULT_NAME
        defaultPatrolDeviceShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the patrolDeviceList where name contains UPDATED_NAME
        defaultPatrolDeviceShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where name does not contain DEFAULT_NAME
        defaultPatrolDeviceShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the patrolDeviceList where name does not contain UPDATED_NAME
        defaultPatrolDeviceShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPatrolDevicesBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where source equals to DEFAULT_SOURCE
        defaultPatrolDeviceShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the patrolDeviceList where source equals to UPDATED_SOURCE
        defaultPatrolDeviceShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesBySourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where source not equals to DEFAULT_SOURCE
        defaultPatrolDeviceShouldNotBeFound("source.notEquals=" + DEFAULT_SOURCE);

        // Get all the patrolDeviceList where source not equals to UPDATED_SOURCE
        defaultPatrolDeviceShouldBeFound("source.notEquals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultPatrolDeviceShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the patrolDeviceList where source equals to UPDATED_SOURCE
        defaultPatrolDeviceShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where source is not null
        defaultPatrolDeviceShouldBeFound("source.specified=true");

        // Get all the patrolDeviceList where source is null
        defaultPatrolDeviceShouldNotBeFound("source.specified=false");
    }
                @Test
    @Transactional
    public void getAllPatrolDevicesBySourceContainsSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where source contains DEFAULT_SOURCE
        defaultPatrolDeviceShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the patrolDeviceList where source contains UPDATED_SOURCE
        defaultPatrolDeviceShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where source does not contain DEFAULT_SOURCE
        defaultPatrolDeviceShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the patrolDeviceList where source does not contain UPDATED_SOURCE
        defaultPatrolDeviceShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }


    @Test
    @Transactional
    public void getAllPatrolDevicesBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultPatrolDeviceShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the patrolDeviceList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultPatrolDeviceShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesBySerialNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where serialNumber not equals to DEFAULT_SERIAL_NUMBER
        defaultPatrolDeviceShouldNotBeFound("serialNumber.notEquals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the patrolDeviceList where serialNumber not equals to UPDATED_SERIAL_NUMBER
        defaultPatrolDeviceShouldBeFound("serialNumber.notEquals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultPatrolDeviceShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the patrolDeviceList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultPatrolDeviceShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where serialNumber is not null
        defaultPatrolDeviceShouldBeFound("serialNumber.specified=true");

        // Get all the patrolDeviceList where serialNumber is null
        defaultPatrolDeviceShouldNotBeFound("serialNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllPatrolDevicesBySerialNumberContainsSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where serialNumber contains DEFAULT_SERIAL_NUMBER
        defaultPatrolDeviceShouldBeFound("serialNumber.contains=" + DEFAULT_SERIAL_NUMBER);

        // Get all the patrolDeviceList where serialNumber contains UPDATED_SERIAL_NUMBER
        defaultPatrolDeviceShouldNotBeFound("serialNumber.contains=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesBySerialNumberNotContainsSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where serialNumber does not contain DEFAULT_SERIAL_NUMBER
        defaultPatrolDeviceShouldNotBeFound("serialNumber.doesNotContain=" + DEFAULT_SERIAL_NUMBER);

        // Get all the patrolDeviceList where serialNumber does not contain UPDATED_SERIAL_NUMBER
        defaultPatrolDeviceShouldBeFound("serialNumber.doesNotContain=" + UPDATED_SERIAL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPatrolDevicesByInstallDateIsEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where installDate equals to DEFAULT_INSTALL_DATE
        defaultPatrolDeviceShouldBeFound("installDate.equals=" + DEFAULT_INSTALL_DATE);

        // Get all the patrolDeviceList where installDate equals to UPDATED_INSTALL_DATE
        defaultPatrolDeviceShouldNotBeFound("installDate.equals=" + UPDATED_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByInstallDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where installDate not equals to DEFAULT_INSTALL_DATE
        defaultPatrolDeviceShouldNotBeFound("installDate.notEquals=" + DEFAULT_INSTALL_DATE);

        // Get all the patrolDeviceList where installDate not equals to UPDATED_INSTALL_DATE
        defaultPatrolDeviceShouldBeFound("installDate.notEquals=" + UPDATED_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByInstallDateIsInShouldWork() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where installDate in DEFAULT_INSTALL_DATE or UPDATED_INSTALL_DATE
        defaultPatrolDeviceShouldBeFound("installDate.in=" + DEFAULT_INSTALL_DATE + "," + UPDATED_INSTALL_DATE);

        // Get all the patrolDeviceList where installDate equals to UPDATED_INSTALL_DATE
        defaultPatrolDeviceShouldNotBeFound("installDate.in=" + UPDATED_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByInstallDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where installDate is not null
        defaultPatrolDeviceShouldBeFound("installDate.specified=true");

        // Get all the patrolDeviceList where installDate is null
        defaultPatrolDeviceShouldNotBeFound("installDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByInstallDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where installDate is greater than or equal to DEFAULT_INSTALL_DATE
        defaultPatrolDeviceShouldBeFound("installDate.greaterThanOrEqual=" + DEFAULT_INSTALL_DATE);

        // Get all the patrolDeviceList where installDate is greater than or equal to UPDATED_INSTALL_DATE
        defaultPatrolDeviceShouldNotBeFound("installDate.greaterThanOrEqual=" + UPDATED_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByInstallDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where installDate is less than or equal to DEFAULT_INSTALL_DATE
        defaultPatrolDeviceShouldBeFound("installDate.lessThanOrEqual=" + DEFAULT_INSTALL_DATE);

        // Get all the patrolDeviceList where installDate is less than or equal to SMALLER_INSTALL_DATE
        defaultPatrolDeviceShouldNotBeFound("installDate.lessThanOrEqual=" + SMALLER_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByInstallDateIsLessThanSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where installDate is less than DEFAULT_INSTALL_DATE
        defaultPatrolDeviceShouldNotBeFound("installDate.lessThan=" + DEFAULT_INSTALL_DATE);

        // Get all the patrolDeviceList where installDate is less than UPDATED_INSTALL_DATE
        defaultPatrolDeviceShouldBeFound("installDate.lessThan=" + UPDATED_INSTALL_DATE);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByInstallDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where installDate is greater than DEFAULT_INSTALL_DATE
        defaultPatrolDeviceShouldNotBeFound("installDate.greaterThan=" + DEFAULT_INSTALL_DATE);

        // Get all the patrolDeviceList where installDate is greater than SMALLER_INSTALL_DATE
        defaultPatrolDeviceShouldBeFound("installDate.greaterThan=" + SMALLER_INSTALL_DATE);
    }


    @Test
    @Transactional
    public void getAllPatrolDevicesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where status equals to DEFAULT_STATUS
        defaultPatrolDeviceShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the patrolDeviceList where status equals to UPDATED_STATUS
        defaultPatrolDeviceShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where status not equals to DEFAULT_STATUS
        defaultPatrolDeviceShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the patrolDeviceList where status not equals to UPDATED_STATUS
        defaultPatrolDeviceShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPatrolDeviceShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the patrolDeviceList where status equals to UPDATED_STATUS
        defaultPatrolDeviceShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where status is not null
        defaultPatrolDeviceShouldBeFound("status.specified=true");

        // Get all the patrolDeviceList where status is null
        defaultPatrolDeviceShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where status is greater than or equal to DEFAULT_STATUS
        defaultPatrolDeviceShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the patrolDeviceList where status is greater than or equal to UPDATED_STATUS
        defaultPatrolDeviceShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where status is less than or equal to DEFAULT_STATUS
        defaultPatrolDeviceShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the patrolDeviceList where status is less than or equal to SMALLER_STATUS
        defaultPatrolDeviceShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where status is less than DEFAULT_STATUS
        defaultPatrolDeviceShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the patrolDeviceList where status is less than UPDATED_STATUS
        defaultPatrolDeviceShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where status is greater than DEFAULT_STATUS
        defaultPatrolDeviceShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the patrolDeviceList where status is greater than SMALLER_STATUS
        defaultPatrolDeviceShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllPatrolDevicesByDeviceModelIsEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where deviceModel equals to DEFAULT_DEVICE_MODEL
        defaultPatrolDeviceShouldBeFound("deviceModel.equals=" + DEFAULT_DEVICE_MODEL);

        // Get all the patrolDeviceList where deviceModel equals to UPDATED_DEVICE_MODEL
        defaultPatrolDeviceShouldNotBeFound("deviceModel.equals=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByDeviceModelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where deviceModel not equals to DEFAULT_DEVICE_MODEL
        defaultPatrolDeviceShouldNotBeFound("deviceModel.notEquals=" + DEFAULT_DEVICE_MODEL);

        // Get all the patrolDeviceList where deviceModel not equals to UPDATED_DEVICE_MODEL
        defaultPatrolDeviceShouldBeFound("deviceModel.notEquals=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByDeviceModelIsInShouldWork() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where deviceModel in DEFAULT_DEVICE_MODEL or UPDATED_DEVICE_MODEL
        defaultPatrolDeviceShouldBeFound("deviceModel.in=" + DEFAULT_DEVICE_MODEL + "," + UPDATED_DEVICE_MODEL);

        // Get all the patrolDeviceList where deviceModel equals to UPDATED_DEVICE_MODEL
        defaultPatrolDeviceShouldNotBeFound("deviceModel.in=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByDeviceModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where deviceModel is not null
        defaultPatrolDeviceShouldBeFound("deviceModel.specified=true");

        // Get all the patrolDeviceList where deviceModel is null
        defaultPatrolDeviceShouldNotBeFound("deviceModel.specified=false");
    }
                @Test
    @Transactional
    public void getAllPatrolDevicesByDeviceModelContainsSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where deviceModel contains DEFAULT_DEVICE_MODEL
        defaultPatrolDeviceShouldBeFound("deviceModel.contains=" + DEFAULT_DEVICE_MODEL);

        // Get all the patrolDeviceList where deviceModel contains UPDATED_DEVICE_MODEL
        defaultPatrolDeviceShouldNotBeFound("deviceModel.contains=" + UPDATED_DEVICE_MODEL);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByDeviceModelNotContainsSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where deviceModel does not contain DEFAULT_DEVICE_MODEL
        defaultPatrolDeviceShouldNotBeFound("deviceModel.doesNotContain=" + DEFAULT_DEVICE_MODEL);

        // Get all the patrolDeviceList where deviceModel does not contain UPDATED_DEVICE_MODEL
        defaultPatrolDeviceShouldBeFound("deviceModel.doesNotContain=" + UPDATED_DEVICE_MODEL);
    }


    @Test
    @Transactional
    public void getAllPatrolDevicesByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where createTime equals to DEFAULT_CREATE_TIME
        defaultPatrolDeviceShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the patrolDeviceList where createTime equals to UPDATED_CREATE_TIME
        defaultPatrolDeviceShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByCreateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where createTime not equals to DEFAULT_CREATE_TIME
        defaultPatrolDeviceShouldNotBeFound("createTime.notEquals=" + DEFAULT_CREATE_TIME);

        // Get all the patrolDeviceList where createTime not equals to UPDATED_CREATE_TIME
        defaultPatrolDeviceShouldBeFound("createTime.notEquals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultPatrolDeviceShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the patrolDeviceList where createTime equals to UPDATED_CREATE_TIME
        defaultPatrolDeviceShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where createTime is not null
        defaultPatrolDeviceShouldBeFound("createTime.specified=true");

        // Get all the patrolDeviceList where createTime is null
        defaultPatrolDeviceShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByCreateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where createTime is greater than or equal to DEFAULT_CREATE_TIME
        defaultPatrolDeviceShouldBeFound("createTime.greaterThanOrEqual=" + DEFAULT_CREATE_TIME);

        // Get all the patrolDeviceList where createTime is greater than or equal to UPDATED_CREATE_TIME
        defaultPatrolDeviceShouldNotBeFound("createTime.greaterThanOrEqual=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByCreateTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where createTime is less than or equal to DEFAULT_CREATE_TIME
        defaultPatrolDeviceShouldBeFound("createTime.lessThanOrEqual=" + DEFAULT_CREATE_TIME);

        // Get all the patrolDeviceList where createTime is less than or equal to SMALLER_CREATE_TIME
        defaultPatrolDeviceShouldNotBeFound("createTime.lessThanOrEqual=" + SMALLER_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByCreateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where createTime is less than DEFAULT_CREATE_TIME
        defaultPatrolDeviceShouldNotBeFound("createTime.lessThan=" + DEFAULT_CREATE_TIME);

        // Get all the patrolDeviceList where createTime is less than UPDATED_CREATE_TIME
        defaultPatrolDeviceShouldBeFound("createTime.lessThan=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByCreateTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where createTime is greater than DEFAULT_CREATE_TIME
        defaultPatrolDeviceShouldNotBeFound("createTime.greaterThan=" + DEFAULT_CREATE_TIME);

        // Get all the patrolDeviceList where createTime is greater than SMALLER_CREATE_TIME
        defaultPatrolDeviceShouldBeFound("createTime.greaterThan=" + SMALLER_CREATE_TIME);
    }


    @Test
    @Transactional
    public void getAllPatrolDevicesByUpdateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where updateTime equals to DEFAULT_UPDATE_TIME
        defaultPatrolDeviceShouldBeFound("updateTime.equals=" + DEFAULT_UPDATE_TIME);

        // Get all the patrolDeviceList where updateTime equals to UPDATED_UPDATE_TIME
        defaultPatrolDeviceShouldNotBeFound("updateTime.equals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByUpdateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where updateTime not equals to DEFAULT_UPDATE_TIME
        defaultPatrolDeviceShouldNotBeFound("updateTime.notEquals=" + DEFAULT_UPDATE_TIME);

        // Get all the patrolDeviceList where updateTime not equals to UPDATED_UPDATE_TIME
        defaultPatrolDeviceShouldBeFound("updateTime.notEquals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByUpdateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where updateTime in DEFAULT_UPDATE_TIME or UPDATED_UPDATE_TIME
        defaultPatrolDeviceShouldBeFound("updateTime.in=" + DEFAULT_UPDATE_TIME + "," + UPDATED_UPDATE_TIME);

        // Get all the patrolDeviceList where updateTime equals to UPDATED_UPDATE_TIME
        defaultPatrolDeviceShouldNotBeFound("updateTime.in=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByUpdateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where updateTime is not null
        defaultPatrolDeviceShouldBeFound("updateTime.specified=true");

        // Get all the patrolDeviceList where updateTime is null
        defaultPatrolDeviceShouldNotBeFound("updateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByUpdateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where updateTime is greater than or equal to DEFAULT_UPDATE_TIME
        defaultPatrolDeviceShouldBeFound("updateTime.greaterThanOrEqual=" + DEFAULT_UPDATE_TIME);

        // Get all the patrolDeviceList where updateTime is greater than or equal to UPDATED_UPDATE_TIME
        defaultPatrolDeviceShouldNotBeFound("updateTime.greaterThanOrEqual=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByUpdateTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where updateTime is less than or equal to DEFAULT_UPDATE_TIME
        defaultPatrolDeviceShouldBeFound("updateTime.lessThanOrEqual=" + DEFAULT_UPDATE_TIME);

        // Get all the patrolDeviceList where updateTime is less than or equal to SMALLER_UPDATE_TIME
        defaultPatrolDeviceShouldNotBeFound("updateTime.lessThanOrEqual=" + SMALLER_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByUpdateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where updateTime is less than DEFAULT_UPDATE_TIME
        defaultPatrolDeviceShouldNotBeFound("updateTime.lessThan=" + DEFAULT_UPDATE_TIME);

        // Get all the patrolDeviceList where updateTime is less than UPDATED_UPDATE_TIME
        defaultPatrolDeviceShouldBeFound("updateTime.lessThan=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByUpdateTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where updateTime is greater than DEFAULT_UPDATE_TIME
        defaultPatrolDeviceShouldNotBeFound("updateTime.greaterThan=" + DEFAULT_UPDATE_TIME);

        // Get all the patrolDeviceList where updateTime is greater than SMALLER_UPDATE_TIME
        defaultPatrolDeviceShouldBeFound("updateTime.greaterThan=" + SMALLER_UPDATE_TIME);
    }


    @Test
    @Transactional
    public void getAllPatrolDevicesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where remark equals to DEFAULT_REMARK
        defaultPatrolDeviceShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the patrolDeviceList where remark equals to UPDATED_REMARK
        defaultPatrolDeviceShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where remark not equals to DEFAULT_REMARK
        defaultPatrolDeviceShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the patrolDeviceList where remark not equals to UPDATED_REMARK
        defaultPatrolDeviceShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultPatrolDeviceShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the patrolDeviceList where remark equals to UPDATED_REMARK
        defaultPatrolDeviceShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where remark is not null
        defaultPatrolDeviceShouldBeFound("remark.specified=true");

        // Get all the patrolDeviceList where remark is null
        defaultPatrolDeviceShouldNotBeFound("remark.specified=false");
    }
                @Test
    @Transactional
    public void getAllPatrolDevicesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where remark contains DEFAULT_REMARK
        defaultPatrolDeviceShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the patrolDeviceList where remark contains UPDATED_REMARK
        defaultPatrolDeviceShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllPatrolDevicesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);

        // Get all the patrolDeviceList where remark does not contain DEFAULT_REMARK
        defaultPatrolDeviceShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the patrolDeviceList where remark does not contain UPDATED_REMARK
        defaultPatrolDeviceShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }


    @Test
    @Transactional
    public void getAllPatrolDevicesByPowerDeviceIsEqualToSomething() throws Exception {
        // Initialize the database
        patrolDeviceRepository.saveAndFlush(patrolDevice);
        PowerDevice powerDevice = PowerDeviceResourceIT.createEntity(em);
        em.persist(powerDevice);
        em.flush();
        patrolDevice.addPowerDevice(powerDevice);
        patrolDeviceRepository.saveAndFlush(patrolDevice);
        Long powerDeviceId = powerDevice.getId();

        // Get all the patrolDeviceList where powerDevice equals to powerDeviceId
        defaultPatrolDeviceShouldBeFound("powerDeviceId.equals=" + powerDeviceId);

        // Get all the patrolDeviceList where powerDevice equals to powerDeviceId + 1
        defaultPatrolDeviceShouldNotBeFound("powerDeviceId.equals=" + (powerDeviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPatrolDeviceShouldBeFound(String filter) throws Exception {
        restPatrolDeviceMockMvc.perform(get("/api/patrol-devices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patrolDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].installDate").value(hasItem(DEFAULT_INSTALL_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].deviceModel").value(hasItem(DEFAULT_DEVICE_MODEL)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));

        // Check, that the count call also returns 1
        restPatrolDeviceMockMvc.perform(get("/api/patrol-devices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPatrolDeviceShouldNotBeFound(String filter) throws Exception {
        restPatrolDeviceMockMvc.perform(get("/api/patrol-devices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPatrolDeviceMockMvc.perform(get("/api/patrol-devices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPatrolDevice() throws Exception {
        // Get the patrolDevice
        restPatrolDeviceMockMvc.perform(get("/api/patrol-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatrolDevice() throws Exception {
        // Initialize the database
        patrolDeviceService.save(patrolDevice);

        int databaseSizeBeforeUpdate = patrolDeviceRepository.findAll().size();

        // Update the patrolDevice
        PatrolDevice updatedPatrolDevice = patrolDeviceRepository.findById(patrolDevice.getId()).get();
        // Disconnect from session so that the updates on updatedPatrolDevice are not directly saved in db
        em.detach(updatedPatrolDevice);
        updatedPatrolDevice
            .name(UPDATED_NAME)
            .source(UPDATED_SOURCE)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .installDate(UPDATED_INSTALL_DATE)
            .status(UPDATED_STATUS)
            .deviceModel(UPDATED_DEVICE_MODEL)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .remark(UPDATED_REMARK);

        restPatrolDeviceMockMvc.perform(put("/api/patrol-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatrolDevice)))
            .andExpect(status().isOk());

        // Validate the PatrolDevice in the database
        List<PatrolDevice> patrolDeviceList = patrolDeviceRepository.findAll();
        assertThat(patrolDeviceList).hasSize(databaseSizeBeforeUpdate);
        PatrolDevice testPatrolDevice = patrolDeviceList.get(patrolDeviceList.size() - 1);
        assertThat(testPatrolDevice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatrolDevice.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testPatrolDevice.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testPatrolDevice.getInstallDate()).isEqualTo(UPDATED_INSTALL_DATE);
        assertThat(testPatrolDevice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPatrolDevice.getDeviceModel()).isEqualTo(UPDATED_DEVICE_MODEL);
        assertThat(testPatrolDevice.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testPatrolDevice.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testPatrolDevice.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingPatrolDevice() throws Exception {
        int databaseSizeBeforeUpdate = patrolDeviceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatrolDeviceMockMvc.perform(put("/api/patrol-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patrolDevice)))
            .andExpect(status().isBadRequest());

        // Validate the PatrolDevice in the database
        List<PatrolDevice> patrolDeviceList = patrolDeviceRepository.findAll();
        assertThat(patrolDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatrolDevice() throws Exception {
        // Initialize the database
        patrolDeviceService.save(patrolDevice);

        int databaseSizeBeforeDelete = patrolDeviceRepository.findAll().size();

        // Delete the patrolDevice
        restPatrolDeviceMockMvc.perform(delete("/api/patrol-devices/{id}", patrolDevice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatrolDevice> patrolDeviceList = patrolDeviceRepository.findAll();
        assertThat(patrolDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
