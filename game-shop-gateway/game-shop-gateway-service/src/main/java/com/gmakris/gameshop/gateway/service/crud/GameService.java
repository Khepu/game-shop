package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Game;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public interface GameService {
    Flux<Game> findAllPaginated(int page, int size);

    Flux<Game> findPricedGamesByQuery(String query, int limit);

    Flux<Game> saveAll(Publisher<Game> gamePublisher);
}
