package com.isme.nunu.repository;

import com.isme.nunu.domain.DmCqbh;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the DmCqbh entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DmCqbhRepository extends ReactiveMongoRepository<DmCqbh, String> {


    Flux<DmCqbh> findAllBy(Pageable pageable);

}
