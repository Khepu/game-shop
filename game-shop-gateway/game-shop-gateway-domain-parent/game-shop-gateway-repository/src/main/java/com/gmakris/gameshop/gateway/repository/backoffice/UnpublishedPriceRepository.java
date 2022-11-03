package com.gmakris.gameshop.gateway.repository.backoffice;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.backoffice.UnpublishedPrice;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UnpublishedPriceRepository extends GenericRepository<UnpublishedPrice> {

    @Query("""
    select distinct on (game_id) *
    from unpublished_prices up
        left outer join publications p on p.unpublished_price_id = up.id
    where user_id = :userId
        and p.id is null
    order by game_id, up.created_at desc
    offset :offset
    limit :limit
    """)
    Flux<UnpublishedPrice> findAllByUserId(
        @Param("userId") UUID userId,
        @Param("offset") int offset,
        @Param("limit") int limit);

    @Query("""
    select distinct on (game_id) *
    from unpublished_prices up
        left outer join publications p on p.unpublished_price_id = up.id
    where user_id = :userId
        and p.id is null
        and up.game_id = :gameId
    order by game_id, up.created_at desc
    limit 1
    """)
    Mono<UnpublishedPrice> findOneByUserIdAndGameId(
        @Param("userId") UUID userId,
        @Param("gameId") UUID gameId);

    @Query("""
    select *
    from unpublished_prices
    where user_id = :userId
        and id = :id
    """)
    Mono<UnpublishedPrice> findOneByIdAndUserId(
        @Param("id") UUID id,
        @Param("userId") UUID userId);
}
