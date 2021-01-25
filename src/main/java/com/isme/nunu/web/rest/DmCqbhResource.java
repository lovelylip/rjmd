package com.isme.nunu.web.rest;

import com.isme.nunu.service.DmCqbhService;
import com.isme.nunu.web.rest.errors.BadRequestAlertException;
import com.isme.nunu.service.dto.DmCqbhDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.reactive.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.isme.nunu.domain.DmCqbh}.
 */
@RestController
@RequestMapping("/api")
public class DmCqbhResource {

    private final Logger log = LoggerFactory.getLogger(DmCqbhResource.class);

    private static final String ENTITY_NAME = "dmCqbh";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DmCqbhService dmCqbhService;

    public DmCqbhResource(DmCqbhService dmCqbhService) {
        this.dmCqbhService = dmCqbhService;
    }

    /**
     * {@code POST  /dm-cqbhs} : Create a new dmCqbh.
     *
     * @param dmCqbhDTO the dmCqbhDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dmCqbhDTO, or with status {@code 400 (Bad Request)} if the dmCqbh has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dm-cqbhs")
    public Mono<ResponseEntity<DmCqbhDTO>> createDmCqbh(@Valid @RequestBody DmCqbhDTO dmCqbhDTO) throws URISyntaxException {
        log.debug("REST request to save DmCqbh : {}", dmCqbhDTO);
        if (dmCqbhDTO.getId() != null) {
            throw new BadRequestAlertException("A new dmCqbh cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return dmCqbhService.save(dmCqbhDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/dm-cqbhs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /dm-cqbhs} : Updates an existing dmCqbh.
     *
     * @param dmCqbhDTO the dmCqbhDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dmCqbhDTO,
     * or with status {@code 400 (Bad Request)} if the dmCqbhDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dmCqbhDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dm-cqbhs")
    public Mono<ResponseEntity<DmCqbhDTO>> updateDmCqbh(@Valid @RequestBody DmCqbhDTO dmCqbhDTO) throws URISyntaxException {
        log.debug("REST request to update DmCqbh : {}", dmCqbhDTO);
        if (dmCqbhDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        return dmCqbhService.save(dmCqbhDTO)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(result -> ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
                .body(result)
            );
    }

    /**
     * {@code GET  /dm-cqbhs} : get all the dmCqbhs.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dmCqbhs in body.
     */
    @GetMapping("/dm-cqbhs")
    public Mono<ResponseEntity<Flux<DmCqbhDTO>>> getAllDmCqbhs(Pageable pageable, ServerHttpRequest request) {
        log.debug("REST request to get a page of DmCqbhs");
        return dmCqbhService.countAll()
            .map(total -> new PageImpl<>(new ArrayList<>(), pageable, total))
            .map(page -> PaginationUtil.generatePaginationHttpHeaders(UriComponentsBuilder.fromHttpRequest(request), page))
            .map(headers -> ResponseEntity.ok().headers(headers).body(dmCqbhService.findAll(pageable)));
    }

    /**
     * {@code GET  /dm-cqbhs/:id} : get the "id" dmCqbh.
     *
     * @param id the id of the dmCqbhDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dmCqbhDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dm-cqbhs/{id}")
    public Mono<ResponseEntity<DmCqbhDTO>> getDmCqbh(@PathVariable String id) {
        log.debug("REST request to get DmCqbh : {}", id);
        Mono<DmCqbhDTO> dmCqbhDTO = dmCqbhService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dmCqbhDTO);
    }

    /**
     * {@code DELETE  /dm-cqbhs/:id} : delete the "id" dmCqbh.
     *
     * @param id the id of the dmCqbhDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dm-cqbhs/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteDmCqbh(@PathVariable String id) {
        log.debug("REST request to delete DmCqbh : {}", id);
        return dmCqbhService.delete(id)            .map(result -> ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
        );
    }

    /**
     * {@code SEARCH  /_search/dm-cqbhs?query=:query} : search for the dmCqbh corresponding
     * to the query.
     *
     * @param query the query of the dmCqbh search.
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the result of the search.
     */
    @GetMapping("/_search/dm-cqbhs")
    public Mono<ResponseEntity<Flux<DmCqbhDTO>>> searchDmCqbhs(@RequestParam String query, Pageable pageable, ServerHttpRequest request) {
        log.debug("REST request to search for a page of DmCqbhs for query {}", query);
        return dmCqbhService.searchCount()
            .map(total -> new PageImpl<>(new ArrayList<>(), pageable, total))
            .map(page -> PaginationUtil.generatePaginationHttpHeaders(UriComponentsBuilder.fromHttpRequest(request), page))
            .map(headers -> ResponseEntity.ok().headers(headers).body(dmCqbhService.search(query, pageable)));
        }
}
