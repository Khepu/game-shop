package com.gmakris.gameshop.gateway.service.crud;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.CartItem;
import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.repository.CartItemRepository;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CartItemServiceImpl extends AbstractCrudService<CartItem> implements CartItemService {

    private final GameService gameService;
    private final CartItemRepository repository;

    public CartItemServiceImpl(
        final GameService gameService,
        final CartItemRepository repository
    ) {
        this.gameService = gameService;
        this.repository = repository;
    }

    @Override
    protected GenericRepository<CartItem> repository() {
        return repository;
    }

    @Override
    public Flux<CartItem> findByUserId(final UUID userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Flux<Game> findCartGames(final UUID userId) {
        return findByUserId(userId)
            .map(CartItem::gameId)
            .flatMap(gameService::findById);
    }
}
