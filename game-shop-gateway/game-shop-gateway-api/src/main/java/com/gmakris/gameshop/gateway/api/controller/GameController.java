package com.gmakris.gameshop.gateway.api.controller;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.gmakris.gameshop.gateway.api.ApiProperties;
import com.gmakris.gameshop.gateway.api.util.ParseUtil;
import com.gmakris.gameshop.gateway.dto.PricedGameDto;
import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.mapper.GameMapper;
import com.gmakris.gameshop.gateway.mapper.PriceMapper;
import com.gmakris.gameshop.gateway.service.crud.GameService;
import com.gmakris.gameshop.gateway.service.crud.PriceService;
import com.gmakris.gameshop.gateway.service.crud.auth.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Slf4j
@Component
public class GameController extends AuthenticatedController implements GenericController {

    private static final int FIRST_PAGE = 1;

    private final GameMapper gameMapper;
    private final PriceMapper priceMapper;
    private final GameService gameService;
    private final PriceService priceService;
    private final ApiProperties apiProperties;

    public GameController(
        final GameMapper gameMapper,
        final PriceMapper priceMapper,
        final UserService userService,
        final GameService gameService,
        final PriceService priceService,
        final ApiProperties apiProperties
    ) {
        super(userService);

        this.gameMapper = gameMapper;
        this.priceMapper = priceMapper;
        this.gameService = gameService;
        this.priceService = priceService;
        this.apiProperties = apiProperties;
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
            .flatMapMany(pageAndSize -> findByQueryOrAll(serverRequest, pageAndSize))
            .flatMap(priceService::toPricedGame)
            .map(priceAndGame -> new PricedGameDto(
                gameMapper.to(priceAndGame.getT2()),
                priceMapper.to(priceAndGame.getT1())))
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

    private Flux<Game> findByQueryOrAll(
        final ServerRequest serverRequest,
        final Tuple2<Integer, Integer> pageAndSize
    ) {
        return Mono.justOrEmpty(serverRequest.queryParam("q"))
            .flatMapMany(query -> gameService.findAllByQuery(
                query,
                pageAndSize.getT1(),
                pageAndSize.getT2()))
            .switchIfEmpty(
                gameService.findAllPaginated(
                    pageAndSize.getT1(),
                    pageAndSize.getT2()));
    }

    private Mono<ServerResponse> findOne(final ServerRequest serverRequest) {
        return ParseUtil.toUUID(serverRequest.pathVariable("gameId"))
            .flatMap(gameService::findOne)
            .flatMap(priceService::toPricedGame)
            .map(priceAndGame -> new PricedGameDto(
                gameMapper.to(priceAndGame.getT2()),
                priceMapper.to(priceAndGame.getT1())))
            .flatMap(pricedGame -> ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(pricedGame))
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .bodyValue(throwable.getMessage()));
    }

    @Override
    public RouterFunction<ServerResponse> routes() {
        return route(GET("/games"), this::findAllPaginated)
            .and(route(GET("/games/{gameId}"), this::findOne));
    }
}
