package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.entity.model.Price;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import com.gmakris.gameshop.gateway.repository.PriceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class PriceServiceImpl extends AbstractCrudService<Price> implements PriceService {

    private final PriceRepository repository;

    public PriceServiceImpl(final PriceRepository repository) {
        this.repository = repository;
    }

    @Override
    protected GenericRepository<Price> repository() {
        return repository;
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
}
