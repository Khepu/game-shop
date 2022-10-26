package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.projection.PricedGame;
import reactor.core.publisher.Flux;

public interface GameService {
    Flux<PricedGame> findPricedGamesByQuery(String query, int limit);
}
