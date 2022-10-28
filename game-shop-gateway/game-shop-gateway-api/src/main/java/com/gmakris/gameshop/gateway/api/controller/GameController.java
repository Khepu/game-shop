package com.gmakris.gameshop.gateway.api.controller;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.gmakris.gameshop.gateway.api.ApiProperties;
import com.gmakris.gameshop.gateway.api.util.ParseUtil;
import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.mapper.GameMapper;
import com.gmakris.gameshop.gateway.mapper.PriceMapper;
import com.gmakris.gameshop.gateway.service.crud.GameService;
import com.gmakris.gameshop.gateway.service.crud.PriceService;
import com.gmakris.gameshop.sdk.dto.PricedGameDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GameController implements GenericController {

    private static final int FIRST_PAGE = 1;

    private final GameMapper gameMapper;
    private final PriceMapper priceMapper;
    private final GameService gameService;
    private final PriceService priceService;
    private final ApiProperties apiProperties;

    public GameController(
        final GameMapper gameMapper,
        final PriceMapper priceMapper,
        final GameService gameService,
        final PriceService priceService,
        final ApiProperties apiProperties
    ) {
        this.gameMapper = gameMapper;
        this.priceMapper = priceMapper;
        this.gameService = gameService;
        this.priceService = priceService;
        this.apiProperties = apiProperties;
    }

    private Mono<PricedGameDto> toPricedGame(final Game game) {
        return priceService.findMostRecentPriceByGame(game)
            .zipWith(Mono.just(game))
            .map(priceAndGame -> new PricedGameDto(
                gameMapper.to(priceAndGame.getT2()),
                priceMapper.to(priceAndGame.getT1())));
    }

    private Mono<ServerResponse> findAllPaginated(final ServerRequest serverRequest) {
        return Mono.zip(
                ParseUtil.toInteger(serverRequest.queryParam("page"))
                    .doOnError(throwable -> log.error("Error parsing page number!"))
                    .onErrorMap(throwable -> new RuntimeException("Could not parse query parameter 'page'!"))
                    .map(page -> max(FIRST_PAGE, page)),
                ParseUtil.toInteger(serverRequest.queryParam("size"))
                    .doOnError(throwable -> log.error("Error parsing page size!"))
                    .onErrorMap(throwable -> new RuntimeException("Could not parse query parameter 'size'!"))
                    .map(size -> min(apiProperties.getMaxPageSize(), size)))
            .flatMapMany(pageAndSize -> gameService.findAllPaginated(
                pageAndSize.getT1(),
                pageAndSize.getT2()))
            .flatMap(this::toPricedGame)
            .collectList()
            .flatMap(pricedGames -> ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(pricedGames))
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .bodyValue(throwable.getMessage()));
    }

    private Mono<ServerResponse> search(final ServerRequest serverRequest) {
        return serverRequest.bodyToMono(String.class)
            .flatMapMany(query -> gameService.findPricedGamesByQuery(
                query,
                apiProperties.getDefaultPageSize()))
            .flatMap(this::toPricedGame)
            .collectList()
            .flatMap(pricedGames -> ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(pricedGames))
            .doOnError(throwable -> log.error("Error while searching for games by query!", throwable))
            .onErrorMap(throwable -> new RuntimeException("Invalid query!"))
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .bodyValue(throwable.getMessage()));
    }

    @Override
    public RouterFunction<ServerResponse> routes() {
        return route(GET("/games"), this::findAllPaginated)
            .and(route(POST("/games"), this::search));
    }
}
