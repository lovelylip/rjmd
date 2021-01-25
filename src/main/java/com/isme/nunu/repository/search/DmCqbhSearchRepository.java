package com.isme.nunu.repository.search;

import com.isme.nunu.domain.DmCqbh;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;


import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Spring Data Elasticsearch repository for the {@link DmCqbh} entity.
 */
public interface DmCqbhSearchRepository extends ReactiveElasticsearchRepository<DmCqbh, String>, DmCqbhSearchRepositoryInternal {
}

interface DmCqbhSearchRepositoryInternal {
    Flux<DmCqbh> search(String query, Pageable pageable);
}

class DmCqbhSearchRepositoryInternalImpl implements DmCqbhSearchRepositoryInternal {

    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

    DmCqbhSearchRepositoryInternalImpl(ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
        this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
    }

    @Override
    public Flux<DmCqbh> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        return reactiveElasticsearchTemplate.find(nativeSearchQuery, DmCqbh.class);
    }
}
