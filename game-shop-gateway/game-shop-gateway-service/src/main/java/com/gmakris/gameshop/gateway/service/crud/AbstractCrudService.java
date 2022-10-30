package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.GenericEntity;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractCrudService<Entity extends GenericEntity> implements GenericCrudService<Entity> {

    abstract protected GenericRepository<Entity> repository();

    @Override
    public Mono<Entity> save(final Entity entity) {
        return repository().save(entity);
    }

    @Override
    public Flux<Entity> saveAll(final Publisher<Entity> entities) {
        return repository().saveAll(entities);
    }
}
