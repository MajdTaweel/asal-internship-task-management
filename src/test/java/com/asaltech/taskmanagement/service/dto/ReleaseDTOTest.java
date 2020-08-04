package com.asaltech.taskmanagement.service.dto;

import com.asaltech.taskmanagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReleaseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReleaseDTO.class);
        ReleaseDTO releaseDTO1 = new ReleaseDTO();
        releaseDTO1.setId("id1");
        ReleaseDTO releaseDTO2 = new ReleaseDTO();
        assertThat(releaseDTO1).isNotEqualTo(releaseDTO2);
        releaseDTO2.setId(releaseDTO1.getId());
        assertThat(releaseDTO1).isEqualTo(releaseDTO2);
        releaseDTO2.setId("id2");
        assertThat(releaseDTO1).isNotEqualTo(releaseDTO2);
        releaseDTO1.setId(null);
        assertThat(releaseDTO1).isNotEqualTo(releaseDTO2);
    }
}
