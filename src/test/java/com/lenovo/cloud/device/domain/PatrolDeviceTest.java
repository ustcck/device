package com.lenovo.cloud.device.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lenovo.cloud.device.web.rest.TestUtil;

public class PatrolDeviceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatrolDevice.class);
        PatrolDevice patrolDevice1 = new PatrolDevice();
        patrolDevice1.setId(1L);
        PatrolDevice patrolDevice2 = new PatrolDevice();
        patrolDevice2.setId(patrolDevice1.getId());
        assertThat(patrolDevice1).isEqualTo(patrolDevice2);
        patrolDevice2.setId(2L);
        assertThat(patrolDevice1).isNotEqualTo(patrolDevice2);
        patrolDevice1.setId(null);
        assertThat(patrolDevice1).isNotEqualTo(patrolDevice2);
    }
}
