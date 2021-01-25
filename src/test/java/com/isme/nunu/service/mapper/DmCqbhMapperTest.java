package com.isme.nunu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DmCqbhMapperTest {

    private DmCqbhMapper dmCqbhMapper;

    @BeforeEach
    public void setUp() {
        dmCqbhMapper = new DmCqbhMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(dmCqbhMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dmCqbhMapper.fromId(null)).isNull();
    }
}
