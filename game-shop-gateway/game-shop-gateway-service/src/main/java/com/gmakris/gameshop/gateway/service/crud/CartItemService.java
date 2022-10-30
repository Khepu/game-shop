package com.gmakris.gameshop.gateway.service.crud;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.CartItem;
import com.gmakris.gameshop.gateway.entity.model.Game;
import reactor.core.publisher.Flux;

public interface CartItemService extends GenericCrudService<CartItem> {
    Flux<CartItem> findByUserId(UUID userId);

    Flux<Game> findCartGames(UUID userId);
}
