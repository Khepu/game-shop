package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.entity.model.Price;
import com.gmakris.gameshop.gateway.repository.PriceRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository repository;

    public PriceServiceImpl(final PriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Tuple2<Price, Game>> toPricedGame(final Game game) {
        return findMostRecentPriceByGame(game)
            .zipWith(Mono.just(game));
    }

    @Override
    public Mono<Price> findMostRecentPriceByGame(final Game game) {
        return repository.findMostRecentPriceByGameId(game.id());
    }

    @Override
    public Flux<Price> saveAll(final Publisher<Price> pricePublisher) {
        return repository.saveAll(pricePublisher);
    }
}
