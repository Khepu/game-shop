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
    select *
    from prices p
        inner join games g on g.id = p.game_id
    where g.id = :id
    order by p.created_at
    limit 1
    """)
    Mono<Price> findMostRecentPriceByGameId(@Param("id") UUID id);
}
