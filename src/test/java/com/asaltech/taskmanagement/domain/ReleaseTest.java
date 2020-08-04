package com.asaltech.taskmanagement.domain;

import com.asaltech.taskmanagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReleaseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Release.class);
        Release release1 = new Release();
        release1.setId("id1");
        Release release2 = new Release();
        release2.setId(release1.getId());
        assertThat(release1).isEqualTo(release2);
        release2.setId("id2");
        assertThat(release1).isNotEqualTo(release2);
        release1.setId(null);
        assertThat(release1).isNotEqualTo(release2);
    }
}
