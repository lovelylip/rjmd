package com.isme.nunu.web.rest;

import com.isme.nunu.RjmdApp;
import com.isme.nunu.domain.DmCqbh;
import com.isme.nunu.repository.DmCqbhRepository;
import com.isme.nunu.repository.search.DmCqbhSearchRepository;
import com.isme.nunu.service.DmCqbhService;
import com.isme.nunu.service.dto.DmCqbhDTO;
import com.isme.nunu.service.mapper.DmCqbhMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * Integration tests for the {@link DmCqbhResource} REST controller.
 */
@SpringBootTest(classes = RjmdApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient
@WithMockUser
public class DmCqbhResourceIT {

    private static final String DEFAULT_MA = "AAAAAAAAAA";
    private static final String UPDATED_MA = "BBBBBBBBBB";

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    @Autowired
    private DmCqbhRepository dmCqbhRepository;

    @Autowired
    private DmCqbhMapper dmCqbhMapper;

    @Autowired
    private DmCqbhService dmCqbhService;

    /**
     * This repository is mocked in the com.isme.nunu.repository.search test package.
     *
     * @see com.isme.nunu.repository.search.DmCqbhSearchRepositoryMockConfiguration
     */
    @Autowired
    private DmCqbhSearchRepository mockDmCqbhSearchRepository;

    @Autowired
    private WebTestClient webTestClient;

    private DmCqbh dmCqbh;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DmCqbh createEntity() {
        DmCqbh dmCqbh = new DmCqbh()
            .ma(DEFAULT_MA)
            .ten(DEFAULT_TEN);
        return dmCqbh;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DmCqbh createUpdatedEntity() {
        DmCqbh dmCqbh = new DmCqbh()
            .ma(UPDATED_MA)
            .ten(UPDATED_TEN);
        return dmCqbh;
    }

    @BeforeEach
    public void initTest() {
        dmCqbhRepository.deleteAll().block();
        dmCqbh = createEntity();
    }

    @Test
    public void createDmCqbh() throws Exception {
        int databaseSizeBeforeCreate = dmCqbhRepository.findAll().collectList().block().size();
        // Configure the mock search repository
        when(mockDmCqbhSearchRepository.save(any()))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        // Create the DmCqbh
        DmCqbhDTO dmCqbhDTO = dmCqbhMapper.toDto(dmCqbh);
        webTestClient.post().uri("/api/dm-cqbhs")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dmCqbhDTO))
            .exchange()
            .expectStatus().isCreated();

        // Validate the DmCqbh in the database
        List<DmCqbh> dmCqbhList = dmCqbhRepository.findAll().collectList().block();
        assertThat(dmCqbhList).hasSize(databaseSizeBeforeCreate + 1);
        DmCqbh testDmCqbh = dmCqbhList.get(dmCqbhList.size() - 1);
        assertThat(testDmCqbh.getMa()).isEqualTo(DEFAULT_MA);
        assertThat(testDmCqbh.getTen()).isEqualTo(DEFAULT_TEN);

        // Validate the DmCqbh in Elasticsearch
        verify(mockDmCqbhSearchRepository, times(1)).save(testDmCqbh);
    }

    @Test
    public void createDmCqbhWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dmCqbhRepository.findAll().collectList().block().size();

        // Create the DmCqbh with an existing ID
        dmCqbh.setId("existing_id");
        DmCqbhDTO dmCqbhDTO = dmCqbhMapper.toDto(dmCqbh);

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient.post().uri("/api/dm-cqbhs")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dmCqbhDTO))
            .exchange()
            .expectStatus().isBadRequest();

        // Validate the DmCqbh in the database
        List<DmCqbh> dmCqbhList = dmCqbhRepository.findAll().collectList().block();
        assertThat(dmCqbhList).hasSize(databaseSizeBeforeCreate);

        // Validate the DmCqbh in Elasticsearch
        verify(mockDmCqbhSearchRepository, times(0)).save(dmCqbh);
    }


    @Test
    public void checkMaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dmCqbhRepository.findAll().collectList().block().size();
        // set the field null
        dmCqbh.setMa(null);

        // Create the DmCqbh, which fails.
        DmCqbhDTO dmCqbhDTO = dmCqbhMapper.toDto(dmCqbh);


        webTestClient.post().uri("/api/dm-cqbhs")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dmCqbhDTO))
            .exchange()
            .expectStatus().isBadRequest();

        List<DmCqbh> dmCqbhList = dmCqbhRepository.findAll().collectList().block();
        assertThat(dmCqbhList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = dmCqbhRepository.findAll().collectList().block().size();
        // set the field null
        dmCqbh.setTen(null);

        // Create the DmCqbh, which fails.
        DmCqbhDTO dmCqbhDTO = dmCqbhMapper.toDto(dmCqbh);


        webTestClient.post().uri("/api/dm-cqbhs")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dmCqbhDTO))
            .exchange()
            .expectStatus().isBadRequest();

        List<DmCqbh> dmCqbhList = dmCqbhRepository.findAll().collectList().block();
        assertThat(dmCqbhList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDmCqbhs() {
        // Initialize the database
        dmCqbhRepository.save(dmCqbh).block();

        // Get all the dmCqbhList
        webTestClient.get().uri("/api/dm-cqbhs?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id").value(hasItem(dmCqbh.getId()))
            .jsonPath("$.[*].ma").value(hasItem(DEFAULT_MA))
            .jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN));
    }
    
    @Test
    public void getDmCqbh() {
        // Initialize the database
        dmCqbhRepository.save(dmCqbh).block();

        // Get the dmCqbh
        webTestClient.get().uri("/api/dm-cqbhs/{id}", dmCqbh.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").value(is(dmCqbh.getId()))
            .jsonPath("$.ma").value(is(DEFAULT_MA))
            .jsonPath("$.ten").value(is(DEFAULT_TEN));
    }
    @Test
    public void getNonExistingDmCqbh() {
        // Get the dmCqbh
        webTestClient.get().uri("/api/dm-cqbhs/{id}", Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    public void updateDmCqbh() throws Exception {
        // Configure the mock search repository
        when(mockDmCqbhSearchRepository.save(any()))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        // Initialize the database
        dmCqbhRepository.save(dmCqbh).block();

        int databaseSizeBeforeUpdate = dmCqbhRepository.findAll().collectList().block().size();

        // Update the dmCqbh
        DmCqbh updatedDmCqbh = dmCqbhRepository.findById(dmCqbh.getId()).block();
        updatedDmCqbh
            .ma(UPDATED_MA)
            .ten(UPDATED_TEN);
        DmCqbhDTO dmCqbhDTO = dmCqbhMapper.toDto(updatedDmCqbh);

        webTestClient.put().uri("/api/dm-cqbhs")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dmCqbhDTO))
            .exchange()
            .expectStatus().isOk();

        // Validate the DmCqbh in the database
        List<DmCqbh> dmCqbhList = dmCqbhRepository.findAll().collectList().block();
        assertThat(dmCqbhList).hasSize(databaseSizeBeforeUpdate);
        DmCqbh testDmCqbh = dmCqbhList.get(dmCqbhList.size() - 1);
        assertThat(testDmCqbh.getMa()).isEqualTo(UPDATED_MA);
        assertThat(testDmCqbh.getTen()).isEqualTo(UPDATED_TEN);

        // Validate the DmCqbh in Elasticsearch
        verify(mockDmCqbhSearchRepository, times(1)).save(testDmCqbh);
    }

    @Test
    public void updateNonExistingDmCqbh() throws Exception {
        int databaseSizeBeforeUpdate = dmCqbhRepository.findAll().collectList().block().size();

        // Create the DmCqbh
        DmCqbhDTO dmCqbhDTO = dmCqbhMapper.toDto(dmCqbh);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient.put().uri("/api/dm-cqbhs")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(dmCqbhDTO))
            .exchange()
            .expectStatus().isBadRequest();

        // Validate the DmCqbh in the database
        List<DmCqbh> dmCqbhList = dmCqbhRepository.findAll().collectList().block();
        assertThat(dmCqbhList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DmCqbh in Elasticsearch
        verify(mockDmCqbhSearchRepository, times(0)).save(dmCqbh);
    }

    @Test
    public void deleteDmCqbh() {
        // Configure the mock search repository
        when(mockDmCqbhSearchRepository.save(any()))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        when(mockDmCqbhSearchRepository.deleteById(anyString())).thenReturn(Mono.empty());
        // Initialize the database
        dmCqbhRepository.save(dmCqbh).block();

        int databaseSizeBeforeDelete = dmCqbhRepository.findAll().collectList().block().size();

        // Delete the dmCqbh
        webTestClient.delete().uri("/api/dm-cqbhs/{id}", dmCqbh.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNoContent();

        // Validate the database contains one less item
        List<DmCqbh> dmCqbhList = dmCqbhRepository.findAll().collectList().block();
        assertThat(dmCqbhList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DmCqbh in Elasticsearch
        verify(mockDmCqbhSearchRepository, times(1)).deleteById(dmCqbh.getId());
    }

    @Test
    public void searchDmCqbh() {
        // Configure the mock search repository
        when(mockDmCqbhSearchRepository.save(any()))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        when(mockDmCqbhSearchRepository.count()).thenReturn(Mono.just(1L));
        // Initialize the database
        dmCqbhRepository.save(dmCqbh).block();
        when(mockDmCqbhSearchRepository.search("id:" + dmCqbh.getId(), PageRequest.of(0, 20)))
            .thenReturn(Flux.just(dmCqbh));

        // Search the dmCqbh
        webTestClient.get().uri("/api/_search/dm-cqbhs?query=id:" + dmCqbh.getId())
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id").value(hasItem(dmCqbh.getId()))
            .jsonPath("$.[*].ma").value(hasItem(DEFAULT_MA))
            .jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN));
    }
}
