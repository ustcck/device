package com.lenovo.cloud.device.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lenovo.cloud.device.web.rest.TestUtil;

public class PowerDeviceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PowerDevice.class);
        PowerDevice powerDevice1 = new PowerDevice();
        powerDevice1.setId(1L);
        PowerDevice powerDevice2 = new PowerDevice();
        powerDevice2.setId(powerDevice1.getId());
        assertThat(powerDevice1).isEqualTo(powerDevice2);
        powerDevice2.setId(2L);
        assertThat(powerDevice1).isNotEqualTo(powerDevice2);
        powerDevice1.setId(null);
        assertThat(powerDevice1).isNotEqualTo(powerDevice2);
    }
}
