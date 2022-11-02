package com.gmakris.gameshop.gateway.api.controller;

import static com.gmakris.gameshop.gateway.entity.model.CartItemOperation.ADD;
import static com.gmakris.gameshop.gateway.entity.model.CartItemOperation.REMOVE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.util.UUID;
import com.gmakris.gameshop.gateway.dto.PricedGameDto;
import com.gmakris.gameshop.gateway.entity.model.CartItem;
import com.gmakris.gameshop.gateway.entity.model.CartItemOperation;
import com.gmakris.gameshop.gateway.mapper.GameMapper;
import com.gmakris.gameshop.gateway.mapper.PriceMapper;
import com.gmakris.gameshop.gateway.service.crud.auth.UserService;
import com.gmakris.gameshop.gateway.service.crud.CartItemService;
import com.gmakris.gameshop.gateway.service.crud.PriceService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CartItemController extends AuthenticatedController implements GenericController {

    private final GameMapper gameMapper;
    private final PriceMapper priceMapper;
    private final PriceService priceService;
    private final CartItemService cartItemService;

    public CartItemController(
        final UserService userService,
        final GameMapper gameMapper,
        final PriceMapper priceMapper,
        final PriceService priceService,
        final CartItemService cartItemService
    ) {
        super(userService);

        this.gameMapper = gameMapper;
        this.priceMapper = priceMapper;
        this.priceService = priceService;
        this.cartItemService = cartItemService;
    }

    private Mono<ServerResponse> showCart(final ServerRequest serverRequest) {
        return getUserId(serverRequest)
            .flatMapMany(cartItemService::findCartGames)
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

    private Mono<CartItem> addCartItem(
        final ServerRequest serverRequest,
        final CartItemOperation cartItemOperation
    ) {
        return getUserId(serverRequest)
            .flatMap(userId -> Mono.fromCallable(() ->
                    UUID.fromString(serverRequest.pathVariable("gameId")))
                .map(gameId -> new CartItem(
                    null,
                    gameId,
                    userId,
                    null,
                    cartItemOperation)))
            .flatMap(cartItemService::save);
    }

    private Mono<ServerResponse> addToCart(final ServerRequest serverRequest) {
        return addCartItem(serverRequest, ADD)
            .flatMap(__ -> ServerResponse
                .ok()
                .build())
            .onErrorResume(throwable -> ServerResponse
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .bodyValue(throwable.getMessage()));
    }

    private Mono<ServerResponse> removeFromCart(final ServerRequest serverRequest) {
        return addCartItem(serverRequest, REMOVE)
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
        return route(GET("/cart"), this::showCart)
            .and(route(POST("/cart/{gameId}"), this::addToCart))
            .and(route(DELETE("/cart/{gameId}"), this::removeFromCart));
    }
}
