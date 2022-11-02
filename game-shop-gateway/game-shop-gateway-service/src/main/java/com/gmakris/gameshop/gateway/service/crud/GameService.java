package com.gmakris.gameshop.gateway.service.crud;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameService extends GenericCrudService<Game> {
    Mono<Game> findById(UUID gameId);

    Flux<Game> findAllOwnedByUserId(UUID userId);

    Flux<Game> findAllPaginated(int page, int size);

    Flux<Game> findAllByQuery(String query, int page, int size);

    Mono<Game> findOne(UUID id);
}
