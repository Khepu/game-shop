package com.gmakris.gameshop.gateway.service.crud;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.repository.GameRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository repository;

    public GameServiceImpl(final GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Game> findById(final UUID gameId) {
        return repository.findById(gameId);
    }

    @Override
    public Flux<Game> findAllPaginated(final int page, final int size) {
        return repository.findAllPaginated((page - 1) * size, size);
    }

    @Override
    public Flux<Game> findPricedGamesByQuery(
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
