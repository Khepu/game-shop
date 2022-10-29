package com.gmakris.gameshop.gateway.repository;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.Price;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PriceRepository extends GenericRepository<Price> {

    @Query("""
    select distinct on (game_id) *
    from prices
    where game_id = :id
    order by game_id, created_at desc
    """)
    Mono<Price> findMostRecentPriceByGameId(@Param("id") UUID id);
}
