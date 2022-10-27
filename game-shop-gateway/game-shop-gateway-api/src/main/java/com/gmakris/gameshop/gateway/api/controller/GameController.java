package com.gmakris.gameshop.gateway.api.controller;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.gmakris.gameshop.gateway.api.ApiProperties;
import com.gmakris.gameshop.gateway.api.util.ParseUtil;
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

    private Mono<ServerResponse> findAllPaginated(final ServerRequest serverRequest) {
        return Mono.zip(
                ParseUtil.toInteger(serverRequest.queryParam("size"))
                    .doOnError(throwable -> log.error("Error parsing page size!"))
                    .onErrorMap(throwable -> new RuntimeException("Error parsing page size!"))
                    .map(size -> min(apiProperties.getMaxPageSize(), size)),
                ParseUtil.toInteger(serverRequest.queryParam("page"))
                    .doOnError(throwable -> log.error("Error parsing page number!"))
                    .onErrorMap(throwable -> new RuntimeException("Error parsing page number!"))
                    .map(page -> max(FIRST_PAGE, page)))
            .flatMapMany(pageAndSize -> gameService.findAllPaginated(
                    pageAndSize.getT1(),
                    pageAndSize.getT2())
                .flatMap(game -> priceService.findMostRecentPriceByGame(game)
                    .zipWith(Mono.just(game)))
                .map(priceAndGame -> new PricedGameDto(
                    gameMapper.to(priceAndGame.getT2()),
                    priceMapper.to(priceAndGame.getT1()))))
            .collectList()
            .flatMap(pricedGames -> ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(pricedGames))
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .bodyValue(throwable.getMessage()));
    }

    @Override
    public RouterFunction<ServerResponse> routes() {
        return route(GET("/games"), this::findAllPaginated);
    }
}