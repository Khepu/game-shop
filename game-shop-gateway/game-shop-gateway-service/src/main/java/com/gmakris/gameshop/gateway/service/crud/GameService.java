package com.gmakris.gameshop.gateway.service.crud;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.Game;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameService {
    Mono<Game> findById(UUID gameId);

    Flux<Game> findAllPaginated(int page, int size);

    Flux<Game> findPricedGamesByQuery(String query, int limit);

    Flux<Game> saveAll(Publisher<Game> gamePublisher);
}
