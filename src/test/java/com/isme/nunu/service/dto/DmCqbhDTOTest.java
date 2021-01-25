package com.isme.nunu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isme.nunu.web.rest.TestUtil;

public class DmCqbhDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DmCqbhDTO.class);
        DmCqbhDTO dmCqbhDTO1 = new DmCqbhDTO();
        dmCqbhDTO1.setId("id1");
        DmCqbhDTO dmCqbhDTO2 = new DmCqbhDTO();
        assertThat(dmCqbhDTO1).isNotEqualTo(dmCqbhDTO2);
        dmCqbhDTO2.setId(dmCqbhDTO1.getId());
        assertThat(dmCqbhDTO1).isEqualTo(dmCqbhDTO2);
        dmCqbhDTO2.setId("id2");
        assertThat(dmCqbhDTO1).isNotEqualTo(dmCqbhDTO2);
        dmCqbhDTO1.setId(null);
        assertThat(dmCqbhDTO1).isNotEqualTo(dmCqbhDTO2);
    }
}
