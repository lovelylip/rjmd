package com.isme.nunu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isme.nunu.web.rest.TestUtil;

public class DmCqbhTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DmCqbh.class);
        DmCqbh dmCqbh1 = new DmCqbh();
        dmCqbh1.setId("id1");
        DmCqbh dmCqbh2 = new DmCqbh();
        dmCqbh2.setId(dmCqbh1.getId());
        assertThat(dmCqbh1).isEqualTo(dmCqbh2);
        dmCqbh2.setId("id2");
        assertThat(dmCqbh1).isNotEqualTo(dmCqbh2);
        dmCqbh1.setId(null);
        assertThat(dmCqbh1).isNotEqualTo(dmCqbh2);
    }
}
