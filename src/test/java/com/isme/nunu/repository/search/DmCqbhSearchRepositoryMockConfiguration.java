package com.isme.nunu.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DmCqbhSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DmCqbhSearchRepositoryMockConfiguration {

    @MockBean
    private DmCqbhSearchRepository mockDmCqbhSearchRepository;

}
