package com.gmakris.gameshop.gateway.service.crud;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.repository.GameRepository;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameServiceImpl extends AbstractCrudService<Game> implements GameService {

    private final GameRepository repository;

    public GameServiceImpl(final GameRepository repository) {
        this.repository = repository;
    }

    @Override
    protected GenericRepository<Game> repository() {
        return repository;
    }

    @Override
    public Mono<Game> findById(final UUID gameId) {
        return repository.findById(gameId);
    }

    @Override
    public Flux<Game> findAllOwnedByUserId(final UUID userId) {
        return repository.findAllOwnedByUserId(userId);
    }

    @Override
    public Flux<Game> findAllPaginated(final int page, final int size) {
        return repository.findAllPaginated((page - 1) * size, size);
    }

    @Override
    public Flux<Game> findAllByQuery(
        final String query,
        final int page,
        final int size
    ) {
        return repository.findAllByQuery(query, (page - 1) * size, size);
    }

    @Override
    public Mono<Game> findOne(final UUID id) {
        return repository.findOne(id);
    }
}
