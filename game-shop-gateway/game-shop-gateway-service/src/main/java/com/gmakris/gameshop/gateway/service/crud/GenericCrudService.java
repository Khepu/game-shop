package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.GenericEntity;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericCrudService<Entity extends GenericEntity> {
    Mono<Entity> save(Entity entity);

    Flux<Entity> saveAll(Publisher<Entity> entities);
}
