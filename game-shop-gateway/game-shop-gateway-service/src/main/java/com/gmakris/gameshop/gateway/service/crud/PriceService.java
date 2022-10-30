package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.entity.model.Price;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public interface PriceService extends GenericCrudService<Price> {
    Mono<Tuple2<Price, Game>> toPricedGame(Game game);

    Mono<Price> findMostRecentPriceByGame(Game game);
}
