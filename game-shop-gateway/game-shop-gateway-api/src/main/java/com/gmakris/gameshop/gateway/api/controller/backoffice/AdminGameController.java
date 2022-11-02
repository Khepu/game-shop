package com.gmakris.gameshop.gateway.api.controller.backoffice;

import static com.gmakris.gameshop.gateway.entity.model.backoffice.GameStatus.AVAILABLE;
import static com.gmakris.gameshop.gateway.entity.model.backoffice.GameStatus.UNAVAILABLE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.util.UUID;
import com.gmakris.gameshop.gateway.api.controller.AuthenticatedController;
import com.gmakris.gameshop.gateway.api.controller.GenericController;
import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.entity.model.backoffice.GameState;
import com.gmakris.gameshop.gateway.entity.model.backoffice.GameStatus;
import com.gmakris.gameshop.gateway.mapper.GameMapper;
import com.gmakris.gameshop.gateway.service.crud.GameService;
import com.gmakris.gameshop.gateway.service.crud.auth.UserService;
import com.gmakris.gameshop.gateway.service.crud.backoffice.GameStateService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AdminGameController extends AuthenticatedController implements GenericController {

    private final GameMapper gameMapper;
    private final GameService gameService;
    private final GameStateService gameStateService;

    public AdminGameController(
        final UserService userService,
        final GameMapper gameMapper,
        final GameService gameService,
        final GameStateService gameStateService
    ) {
        super(userService);
        this.gameMapper = gameMapper;

        this.gameService = gameService;
        this.gameStateService = gameStateService;
    }

    private Mono<ServerResponse> createGame(final ServerRequest serverRequest) {
        return getUserId(serverRequest)
            .flatMap(userId -> serverRequest.bodyToMono(String.class)
                .map(newGameName -> new Game(null, newGameName, null))
                .flatMap(gameService::save)
                .flatMap(persistedGame -> gameStateService.save(
                        new GameState(
                            null,
                            persistedGame.id(),
                            userId,
                            null,
                            AVAILABLE))
                    .thenReturn(persistedGame)))
            .map(gameMapper::to)
            .flatMap(persistedGame -> ServerResponse
                .ok()
                .bodyValue(persistedGame))
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .bodyValue(throwable.getMessage()));
    }

    private Mono<GameState> changeGameStatus(
        final ServerRequest serverRequest,
        final GameStatus gameStatus
    ) {
        return getUserId(serverRequest)
            .flatMap(userId -> Mono.justOrEmpty(serverRequest.pathVariable("gameId"))
                .flatMap(gameIdString -> Mono.fromCallable(() -> UUID.fromString(gameIdString)))
                .map(gameId -> new GameState(
                    null,
                    gameId,
                    userId,
                    null,
                    gameStatus)))
            .flatMap(gameStateService::save);
    }

    private Mono<ServerResponse> setGameAvailable(final ServerRequest serverRequest) {
        return getUserId(serverRequest)
            .flatMap(userId -> gameStateService.changeGameStatus(
                userId,
                serverRequest.pathVariable("gameId"),
                AVAILABLE))
            .flatMap(__ -> ServerResponse
                .ok()
                .build())
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .bodyValue(throwable.getMessage()));
    }

    private Mono<ServerResponse> setGameUnavailable(final ServerRequest serverRequest) {
        return getUserId(serverRequest)
            .flatMap(userId -> gameStateService.changeGameStatus(
                userId,
                serverRequest.pathVariable("gameId"),
                UNAVAILABLE))
            .flatMap(__ -> ServerResponse
                .noContent()
                .build())
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .bodyValue(throwable.getMessage()));
    }

    @Override
    public RouterFunction<ServerResponse> routes() {
        return route(POST("/admin/game"), this::createGame)
            .and(route(PUT("/admin/game/{gameId}"), this::setGameAvailable))
            .and(route(DELETE("/admin/game/{gameId}"), this::setGameUnavailable));
    }
}
