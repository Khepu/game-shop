package com.gmakris.gameshop.gateway.repository.backoffice;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.backoffice.UnpublishedPrice;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UnpublishedPriceRepository extends GenericRepository<UnpublishedPrice> {

    @Query("""
    select distinct on (game_id) *
    from unpublished_prices
    where user_id = :userId
    order by game_id, created_at desc
    """)
    Flux<UnpublishedPrice> findAllByUserId(@Param("userId") UUID userId);
}
