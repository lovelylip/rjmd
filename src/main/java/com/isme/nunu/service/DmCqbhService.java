package com.isme.nunu.service;

import com.isme.nunu.service.dto.DmCqbhDTO;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Service Interface for managing {@link com.isme.nunu.domain.DmCqbh}.
 */
public interface DmCqbhService {

    /**
     * Save a dmCqbh.
     *
     * @param dmCqbhDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<DmCqbhDTO> save(DmCqbhDTO dmCqbhDTO);

    /**
     * Get all the dmCqbhs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<DmCqbhDTO> findAll(Pageable pageable);

    /**
    * Returns the number of dmCqbhs available.
    *
    */
    Mono<Long> countAll();

    /**
    * Returns the number of dmCqbhs available in search repository.
    *
    */
    Mono<Long> searchCount();


    /**
     * Get the "id" dmCqbh.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<DmCqbhDTO> findOne(String id);

    /**
     * Delete the "id" dmCqbh.
     *
     * @param id the id of the entity.
     */
    Mono<Void> delete(String id);

    /**
     * Search for the dmCqbh corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<DmCqbhDTO> search(String query, Pageable pageable);
}
