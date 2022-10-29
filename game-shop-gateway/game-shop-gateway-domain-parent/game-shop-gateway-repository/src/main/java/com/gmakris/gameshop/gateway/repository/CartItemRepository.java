package com.gmakris.gameshop.gateway.repository;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.CartItem;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface CartItemRepository extends GenericRepository<CartItem> {

    @Query("""
    with
        items as (
            select distinct on (game_id) *
            from cart_items
            where user_id = :userId
            order by game_id, created_at desc)
        select *
        from items
        where operation = 'ADD'
    """)
    Flux<CartItem> findByUserId(@Param("userId") UUID id);
}
