package com.gmakris.gameshop.gateway.service.crud;

import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.entity.projection.PricedGame;
import com.gmakris.gameshop.gateway.repository.GameRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository repository;

    public GameServiceImpl(final GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<PricedGame> findPricedGamesByQuery(
        final String query,
        final int limit
    ) {
        return repository.findPricedGamesByQuery(query, limit);
    }

    @Override
    public Flux<Game> saveAll(final Publisher<Game> gamePublisher) {
        return repository.saveAll(gamePublisher);
    }
}