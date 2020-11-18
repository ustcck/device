package com.lenovo.cloud.device.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lenovo.cloud.device.web.rest.TestUtil;

public class ThresholdTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Threshold.class);
        Threshold threshold1 = new Threshold();
        threshold1.setId(1L);
        Threshold threshold2 = new Threshold();
        threshold2.setId(threshold1.getId());
        assertThat(threshold1).isEqualTo(threshold2);
        threshold2.setId(2L);
        assertThat(threshold1).isNotEqualTo(threshold2);
        threshold1.setId(null);
        assertThat(threshold1).isNotEqualTo(threshold2);
    }
}
