package com.gmakris.gameshop.gateway.service.crud.backoffice;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.backoffice.GameState;
import com.gmakris.gameshop.gateway.entity.model.backoffice.GameStatus;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import com.gmakris.gameshop.gateway.repository.backoffice.GameStateRepository;
import com.gmakris.gameshop.gateway.service.crud.AbstractCrudService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GameStateServiceImpl extends AbstractCrudService<GameState> implements GameStateService {

    private final GameStateRepository repository;

    public GameStateServiceImpl(final GameStateRepository repository) {
        this.repository = repository;
    }

    @Override
    protected GenericRepository<GameState> repository() {
        return repository;
    }

    @Override
    public Mono<GameState> changeGameStatus(
        final UUID userId,
        final String gameIdPathVariable,
        final GameStatus gameStatus
    ) {
        return Mono.justOrEmpty(gameIdPathVariable)
            .flatMap(gameIdString -> Mono.fromCallable(() -> UUID.fromString(gameIdString)))
            .map(gameId -> new GameState(
                null,
                gameId,
                userId,
                null,
                gameStatus))
            .flatMap(this::save);
    }
}
