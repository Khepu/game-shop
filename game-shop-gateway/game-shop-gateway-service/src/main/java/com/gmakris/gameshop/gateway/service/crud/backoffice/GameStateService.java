package com.gmakris.gameshop.gateway.service.crud.backoffice;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.backoffice.GameState;
import com.gmakris.gameshop.gateway.entity.model.backoffice.GameStatus;
import com.gmakris.gameshop.gateway.service.crud.GenericCrudService;
import reactor.core.publisher.Mono;

public interface GameStateService extends GenericCrudService<GameState> {
    Mono<GameState> changeGameStatus(
        UUID userId,
        String gameIdPathVariable,
        GameStatus gameStatus);
}
