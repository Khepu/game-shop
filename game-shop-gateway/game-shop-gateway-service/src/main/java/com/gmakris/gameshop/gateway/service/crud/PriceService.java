package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.entity.model.Price;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PriceService {
    Mono<Price> findMostRecentPriceByGame(Game game);

    Flux<Price> saveAll(Publisher<Price> pricePublisher);
}
