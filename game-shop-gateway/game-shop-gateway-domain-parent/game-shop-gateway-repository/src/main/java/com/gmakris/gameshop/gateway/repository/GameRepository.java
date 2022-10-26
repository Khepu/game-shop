package com.gmakris.gameshop.gateway.repository;

import com.gmakris.gameshop.gateway.entity.model.Game;
import com.gmakris.gameshop.gateway.entity.projection.PricedGame;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GameRepository extends GenericRepository<Game> {

    @Query("""
    select g.id as gameId,
           p.id as priceId,
           g.created_at as gameCreatedAt,
           p.created_at as priceCreatedAt,
           g.name as name,
           p.value as value
    from games g
        inner join prices p on p.game_id = g.id
    where websearch_to_tsquery('english', :query) @@ name_embeddings
    limit :limit
    """)
    Flux<PricedGame> findPricedGamesByQuery(
        @Param("query") String query,
        @Param("limit") int limit);
}
