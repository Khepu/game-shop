package com.gmakris.gameshop.gateway.api.controller.backoffice;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.gmakris.gameshop.gateway.api.ApiProperties;
import com.gmakris.gameshop.gateway.api.controller.AuthenticatedController;
import com.gmakris.gameshop.gateway.api.controller.GenericController;
import com.gmakris.gameshop.gateway.api.util.ParseUtil;
import com.gmakris.gameshop.gateway.entity.model.backoffice.UnpublishedPrice;
import com.gmakris.gameshop.gateway.mapper.backoffice.UnpublishedPriceMapper;
import com.gmakris.gameshop.gateway.service.crud.auth.UserService;
import com.gmakris.gameshop.gateway.service.crud.backoffice.UnpublishedPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AdminUnpublishedPriceController extends AuthenticatedController implements GenericController {

    private static final int FIRST_PAGE = 1;

    private final ApiProperties properties;
    private final UnpublishedPriceMapper unpublishedPriceMapper;
    private final UnpublishedPriceService unpublishedPriceService;

    public AdminUnpublishedPriceController(
        final UserService userService,
        final ApiProperties properties,
        final UnpublishedPriceMapper unpublishedPriceMapper,
        final UnpublishedPriceService unpublishedPriceService
    ) {
        super(userService);

        this.properties = properties;
        this.unpublishedPriceMapper = unpublishedPriceMapper;
        this.unpublishedPriceService = unpublishedPriceService;
    }

    private Mono<ServerResponse> findAllPaginated(final ServerRequest serverRequest) {
        return Mono.zip(
                ParseUtil.toInteger(serverRequest.queryParam("page"))
                    .doOnError(throwable -> log.error("Error parsing page number!"))
                    .onErrorMap(throwable -> new RuntimeException("Could not parse query parameter 'page'!"))
                    .map(page -> max(FIRST_PAGE, page))
                    .switchIfEmpty(Mono.just(FIRST_PAGE)),
                ParseUtil.toInteger(serverRequest.queryParam("size"))
                    .doOnError(throwable -> log.error("Error parsing page size!"))
                    .onErrorMap(throwable -> new RuntimeException("Could not parse query parameter 'size'!"))
                    .map(size -> min(properties.getMaxPageSize(), size))
                    .switchIfEmpty(Mono.just(properties.getDefaultPageSize())))
            .flatMap(pageAndSize -> getUserId(serverRequest)
                .flatMapMany(userId -> unpublishedPriceService.findAllByUserId(
                        userId,
                        pageAndSize.getT1(),
                        pageAndSize.getT2())
                    .doOnError(throwable -> log.error("Could not fetch all with parameters [size: '{}', page: '{}'] by user '{}'!",
                        serverRequest.queryParam("size").orElse(null),
                        serverRequest.queryParam("page").orElse(null),
                        userId,
                        throwable)))
                .map(unpublishedPriceMapper::to)
                .collectList()
                .flatMap(unpublishedPrices -> ServerResponse
                    .ok()
                    .contentType(APPLICATION_JSON)
                    .bodyValue(unpublishedPrices))
                .onErrorResume(throwable -> ServerResponse
                    .status(INTERNAL_SERVER_ERROR)
                    .contentType(APPLICATION_JSON)
                    .bodyValue(throwable.getMessage())));
    }

    private Mono<ServerResponse> findOneByGame(final ServerRequest serverRequest) {
        return getUserId(serverRequest)
            .flatMap(userId -> ParseUtil.toUUID(serverRequest.pathVariable("gameId"))
                .flatMap(gameId -> unpublishedPriceService.findOneByUserIdAndGameId(userId, gameId)))
            .flatMap(unpublishedPrice -> ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(unpublishedPrice))
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .bodyValue(throwable.getMessage()));
    }

    private Mono<ServerResponse> createUnpublishedPrice(final ServerRequest serverRequest) {
        return getUserId(serverRequest)
            .flatMap(userId -> ParseUtil.toUUID(serverRequest.pathVariable("gameId"))
                .zipWith(serverRequest.bodyToMono(BigDecimal.class))
                .map(gameIdAndValue -> new UnpublishedPrice(
                    null,
                    gameIdAndValue.getT1(),
                    userId,
                    LocalDateTime.now(),
                    gameIdAndValue.getT2())))
            .flatMap(unpublishedPriceService::save)
            .map(unpublishedPriceMapper::to)
            .flatMap(unpublishedPrice -> ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(unpublishedPrice))
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .bodyValue(throwable.getMessage()));
    }

    @Override
    public RouterFunction<ServerResponse> routes() {
        return route(GET("/admin/unpublished-prices"), this::findAllPaginated)
            .and(route(GET("/admin/unpublished-prices/{gameId}"), this::findOneByGame))
            .and(route(POST("/admin/unpublished-prices/{gameId}"), this::createUnpublishedPrice));
    }
}
