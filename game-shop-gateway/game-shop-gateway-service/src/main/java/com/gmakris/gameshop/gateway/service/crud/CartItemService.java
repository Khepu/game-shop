package com.gmakris.gameshop.gateway.service.crud;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.CartItem;
import com.gmakris.gameshop.gateway.entity.model.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartItemService {
    Flux<CartItem> findByUserId(UUID userId);

    Flux<Game> findCartGames(UUID userId);

    Mono<CartItem> save(CartItem cartItem);
}
