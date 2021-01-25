package com.isme.nunu.service.impl;

import com.isme.nunu.service.DmCqbhService;
import com.isme.nunu.domain.DmCqbh;
import com.isme.nunu.repository.DmCqbhRepository;
import com.isme.nunu.repository.search.DmCqbhSearchRepository;
import com.isme.nunu.service.dto.DmCqbhDTO;
import com.isme.nunu.service.mapper.DmCqbhMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DmCqbh}.
 */
@Service
public class DmCqbhServiceImpl implements DmCqbhService {

    private final Logger log = LoggerFactory.getLogger(DmCqbhServiceImpl.class);

    private final DmCqbhRepository dmCqbhRepository;

    private final DmCqbhMapper dmCqbhMapper;

    private final DmCqbhSearchRepository dmCqbhSearchRepository;

    public DmCqbhServiceImpl(DmCqbhRepository dmCqbhRepository, DmCqbhMapper dmCqbhMapper, DmCqbhSearchRepository dmCqbhSearchRepository) {
        this.dmCqbhRepository = dmCqbhRepository;
        this.dmCqbhMapper = dmCqbhMapper;
        this.dmCqbhSearchRepository = dmCqbhSearchRepository;
    }

    @Override
    public Mono<DmCqbhDTO> save(DmCqbhDTO dmCqbhDTO) {
        log.debug("Request to save DmCqbh : {}", dmCqbhDTO);
        return dmCqbhRepository.save(dmCqbhMapper.toEntity(dmCqbhDTO))
            .flatMap(dmCqbhSearchRepository::save)

            .map(dmCqbhMapper::toDto)
;    }

    @Override
    public Flux<DmCqbhDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DmCqbhs");
        return dmCqbhRepository.findAllBy(pageable)
            .map(dmCqbhMapper::toDto);
    }


    public Mono<Long> countAll() {
        return dmCqbhRepository.count();
    }

    public Mono<Long> searchCount() {
        return dmCqbhSearchRepository.count();
    }

    @Override
    public Mono<DmCqbhDTO> findOne(String id) {
        log.debug("Request to get DmCqbh : {}", id);
        return dmCqbhRepository.findById(id)
            .map(dmCqbhMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete DmCqbh : {}", id);
        return dmCqbhRepository.deleteById(id)
            .then(dmCqbhSearchRepository.deleteById(id));    }

    @Override
    public Flux<DmCqbhDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DmCqbhs for query {}", query);
        return dmCqbhSearchRepository.search(query, pageable)
            .map(dmCqbhMapper::toDto);
    }
}
