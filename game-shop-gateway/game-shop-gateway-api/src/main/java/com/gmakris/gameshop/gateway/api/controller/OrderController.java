package com.gmakris.gameshop.gateway.api.controller;

import static com.gmakris.gameshop.gateway.entity.model.CartItemOperation.REMOVE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.time.LocalDateTime;
import com.gmakris.gameshop.gateway.entity.model.CartItem;
import com.gmakris.gameshop.gateway.entity.model.Order;
import com.gmakris.gameshop.gateway.service.crud.auth.UserService;
import com.gmakris.gameshop.gateway.service.crud.CartItemService;
import com.gmakris.gameshop.gateway.service.crud.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class OrderController extends AuthenticatedController implements GenericController {

    private final OrderService orderService;
    private final CartItemService cartItemService;

    public OrderController(
        final UserService userService,
        final OrderService orderService,
        final CartItemService cartItemService
    ) {
        super(userService);

        this.orderService = orderService;
        this.cartItemService = cartItemService;
    }

    /**
     * Gives ownership of all the items present in the cart to the user.
     */
    private Mono<ServerResponse> finalizeOrder(final ServerRequest serverRequest) {
        return getUserId(serverRequest)
            .flatMap(userId -> cartItemService.findCartGames(userId)
                .map(game -> new Order(
                    null,
                    userId,
                    game.id(),
                    LocalDateTime.now()))
                .transform(orderService::saveAll)
                .map(order -> new CartItem(
                    null,
                    order.gameId(),
                    order.userId(),
                    LocalDateTime.now(),
                    REMOVE))
                .flatMap(cartItemService::save)
                .collectList()
                .flatMap(__ -> ServerResponse
                    .ok()
                    .build())
                .doOnError(throwable -> log.error("Could not finalize order for user '{}'!",
                    userId,
                    throwable))
                .onErrorResume(throwable -> ServerResponse
                    .status(INTERNAL_SERVER_ERROR)
                    .contentType(APPLICATION_JSON)
                    .bodyValue(throwable.getMessage())));
    }

    @Override
    public RouterFunction<ServerResponse> routes() {
        return route(POST("/orders"), this::finalizeOrder);
    }
}
